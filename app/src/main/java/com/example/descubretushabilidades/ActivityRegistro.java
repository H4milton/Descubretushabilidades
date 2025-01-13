package com.example.descubretushabilidades;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
//import android.support.v7.app.AppCompatActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.HashMap;
import java.util.Map;

public class ActivityRegistro extends AppCompatActivity {

    private ImageButton btnWhite;
    private Button btn_registar, btnInvitado, btnIniciar;
    private ImageButton btnRegGoogle;
    private TextInputEditText Etname, Etemail, Etpass;
    private TextInputLayout TinpuName, TinpuEmail, TimputPass;

    private FirebaseAuth mAuth;
    private GoogleSignInClient GoogleClient;
    static final String TAG = "GoogleActivity";
    static final int RC_SIGN_IN = 9001;
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnWhite=findViewById(R.id.btnWhite);
        btn_registar=findViewById(R.id.btn_registrarse1);
        btnIniciar=findViewById(R.id.btn_Login);
        btnRegGoogle=findViewById(R.id.buttonGoogleReg);
        Etname = findViewById(R.id.EditText_reg_name);
        Etemail = findViewById(R.id.EditText_reg_email);
        Etpass = findViewById(R.id.EditText_reg_pass);

        intanciarAutenticacion();

        btnWhite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityRegistro.super.onBackPressed();
            }
        });

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //Registro de usuarios con correos normales
        btn_registar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name, email, password;
                name = Etname.getText().toString();
                email= Etemail.getText().toString();
                password=Etpass.getText().toString();

                if (email.isEmpty() | password.isEmpty()|name.isEmpty()){
                    DynamicToast.makeError(getApplicationContext(), "Existen campos vacios", Toast.LENGTH_LONG).show();
                }

                else if (!VerificarCorreo(email)){
                    DynamicToast.makeError(getApplicationContext(), "Correo no valido", Toast.LENGTH_LONG).show();
                }

                else if (password.length()>6){
                    mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser usuario=mAuth.getCurrentUser();
                                DynamicToast.makeSuccess(getApplicationContext(), "Bienvenido"+name, Toast.LENGTH_LONG).show();

                                enviar_aBDG(usuario.getUid(),"NORMAL",usuario.getEmail(),name);
                                guardarSesion(usuario.getUid(),"NORMAL",usuario.getEmail(),name);
                                iraMenu(usuario.getUid(),usuario.getEmail(),name);
                                finish();
                            }else{
                                DynamicToast.makeError(getApplicationContext(), "Correo ya registrado", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
                else{
                    DynamicToast.makeError(getApplicationContext(), "Contraseña corta", Toast.LENGTH_LONG).show();
                }
            }

            private boolean VerificarCorreo(String email) {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }

        });

        btnRegGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                GoogleClient.signOut();
            }
        });
    }


    private void intanciarAutenticacion() {
        mAuth = FirebaseAuth.getInstance();//Instancia para Autenticacion
        db = FirebaseFirestore.getInstance();//Instancia para base de datos

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleClient = GoogleSignIn.getClient(this, gso);

    }

    private void iraMenu(String Uid, String Email, String name){
        Intent intent = new Intent(ActivityRegistro.this,ActivityBienvenida.class);
        intent.putExtra("uid",Uid);
        intent.putExtra("email",Email);
        intent.putExtra("namel",name);
        startActivity(intent);
    }


    private void signIn() {
        Intent signInIntent = GoogleClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                DynamicToast.makeError(getApplicationContext(), "Hubo un error al iniciar sesion, verifique su conexion a internet", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount  account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            DynamicToast.makeSuccess(getApplicationContext(), "Bienvenido "+account.getDisplayName(), Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            enviar_aBDG(account.getId(),"GOOGLE",account.getEmail(),account.getDisplayName());
                            guardarSesion(account.getId(),"GOOGLE",account.getEmail(),account.getDisplayName());
                            iraMenu(account.getDisplayName(),account.getEmail(),account.getId());
                            finish();

                        } else {
                            DynamicToast.makeError(getApplicationContext(), "Hubo un error al iniciar sesion, verifique su conexion a internet", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
    }

    private void enviar_aBDG(String userUID, String EmailProvider,String UserEmail,  String UserName){
        Map<String, Object> user_autenticados = new HashMap<>();
        user_autenticados.put("uid", userUID);
        user_autenticados.put("Cor_prov",EmailProvider);
        user_autenticados.put("nombre", UserName);
        user_autenticados.put("email", UserEmail);

        db.collection("user_autenticados")
                .document(userUID).set(user_autenticados)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DynamicToast.makeSuccess(getApplicationContext(), "Informacion Agregada Correctamente", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        DynamicToast.makeError(getApplicationContext(), "Hubo un error al almacenar la info", Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void guardarSesion(String uid, String providerId, String email, String Name) {
        //guardado de datos
        SharedPreferences prefs = getSharedPreferences("mySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();
        myEdit.putString("email",email);
        myEdit.putString("providerId",providerId);
        myEdit.putString("uid",uid);
        myEdit.putString("name",Name);
        myEdit.apply();
    }

}