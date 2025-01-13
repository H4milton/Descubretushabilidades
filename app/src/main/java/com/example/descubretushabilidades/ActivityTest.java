package com.example.descubretushabilidades;

//import androidx.activity.result.ActivityResult;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.pranavpandey.android.dynamic.toasts.DynamicToast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivityTest extends AppCompatActivity {

    private ImageButton btnAtras;
    private Button btn_si,btn_no;
    private TextView noPregunta, Pregunta;
    ArrayList<listPreguntas> listPreguntasTest;

    private int Totala1=0,Totala2=0,Totala3=0,Totala4=0,Totala5=0,idArea,PosicionActual;
    private int INTERVALO = 2000; //2 segundos para salir
    private long tiempoPrimerClick = 0;

    private String Bun_uid, Bun_NameUser,Bun_EmailUser;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        btnAtras = findViewById(R.id.btnBack_test);
        btn_si = findViewById(R.id.btnSi_test);
        btn_no = findViewById(R.id.btnNo_test);
        noPregunta = findViewById(R.id.textView_NoPregunta);
        Pregunta = findViewById(R.id.textView_Pregunta);

        listPreguntasTest = new ArrayList<>();
        PosicionActual=0;
        CargarPreguntas();
        AsignarPregunta(PosicionActual);

        Bundle DatosUser=this.getIntent().getExtras();
        Bun_uid=DatosUser.getString("uid");
        Bun_NameUser=DatosUser.getString("name");
        Bun_EmailUser=DatosUser.getString("email");

        //Instancia para conexion a firestore
        db = FirebaseFirestore.getInstance();

        btn_si.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PosicionActual!=79){
                    interesArea(listPreguntasTest.get(PosicionActual).getId_area());
                    PosicionActual++;
                    AsignarPregunta(PosicionActual);
                }else {
                    interesArea(listPreguntasTest.get(PosicionActual).getId_area());
                    IraResultados();
                }
            }
        });

        btn_no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PosicionActual!=79){
                    PosicionActual++;
                    AsignarPregunta(PosicionActual);
                }else {
                    interesArea(listPreguntasTest.get(PosicionActual).getId_area());
                    IraResultados();
                }
            }
        });


        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (PosicionActual>0){
                    DynamicToast.makeError(getApplicationContext(), "Opcion no valido, termine el test", Toast.LENGTH_SHORT).show();
                }else{
                finish();
                }
            }
        });


    }//Fin oncreate

    private void IraResultados(){
        SharedPreferences prefs = getSharedPreferences("mySharedPref",MODE_PRIVATE);
        SharedPreferences.Editor myEdit = prefs.edit();
        String Uid = prefs.getString("uid",null);
        String NameColeccion="puntaje_areas";

        if (Bun_uid.equals("0")){
            Uid=Bun_NameUser;
            NameColeccion="puntaje_areas_invitados";
        }
        enviar_aBD(Uid,Integer.toString(Totala1),Integer.toString(Totala2),Integer.toString(Totala3),Integer.toString(Totala4),Integer.toString(Totala5),NameColeccion);
        Intent intent = new Intent(ActivityTest.this, ActivityResultTest.class);
        intent.putExtra("Totalarea1",Totala1);
        intent.putExtra("Totalarea2",Totala2);
        intent.putExtra("Totalarea3",Totala3);
        intent.putExtra("Totalarea4",Totala4);
        intent.putExtra("Totalarea5",Totala5);
        startActivity(intent);
        finish();
    }

    private void AsignarPregunta(int posActual) {
        noPregunta.setText("PREGUNTA NO "+(posActual+1));
        Pregunta.setText(listPreguntasTest.get(posActual).getPregunta());
    }

    private void interesArea(int area) {
        switch (area){
            case 1:
                Totala1++;
                break;
            case 2:
                Totala2++;
                break;
            case 3:
                Totala3++;
                break;
            case 4:
                Totala4++;
                break;
            case 5:
                Totala5++;
                break;
        }
    }


    private void CargarPreguntas() {
        listPreguntasTest.add(new listPreguntas(4, "Diseñar programas de computación y explorar nuevas aplicaciones tecnológicas para uso del internet."));
        listPreguntasTest.add(new listPreguntas(5, "Criar, cuidar y tratar animales domésticos y de campo."));
        listPreguntasTest.add(new listPreguntas(5, "Investigar sobre áreas verdes, medio ambiente y cambios climáticos."));
        listPreguntasTest.add(new listPreguntas(1, "Ilustrar, dibujar y animar digitalmente."));
        listPreguntasTest.add(new listPreguntas(3, "Seleccionar, capacitar y motivar al personal de una organización/empresa."));
        listPreguntasTest.add(new listPreguntas(2, "Realizar excavaciones para descubrir restos del pasado."));
        listPreguntasTest.add(new listPreguntas(4, "Resolver problemas de cálculo para construir un puente."));
        listPreguntasTest.add(new listPreguntas(5, "Diseñar cursos para enseñar a la gente sobre temas de salud e higiene."));
        listPreguntasTest.add(new listPreguntas(1, "Tocar un instrumento y componer música."));
        listPreguntasTest.add(new listPreguntas(3, "Planificar cuáles son las metas de una organización pública o privada a mediano y largo plazo."));
        listPreguntasTest.add(new listPreguntas(4, "Diseñar y planificar la producción masiva de artículos como muebles, autos, equipos de oficina, empaques y envases para alimentos y otros."));
        listPreguntasTest.add(new listPreguntas(1, "Diseñar logotipos y portadas de una revista."));
        listPreguntasTest.add(new listPreguntas(2, "Organizar eventos y atender a sus asistentes."));
        listPreguntasTest.add(new listPreguntas(5, "Atender la salud de personas enfermas."));
        listPreguntasTest.add(new listPreguntas(3, "Controlar ingresos y egresos de fondos y presentar el balance final de una institución."));
        listPreguntasTest.add(new listPreguntas(5, "Hacer experimentos con plantas (frutas, árboles, flores)."));
        listPreguntasTest.add(new listPreguntas(4, "Concebir planos para viviendas, edificios y ciudadelas."));
        listPreguntasTest.add(new listPreguntas(4, "Investigar y probar nuevos productos farmacéuticos."));
        listPreguntasTest.add(new listPreguntas(3, "Hacer propuestas y formular estrategias para aprovechar las relaciones económicas entre dos países."));
        listPreguntasTest.add(new listPreguntas(1, "Pintar, hacer esculturas, ilustrar libros de arte, etcétera."));
        listPreguntasTest.add(new listPreguntas(3, "Elaborar campañas para introducir un nuevo producto al mercado."));
        listPreguntasTest.add(new listPreguntas(5, "Examinar y tratar los problemas visuales"));
        listPreguntasTest.add(new listPreguntas(2, "Defender a clientes individuales o empresas en juicios de diferente naturaleza."));
        listPreguntasTest.add(new listPreguntas(4, "Diseñar máquinas que puedan simular actividades humanas."));
        listPreguntasTest.add(new listPreguntas(2, "Investigar las causas y efectos de los trastornos emocionales."));
        listPreguntasTest.add(new listPreguntas(3, "Supervisar las ventas de un centro comercial."));
        listPreguntasTest.add(new listPreguntas(5, "Atender y realizar ejercicios a personas que tienen limitaciones físicas, problemas de lenguaje, etcétera."));
        listPreguntasTest.add(new listPreguntas(1, "Prepararse para ser modelo profesional."));
        listPreguntasTest.add(new listPreguntas(3, "Aconsejar a las personas sobre planes de ahorro e inversiones."));
        listPreguntasTest.add(new listPreguntas(4, "Elaborar mapas, planos e imágenes para el estudio y análisis de datos geográficos."));
        listPreguntasTest.add(new listPreguntas(1, "Diseñar juegos interactivos electrónicos para computadora."));
        listPreguntasTest.add(new listPreguntas(5, "Realizar el control de calidad de los alimentos."));
        listPreguntasTest.add(new listPreguntas(3, "Tener un negocio propio de tipo comercial."));
        listPreguntasTest.add(new listPreguntas(2, "Escribir artículos periodísticos, cuentos, novelas y otros."));
        listPreguntasTest.add(new listPreguntas(1, "Redactar guiones y libretos para un programa de televisión."));
        listPreguntasTest.add(new listPreguntas(3, "Organizar un plan de distribución y venta de un gran almacén."));
        listPreguntasTest.add(new listPreguntas(2, "Estudiar la diversidad cultural en el ámbito rural y urbano."));
        listPreguntasTest.add(new listPreguntas(2, "Gestionar y evaluar convenios internacionales de cooperación para el desarrollo social."));
        listPreguntasTest.add(new listPreguntas(1, "Crear campañas publicitarias."));
        listPreguntasTest.add(new listPreguntas(5, "Trabajar investigando la reproducción de peces, camarones y otros animales marinos."));
        listPreguntasTest.add(new listPreguntas(4, "Dedicarse a fabricar productos alimenticios de consumo masivo."));
        listPreguntasTest.add(new listPreguntas(2, "Gestionar y evaluar proyectos de desarrollo en una institución educativa y/o fundación."));
        listPreguntasTest.add(new listPreguntas(1, "Rediseñar y decorar espacios físicos en viviendas, oficinas y locales comerciales."));
        listPreguntasTest.add(new listPreguntas(3, "Administrar una empresa de turismo y/o agencias de viaje."));
        listPreguntasTest.add(new listPreguntas(5, "Aplicar métodos alternativos a la medicina tradicional para atender personas con dolencias de diversa índole."));
        listPreguntasTest.add(new listPreguntas(1, "Diseñar ropa para niños, jóvenes y adultos."));
        listPreguntasTest.add(new listPreguntas(5, "Investigar organismos vivos para elaborar vacunas."));
        listPreguntasTest.add(new listPreguntas(4, "Manejar y/o dar mantenimiento a dispositivos/aparatos tecnológicos en aviones, barcos, radares, etcétera."));
        listPreguntasTest.add(new listPreguntas(2, "Estudiar idiomas extranjeros –actuales y antiguos- para hacer traducción."));
        listPreguntasTest.add(new listPreguntas(1, "Restaurar piezas y obras de arte."));
        listPreguntasTest.add(new listPreguntas(4, "Revisar y dar mantenimiento a artefactos eléctricos, electrónicos y computadoras."));
        listPreguntasTest.add(new listPreguntas(2, "Enseñar a niños de 0 a 5 años."));
        listPreguntasTest.add(new listPreguntas(3, "Investigar y/o sondear nuevos mercados."));
        listPreguntasTest.add(new listPreguntas(5, "Atender la salud dental de las personas."));
        listPreguntasTest.add(new listPreguntas(2, "Tratar a niños, jóvenes y adultos con problemas psicológicos."));
        listPreguntasTest.add(new listPreguntas(3, "Crear estrategias de promoción y venta de nuevos productos ecuatorianos en el mercado internacional."));
        listPreguntasTest.add(new listPreguntas(5, "Planificar y recomendar dietas para personas diabéticas y/o con sobrepeso."));
        listPreguntasTest.add(new listPreguntas(4, "Trabajar en una empresa petrolera en un cargo técnico como control de la producción."));
        listPreguntasTest.add(new listPreguntas(3, "Administrar una empresa (familiar, privada o pública)."));
        listPreguntasTest.add(new listPreguntas(4, "Tener un taller de reparación y mantenimiento de carros, tractores, etcétera."));
        listPreguntasTest.add(new listPreguntas(4, "Ejecutar proyectos de extracción minera y metalúrgica."));
        listPreguntasTest.add(new listPreguntas(3, "Asistir a directivos de multinacionales con manejo de varios idiomas."));
        listPreguntasTest.add(new listPreguntas(2, "Diseñar programas educativos para niños con discapacidad."));
        listPreguntasTest.add(new listPreguntas(4, "Aplicar conocimientos de estadística en investigaciones en diversas áreas (social, administrativa, salud, etcétera.)"));
        listPreguntasTest.add(new listPreguntas(1, "Fotografiar hechos históricos, lugares significativos, rostros, paisajes para el área publicitaria, artística, periodística y social."));
        listPreguntasTest.add(new listPreguntas(2, "Trabajar en museos y bibliotecas nacionales e internacionales."));
        listPreguntasTest.add(new listPreguntas(1, "Ser parte de un grupo de teatro."));
        listPreguntasTest.add(new listPreguntas(1, "Producir cortometrajes, spots publicitarios, programas educativos, de ficción, etcétera."));
        listPreguntasTest.add(new listPreguntas(5, "Estudiar la influencia entre las corrientes marinas y el clima y sus consecuencias ecológicas."));
        listPreguntasTest.add(new listPreguntas(2, "Conocer las distintas religiones, su filosofía y transmitirlas a la comunidad en general."));
        listPreguntasTest.add(new listPreguntas(3, "Asesorar a inversionistas en la compra de bienes/acciones en mercados nacionales e internacionales."));
        listPreguntasTest.add(new listPreguntas(2, "Estudiar grupos étnicos, sus costumbres, tradiciones, cultura y compartir sus vivencias."));
        listPreguntasTest.add(new listPreguntas(4, "Explorar el espacio sideral, los planetas, características y componentes."));
        listPreguntasTest.add(new listPreguntas(5, "Mejorar la imagen facial y corporal de las personas aplicando diferentes técnicas."));
        listPreguntasTest.add(new listPreguntas(1, "Decorar jardines de casas y parques públicos."));
        listPreguntasTest.add(new listPreguntas(5, "Administrar y renovar menús de comidas en un hotel o restaurante."));
        listPreguntasTest.add(new listPreguntas(1, "Trabajar como presentador de televisión, locutor de radio y televisión, animador de programas culturales y concursos."));
        listPreguntasTest.add(new listPreguntas(2, "Diseñar y ejecutar programas de turismo."));
        listPreguntasTest.add(new listPreguntas(4, "Administrar y ordenar (planificar) adecuadamente la ocupación del espacio físico de ciudades, países etc., utilizando imágenes de satélite, mapas."));
        listPreguntasTest.add(new listPreguntas(3, "Organizar, planificar y administrar centros educativos."));

    }

    @Override
    public void onBackPressed(){
        if (PosicionActual>0) {
            if (tiempoPrimerClick + INTERVALO > System.currentTimeMillis()) {
                super.onBackPressed();
                return;
            } else {
                DynamicToast.makeWarning(getApplicationContext(), "Salir borrara todo su proceso, presione de nuevo para salir", Toast.LENGTH_SHORT).show();
            }
            tiempoPrimerClick = System.currentTimeMillis();
        }else{
            super.onBackPressed();
            return;
        }
    }


    private void enviar_aBD(String UserUid, String a1, String a2, String a3, String a4, String a5, String coleccion){
        Map<String, Object> ResTest = new HashMap<>();
        ResTest.put("a1", a1);
        ResTest.put("a2", a2);
        ResTest.put("a3", a3);
        ResTest.put("a4", a4);
        ResTest.put("a5", a5);
        ResTest.put("uid", UserUid);
        ResTest.put("fecha_realizado", DateFormat.getDateTimeInstance().format(new Date()));

        // Add a new document with a generated ID
        db.collection(coleccion)
                .document(UserUid)
                .set(ResTest)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        DynamicToast.makeSuccess(getApplicationContext(), "Este es el resultado del test", Toast.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        DynamicToast.makeError(getApplicationContext(), "Hubo un error, al guardar sus resultados", Toast.LENGTH_LONG).show();
                    }
                });
    }



}