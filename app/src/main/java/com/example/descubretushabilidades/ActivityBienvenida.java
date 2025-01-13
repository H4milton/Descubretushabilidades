package com.example.descubretushabilidades;

//import android.support.v7.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.Objects;

public class ActivityBienvenida extends AppCompatActivity {
    private ImageButton btnAtras, btnSalir;
    private Button btnTest, btnDirectorio,btnMisResultados;

    private int INTERVALO = 2000;
    private long tiempoPrimerClick = 0;

    private String uid="0", NameUser,EmailUser;
    private FirebaseFirestore db;

    private boolean ExisteTest=false, TestNuevo=false;
    private int a1,a2,a3,a4,a5;

    DBHelper dbHelper;
    SQLiteDatabase db_local;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //getWindow().setStatusBarColor(Color.WHITE);

        //CREACION DE BD PARA ESCUELAS Y CARRERAS
        btnAtras = findViewById(R.id.flecha);
        btnSalir = findViewById(R.id.btnClose_Wel);
        btnTest=findViewById(R.id.btn_test);
        btnDirectorio=findViewById(R.id.btn_directorio);
        btnMisResultados=findViewById(R.id.btn_estadisticas);

        //OBTENER DATOS DE USUARIO DE INTENT
        Bundle DatosUser=this.getIntent().getExtras();
        uid=DatosUser.getString("uid");
        NameUser=DatosUser.getString("name");
        EmailUser=DatosUser.getString("email");

        if (!uid.equals("0")){
            btnMisResultados.setEnabled(false);
            btnMisResultados.setText("MIS RESULTADOS (Cargando...)");
            consultarTest();
        }

        cargarbd();

        //FUNCIONES CLICK-LISTENER
        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                borrarDatosSesion();
                finish();
            }
        });

        btnSalir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrarDatosSesion();
                finish();
            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ExisteTest) {
                    Preguntartest();
                } else {
                    iraTest();
                    TestNuevo=true;
                }
            }
        });

        btnDirectorio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActivityBienvenida.this,Carrera_EscuelaActivity.class);
                intent.putExtra("idCarrera","0");
                intent.putExtra("CarreraName","0");
                startActivity(intent);
            }
        });

        btnMisResultados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uid.equals("0")){
                    DynamicToast.makeError(getApplicationContext(), "Registrese para guardar sus resultados", Toast.LENGTH_LONG).show();
                    btnMisResultados.setEnabled(false);
                }else if (ExisteTest){
                    Intent intent = new Intent(ActivityBienvenida.this, ActivityResultTest.class);
                    intent.putExtra("Totalarea1",a1);
                    intent.putExtra("Totalarea2",a2);
                    intent.putExtra("Totalarea3",a3);
                    intent.putExtra("Totalarea4",a4);
                    intent.putExtra("Totalarea5",a5);
                    startActivity(intent);
                }else {
                    DynamicToast.makeError(getApplicationContext(), "No exiten registro, realice el test.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }



    private void cargarbd() {
        dbHelper = new DBHelper(ActivityBienvenida.this);
        db_local = dbHelper.getWritableDatabase();
        if (db_local!=null){
            dbHelper.onUpgrade(db_local,2,3);
            dbHelper.Registrar_carreras();
            dbHelper.Registar_relacion();
            dbHelper.Registar_Escuelas();
            //DynamicToast.makeSuccess(getApplicationContext(), "BD CREADA EXITOSAMENTE", Toast.LENGTH_LONG).show();
        }
    }

    private void iraTest() {
        Intent intent = new Intent(ActivityBienvenida.this,ActivityTest.class);
        intent.putExtra("uid",uid);
        intent.putExtra("email",EmailUser);
        intent.putExtra("name",NameUser);
        startActivity(intent);
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private void Preguntartest() {
        MaterialAlertDialogBuilder builder =  new MaterialAlertDialogBuilder(ActivityBienvenida.this);
        builder.setTitle("Existe un test ya hecho");
        builder.setMessage("Continuar borrará tus resultados anteriores, ¿deseas continuar?");
        builder.setBackground(getResources().getDrawable(R.drawable.bottom_sheet_background));
        builder.setIcon(R.drawable.ic_no);
        builder.setPositiveButton("SI", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                iraTest();
                TestNuevo=true;
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
    }

    private void consultarTest(){
        //OBTENER DATOS DE USUARIO DESDE SESION GUARDADA
        SharedPreferences prefs = getSharedPreferences("mySharedPref",MODE_PRIVATE);
        String uid2 = prefs.getString("uid",null);

        db = FirebaseFirestore.getInstance();
        db.collection("puntaje_areas")
                .document(uid2)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot document) {
                        if (document.exists()){
                            btnMisResultados.setEnabled(true);
                            btnMisResultados.setText("VER MIS RESULTADOS");
                            ExisteTest=true;
                            a1=Integer.parseInt(Objects.requireNonNull(document.getString("a1")));
                            a2=Integer.parseInt(Objects.requireNonNull(document.getString("a2")));
                            a3=Integer.parseInt(Objects.requireNonNull(document.getString("a3")));
                            a4=Integer.parseInt(Objects.requireNonNull(document.getString("a4")));
                            a5=Integer.parseInt(Objects.requireNonNull(document.getString("a5")));
                        }else{
                            ExisteTest=false;
                            btnMisResultados.setEnabled(true);
                            btnMisResultados.setText("VER MIS RESULTADOS");
                        }
                    }
                });
    }

    private void borrarDatosSesion() {
        SharedPreferences prefs = getSharedPreferences("mySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();
        myEdit.clear();
        myEdit.apply();
    }

    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            borrarDatosSesion();
            return;
        }else {
            DynamicToast.makeWarning(getApplicationContext(), "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();
    }

    public void onResume() {
        super.onResume();
        if (!uid.equals("0")&&TestNuevo==true){
            consultarTest();
            //DynamicToast.makeSuccess(getApplicationContext(), "Hizo Test por tanto hay que recargar", Toast.LENGTH_SHORT).show();
        }
    }


}