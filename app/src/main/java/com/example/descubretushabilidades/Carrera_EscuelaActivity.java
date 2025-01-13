package com.example.descubretushabilidades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Carrera_EscuelaActivity extends AppCompatActivity {

    private ArrayList<listEscuela> ListaEscuelas;
    private RecyclerView recyclerEscuelas;
    private String idCarrera, CarreraName;
    private ImageButton btnBack;
    private TextView Titulo, Subtitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carrera_escuela);

        Titulo = findViewById(R.id.textView1_TituDirectorio);
        Subtitulo = findViewById(R.id.textView_sub_directorio);

        ListaEscuelas = new ArrayList<listEscuela>();
        DBHelper dbHelper = new DBHelper(Carrera_EscuelaActivity.this);

        Bundle options=this.getIntent().getExtras();
        idCarrera=options.getString("idCarrera");
        CarreraName=options.getString("CarreraName");

        if (idCarrera.equals("0")){
            ListaEscuelas = dbHelper.Cargar_Escuelas();
        }else{
            ListaEscuelas = dbHelper.BuscarEscuelas(idCarrera);
            Titulo.setText("INSTITUTO O COLEGIO PARA");
            Subtitulo.setText(CarreraName);
        }

        btnBack = findViewById(R.id.btnBack_Carr_escuela);

        recyclerEscuelas = findViewById(R.id.ListadeEscuelaParaCarrera);
        recyclerEscuelas.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        AdapterListCarrera_Escuela adapter = new AdapterListCarrera_Escuela(ListaEscuelas);
        recyclerEscuelas.setAdapter(adapter);


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carrera_EscuelaActivity.super.onBackPressed();
            }
        });

    }
}