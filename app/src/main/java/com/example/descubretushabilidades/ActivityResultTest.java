package com.example.descubretushabilidades;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


public class ActivityResultTest extends AppCompatActivity {

    private PieChart pieA1,pieA2,pieA3,pieA4,pieA5;
    private LinearLayout a1,a2,a3,a4,a5;
    private int Totala1=0,Totala2=0,Totala3=0,Totala4=0,Totala5=0;
    private ImageButton btnHome, brnClose;
    private TextView Tita1, Tita2, Tita3, Tita4, Tita5;

    int INTERVALO = 2000; //2 segundos para salir
    long tiempoPrimerClick = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_test);

        btnHome = findViewById(R.id.btnBack_test);
        brnClose = findViewById(R.id.btnClose_res);


        pieA1 = findViewById(R.id.piechart_a1);
        pieA2 = findViewById(R.id.piechart_a2);
        pieA3 = findViewById(R.id.piechart_a3);
        pieA4 = findViewById(R.id.piechart_a4);
        pieA5 = findViewById(R.id.piechart_a5);


        a1 = findViewById(R.id.Llayouta1);
        a2 = findViewById(R.id.Llayouta2);
        a3 = findViewById(R.id.Llayouta3);
        a4 = findViewById(R.id.Llayouta4);
        a5 = findViewById(R.id.Llayouta5);

        Tita1 = findViewById(R.id.textTituloArea1);
        Tita2 = findViewById(R.id.textTituloArea2);
        Tita3 = findViewById(R.id.textTituloArea3);
        Tita4 = findViewById(R.id.textTituloArea4);
        Tita5 = findViewById(R.id.textTituloArea5);

        Bundle Areoptions=this.getIntent().getExtras();
        Totala1=Areoptions.getInt("Totalarea1");
        Totala2=Areoptions.getInt("Totalarea2");
        Totala3=Areoptions.getInt("Totalarea3");
        Totala4=Areoptions.getInt("Totalarea4");
        Totala5=Areoptions.getInt("Totalarea5");

        //100/16=6.25
        Tita1.setText((6.25*Totala1)+"%");
        Tita2.setText((6.25*Totala2)+"%");
        Tita3.setText((6.25*Totala3)+"%");
        Tita4.setText((6.25*Totala4)+"%");
        Tita5.setText((6.25*Totala5)+"%");

        setData(Totala1,Totala2,Totala3,Totala4,Totala5);

        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityResultTest.this,DescipAreasActivity.class);
                intent.putExtra("area",1);
                startActivity(intent);
            }
        });

        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityResultTest.this,DescipAreasActivity.class);
                intent.putExtra("area",2);
                startActivity(intent);
            }
        });

        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityResultTest.this,DescipAreasActivity.class);
                intent.putExtra("area",3);
                startActivity(intent);
            }
        });

        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityResultTest.this,DescipAreasActivity.class);
                intent.putExtra("area",4);
                startActivity(intent);
            }
        });

        a5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ActivityResultTest.this,DescipAreasActivity.class);
                intent.putExtra("area",5);
                startActivity(intent);
            }
        });

        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        brnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences prefs = getSharedPreferences("mySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = prefs.edit();
                myEdit.clear();
                myEdit.apply();
                Intent intent = new Intent(ActivityResultTest.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void setData(int ta1, int ta2,int ta3,int ta4,int ta5){

        pieA1.addPieSlice(new PieModel("Si",ta1,Color.parseColor("#80D8FF")));
        pieA1.addPieSlice(new PieModel("No",(16-ta1),Color.parseColor("#00FFFFFF")));
        pieA1.startAnimation();

        pieA2.addPieSlice(new PieModel("Si",ta2,Color.parseColor("#80D8FF")));
        pieA2.addPieSlice(new PieModel("No",(16-ta2),Color.parseColor("#00FFFFFF")));
        pieA2.startAnimation();

        pieA3.addPieSlice(new PieModel("Si",ta3,Color.parseColor("#80D8FF")));
        pieA3.addPieSlice(new PieModel("No",(16-ta3),Color.parseColor("#00FFFFFF")));
        pieA3.startAnimation();

        pieA4.addPieSlice(new PieModel("Si",ta4,Color.parseColor("#80D8FF")));
        pieA4.addPieSlice(new PieModel("No",(16-ta4),Color.parseColor("#00FFFFFF")));
        pieA4.startAnimation();

        pieA5.addPieSlice(new PieModel("Si",ta5,Color.parseColor("#80D8FF")));
        pieA5.addPieSlice(new PieModel("No",(16-ta5),Color.parseColor("#00FFFFFF")));
        pieA5.startAnimation();

    }

    @Override
    public void onBackPressed(){
        if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()){
            super.onBackPressed();
            return;
        }else {
            DynamicToast.makeWarning(getApplicationContext(), "Presione de nuevo para salir", Toast.LENGTH_SHORT).show();
        }
        tiempoPrimerClick = System.currentTimeMillis();

    }//Fin onBackPressed
}