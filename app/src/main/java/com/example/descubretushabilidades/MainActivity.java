package com.example.descubretushabilidades;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaSync;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.text.Editable;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialog;
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

import java.text.DateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private Button btn_iniciar,btn_registro,btn_invitado;
    private ImageButton btnIniciarG;
    private EditText InputPass,InputUser;

    //OnBackPres Var
    private int INTERVALO = 2000;
    private long tiempoPrimerClick = 0;

    //Autenticacion Var
    private FirebaseAuth mAuth;
    private GoogleSignInClient GoogleClient;
    private static final String TAG = "GoogleActivity";
    private static final int RC_SIGN_IN = 9001;


    //Firebase Var
    private FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_iniciar=findViewById(R.id.Btn_iniciar1);
        btn_registro=findViewById(R.id.btn_registrarse1);
        btn_invitado=findViewById(R.id.btn_entrarInvitado1);
        btnIniciarG=findViewById(R.id.buttonGoogleIni);
        InputUser = (EditText) findViewById(R.id.email_login);
        InputPass = (EditText) findViewById(R.id.passLogin);

        intanciarAutenticacion();
        VerificarSession();

        btn_iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email, password;
                email= InputUser.getText().toString();
                password=InputPass.getText().toString();

                if (password.isEmpty() | email.isEmpty()){
                    DynamicToast.makeError(getApplicationContext(), "Existen campos vacios", Toast.LENGTH_LONG).show();
                }

                else if (!verificarCorreo(email)){
                    DynamicToast.makeError(getApplicationContext(), "Formato de correo no valido", Toast.LENGTH_LONG).show();
                }

                else {
                    mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                FirebaseUser usuario=mAuth.getCurrentUser();
                                DynamicToast.makeSuccess(getApplicationContext(), "Bienvenido "+usuario.getEmail(), Toast.LENGTH_LONG).show();
                                guardarSesion(usuario.getEmail(),"NORMAL",usuario.getUid(),usuario.getDisplayName());
                                iraMenu(usuario.getUid(),usuario.getEmail(),usuario.getDisplayName());
                            }else{
                                DynamicToast.makeError(getApplicationContext(), "Credenciales no validos", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }

            private boolean verificarCorreo(String email) {
                return Patterns.EMAIL_ADDRESS.matcher(email).matches();
            }

        });

        btn_registro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,ActivityRegistro.class);
                startActivity(intent);
            }
        });


        btn_invitado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Creacion de BottomDialog
                BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(MainActivity.this);
                bottomSheetDialog.setContentView(R.layout.layout_invitados);
                bottomSheetDialog.setCanceledOnTouchOutside(true);

                //Inicializacion de objetos
                Button btn_Ingresar_invitado = bottomSheetDialog.findViewById(R.id.Btn_iniciar_invitado_layout);
                EditText NameInvitado = bottomSheetDialog.findViewById(R.id.name_Invitado);

                //Variables Para verificacion
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(MainActivity.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                btn_Ingresar_invitado.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (NameInvitado.getText().toString().isEmpty()){
                            DynamicToast.makeError(getApplicationContext(), "Existen campos vacios", Toast.LENGTH_LONG).show();
                        }else if(networkInfo != null && networkInfo.isConnected()){//Guardamos informacion en bd
                            enviar_aBD(NameInvitado.getText().toString());
                            iraMenu("0","0",NameInvitado.getText().toString());
                        }else{
                            iraMenu("0","0",NameInvitado.getText().toString());
                        }
                    }
                });
                bottomSheetDialog.show();
            }
        });

        btnIniciarG.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
                GoogleClient.signOut();
            }
        });


    }


    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()) {
            super.finish();
        }else {
            DynamicToast.makeWarning(getApplicationContext(), "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }


    @Override
    protected void onResume() {
        super.onResume();
        InputPass.setText("");
    }


    private void iraMenu(String Uid, String Email, String name){
        Intent intent = new Intent(MainActivity.this,ActivityBienvenida.class);
        intent.putExtra("uid",Uid);
        intent.putExtra("email",Email);
        intent.putExtra("name",name);
        startActivity(intent);
    }

    private void intanciarAutenticacion() {
        mAuth = FirebaseAuth.getInstance(); //Instancia para Autenticacion
        db = FirebaseFirestore.getInstance();           //Instancia para base de datos
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        GoogleClient = GoogleSignIn.getClient(this, gso);
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

    private void VerificarSession() {
        SharedPreferences prefs = getSharedPreferences("mySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();

        String email = prefs.getString("email",null);
        String providerId = prefs.getString("providerId",null);
        String Uid = prefs.getString("uid",null);
        String nameSesion = prefs.getString("name",null);

        if (email!=null && providerId!=null){
            iraMenu(Uid,email,nameSesion);
            myEdit.apply();
        }
    }


    /*      Inicio de Fnciones para Autenticacion con google        */

    private void signIn() {
        Intent signInIntent = GoogleClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {// Google Sign In was successful, authenticate with Firebase
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
                        if (task.isSuccessful()) { // Sign in success, update UI with the signed-in user's information
                            DynamicToast.makeSuccess(getApplicationContext(), "Bienvenido "+account.getDisplayName(), Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);

                            enviar_aBDG(account.getId(),"GOOGLE",account.getEmail(),account.getDisplayName());
                            guardarSesion(account.getId(),"GOOGLE",account.getEmail(),account.getDisplayName());
                            iraMenu(account.getDisplayName(),account.getEmail(),account.getId());
                        } else { // If sign in fails, display a message to the user.
                            DynamicToast.makeError(getApplicationContext(), "Hubo un error al iniciar sesion, verifique su conexion a internet", Toast.LENGTH_LONG).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
    }
    /*      Fin de Fnciones para Autenticacion con google       */


    //NEW DOCUMENT: USUARIO AUTENTICADO A FIREBASE
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
                        //DynamicToast.makeSuccess(getApplicationContext(), "Informacion Agregada Correctamente", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        DynamicToast.makeError(getApplicationContext(), "Hubo un error al almacenar la info", Toast.LENGTH_LONG).show();
                    }
                });
    }

    //NEW DOCUMENT: Usuario invitados en Firebase
    private void enviar_aBD(String UserName){
        Map<String, Object> user_invitado = new HashMap<>();
        user_invitado.put("nombre", UserName);
        user_invitado.put("fecha_reg", DateFormat.getDateTimeInstance().format(new Date()));

        //DocumentReference idDocumento = db.collection("user_invitados").document();
        db.collection("user_invitados")
                .add(user_invitado)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        DynamicToast.makeSuccess(getApplicationContext(), "Bienvenido "+UserName, Toast.LENGTH_LONG).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        DynamicToast.makeError(getApplicationContext(), "Hubo un error, intente de nuevo", Toast.LENGTH_LONG).show();
                    }
                });
    }

}

