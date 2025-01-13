package com.example.descubretushabilidades;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.util.ArrayList;

public class DescipAreasActivity extends AppCompatActivity {

    ArrayList<listCarreras> CarrerasListNew;
    private RecyclerView recycler;
    private Integer opcion;
    private TextView TitCarrera;
    private ImageButton btnBack;
    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descip_areas);

        CarrerasListNew = new ArrayList<listCarreras>();
        recycler = findViewById(R.id.ListadeCarreras);
        TitCarrera = findViewById(R.id.TituloCarrea);
        btnBack = findViewById(R.id.btnBack_Des);

        Bundle options=this.getIntent().getExtras();
        opcion=options.getInt("area");

        DBHelper dbHelper = new DBHelper(DescipAreasActivity.this);


        switch (opcion){
            case 1:
                TitCarrera.setText(R.string.Area1);
                CarrerasListNew=dbHelper.cargarCarreras("AC");
                break;
            case 2:
                TitCarrera.setText(R.string.Area2);
                CarrerasListNew=dbHelper.cargarCarreras("CS");
                break;
            case 3:
                TitCarrera.setText(R.string.Area3);
                CarrerasListNew=dbHelper.cargarCarreras("AF");
                break;
            case 4:
                TitCarrera.setText(R.string.Area4);
                CarrerasListNew=dbHelper.cargarCarreras("CT");
                break;
            case 5:
                TitCarrera.setText(R.string.Area5);
                CarrerasListNew=dbHelper.cargarCarreras("BIO");
                break;
        }

        recycler.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        AdapterListCarreras adapter = new AdapterListCarreras(CarrerasListNew);

        adapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idCarrera=CarrerasListNew.get(recycler.getChildAdapterPosition(view)).getIdCarr();
                DBHelper dbHelper = new DBHelper(DescipAreasActivity.this);
                ArrayList<listEscuela> MisEscuelas;
                MisEscuelas = dbHelper.BuscarEscuelas(idCarrera);
                if (MisEscuelas!=null){
                    Intent intent = new Intent(DescipAreasActivity.this,Carrera_EscuelaActivity.class);
                    intent.putExtra("idCarrera",idCarrera);
                    intent.putExtra("CarreraName",CarrerasListNew.get(recycler.getChildAdapterPosition(view)).getCarreraName());
                    startActivity(intent);
                }else{
                    DynamicToast.makeError(getApplicationContext(),"Aun no se tiene colegios o institutos para esta carrera", Toast.LENGTH_SHORT).show();
                }

            }
        });
        recycler.setAdapter(adapter);



        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DescipAreasActivity.super.onBackPressed();
            }
        });

    }
}