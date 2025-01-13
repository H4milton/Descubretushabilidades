package com.example.descubretushabilidades;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "db_mishabilidades.db";
    public static final String TABLE_CARRERAS = "tb_carreras";
    public static final String TABLE_SCHOOL = "tb_school";
    public static final String TABLE_CARRERAS_AND_SCHOOL = "tb_carr_school";
    Context context;

    public DBHelper(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE "+TABLE_CARRERAS+"(id_carrera TEXT,carrera TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_SCHOOL+"(id_school TEXT,name_escuela TEXT,tel_escuela TEXT,tel2_escuela TEXT,dir_escuela TEXT,sector_escuela TEXT,url_escuela TEXT)");
        db.execSQL("CREATE TABLE "+TABLE_CARRERAS_AND_SCHOOL+"(id_carrera TEXT, id_school TEXT)");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARRERAS);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_SCHOOL);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_CARRERAS_AND_SCHOOL);
        onCreate(db);
    }

    private void insert_carreras(String idCarr, String carreraName){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues datos = new ContentValues();
        datos.put("id_carrera",idCarr);
        datos.put("carrera",carreraName);
        long result = db.insert(TABLE_CARRERAS,null,datos);
        db.close();
    }

    public void Registrar_carreras(){
        //AREA 1
        insert_carreras("AC1","Bachillerato en Diseño Grafico");
        insert_carreras("AC2","Bachillerato en construcción con dibujo Técnico y Dibujo computarizado");
        insert_carreras("AC4","Dibujo de Construcción");
        insert_carreras("AC5","Diseño y decoración de interiores");
        insert_carreras("AC6","Diseño de Modas");
        insert_carreras("AC7","Artes Plásticas (Pintura, Escultura, Danza, Teatro, Artesanía, Cerámica)");
        insert_carreras("AC8","Restauración y Museología");
        insert_carreras("AC9","Modelaje");
        insert_carreras("AC10","Fotografia, Fotografia Digital");
        insert_carreras("AC11","Gestión Gráfica y Publicitaria");
        insert_carreras("AC12","Locución y Publicidad");
        insert_carreras("AC13","Actuación");
        insert_carreras("AC14","Camarografía");
        insert_carreras("AC15","Arte Industrial");
        insert_carreras("AC16","Producción Audiovisual y Multimedia");
        insert_carreras("AC17","Comunicación y Producción en Radio y Televisión");

        //AREA 2
        insert_carreras("CS1","Magisterio Parvulario");
        insert_carreras("CS2","Bachillerato en Turismo");
        insert_carreras("CS3","Magisterio de Educación Infantil Bilingüe Intercultural ");
        insert_carreras("CS4","Bachillerato en Ciencias y Letras con Orientación en Educación");
        insert_carreras("CS5","Magisterio en Educación Bilingüe Intercultural");
        insert_carreras("CS7","Psicología ");
        insert_carreras("CS8","Historia y Geografía");
        insert_carreras("CS9","Periodismo");
        insert_carreras("CS10","Arqueología");
        insert_carreras("CS11","Gestión Social y Desarrollo");
        insert_carreras("CS12","Comunicación y Publicidad");
        insert_carreras("CS13","Linguística");
        insert_carreras("CS14","Bibliotecología");
        insert_carreras("CS15","Musica");
        insert_carreras("CS16","Comunicación Social con mención en Marketing y Gestión de Empresas");
        insert_carreras("CS17","Redacción Creativa y Publicitaria");
        insert_carreras("CS18","Relaciones Públicas y Comunicación Organizacional");
        insert_carreras("CS19","Hotelería y Turismo");
        insert_carreras("CS20","Teología");
        insert_carreras("CS21","Institución Sacerdotal");

        //AREA 3
        insert_carreras("AF1","Secretariado y Oficinista");
        insert_carreras("AF2","Perito en Administración Pública");
        insert_carreras("AF3","Perito en administración de Empresas");
        insert_carreras("AF4","Perito Contador con Orientación en Computación");
        insert_carreras("AF6","Bachillerato en Computación con Orientación Comercial");
        insert_carreras("AF7","Secretariado Bilingüe");
        insert_carreras("AF9","Perito Contador");
        insert_carreras("AF11","Administración de Empresas Ecoturísticas y de Hoteleras");
        insert_carreras("AF12","Gestion y Negocios Internacionales");
        insert_carreras("AF13","Gestión empresarial");


        //AREA 4
        insert_carreras("CT2","Imagen y sonido");
        insert_carreras("CT3","Biotecnología Ambiental");
        insert_carreras("CT4","Carreras Militares (Marina, Aviación, Ejercito)");
        insert_carreras("CT5","Programación y desarrollo de sistemas");
        insert_carreras("CT6","Tecnología en informatica educativa");
        insert_carreras("CT7","Bachillerato Ciencias y Letras");
        insert_carreras("CT8","Bachillerato en Ciencias y Letras con Orientación en Computación");
        insert_carreras("CT10","Bachillerato en Computación con Orientación Cientifica ");
        insert_carreras("CT11","Perito en electrónica");
        insert_carreras("CT12","Perito en Electricidad Industrial");
        insert_carreras("CT13","Mecánica Automotriz");
        insert_carreras("CT14","Bachillerato en computación");

        //AREA 5
        insert_carreras("BIO1","Bachillerato en Ciencias y Letras con Orientación en Ciencias Biológicas");
        insert_carreras("BIO2","Perito Agrónomo");
        insert_carreras("BIO3","Especialidad de auxiliar Agropecuario");
        insert_carreras("BIO4","Bachillerato Con orientación en Educación Física");
        insert_carreras("BIO7","Bachillerato Productividad y Desarrollo");
        insert_carreras("BIO8","Bachillerato Mecanica Automotriz");
        insert_carreras("BIO9","Tecnico en Desarrollo Comunitario");
        insert_carreras("BIO10","Medicina");
        insert_carreras("BIO11","Ciencias ambientales");
        insert_carreras("BIO12","Zootecnia");
        insert_carreras("BIO13","Veterinaria");
        insert_carreras("BIO14","Nutrición y Estetica");
        insert_carreras("BIO15","Cosmetología");
        insert_carreras("BIO16","Enfermeria");
        insert_carreras("BIO17","Obstetricia");
        insert_carreras("BIO18","Horticultura y Fruticultura");
        insert_carreras("BIO19","Gastronomia");
        insert_carreras("BIO20","Chef");
        insert_carreras("BIO21","Cultura Fisica");
        insert_carreras("BIO22","Deporte y Rehabilitación");
    }

    private void insertar_escuelas(String id, String nombre, String tel1, String tel2, String dir, String sector , String url){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
            ContentValues datos = new ContentValues();
            datos.put("id_school",id);
            datos.put("name_escuela",nombre);
            datos.put("tel_escuela",tel1);
            datos.put("tel2_escuela",tel2);
            datos.put("dir_escuela",dir);
            datos.put("sector_escuela",sector);
            datos.put("url_escuela",url);
            long result = db.insert(TABLE_SCHOOL,null,datos);
            db.close();
    }

    public void Registar_Escuelas(){
        insertar_escuelas("C01","Centro Educativo de Formación Tecnica (Ceftec Sololá)","42291143","58255240","7a, Av. 7-02 Zona 2 Barrio El Calvario, Sololá","Privada","https://www.facebook.com/ceftec.solola.14");
        insertar_escuelas("C02","Colegio Integral Sololateco","77623709","77624564","6a. Av. 6-37 Zona 2 Barrio El Calvario, Solola","Privada","https://www.facebook.com/educacioncolegiointegral");
        insertar_escuelas("C03","Colegio Maya Tzolojya","51597561","","Km 136 , Caserío Central aldea El Tablón, Sololá","Privada","https://www.facebook.com/profile.php?id=100005898434951");
        insertar_escuelas("C04","Colegio Cientifico Montessori Sololá","77623114","","8va. Av. Camino a Justo Rufino Barrios, Sololá","Privada","https://www.facebook.com/Colegio-Cient%C3%ADfico-Montessori-Solol%C3%A1-1406292876300797");
        insertar_escuelas("C05","Escuela Ciencias Comerciales","77624233","","5 Avenida, Bario el Carmen, a 1 cuadra del Mercado Centro Comercial, Sololá","Pública","https://www.facebook.com/profile.php?id=100012213735201");
        insertar_escuelas("C06","Escuela Ciencias de la Computación","77625180","54343199","3a. Calle 1-30 Zona 1, Barrio San Antonio, Sololá","Privada","https://www.ecc.edu.gt/");
        insertar_escuelas("C07","Escuela de Formación Agricola Solola","33048676","58331218","Caserío Molino Belén, Cantón Sacsiguán 07001-Sololá","Pública","https://www.facebook.com/EfaSololaAgronomia");
        insertar_escuelas("C08","Escuela Normal Educación Física","77625207","","5ta. Ave. 2-28 Zona 1, Barrio san antonio, a un costado del Rastro Municipal, Sololá","Pública","https://www.facebook.com/Escuela-Normal-De-Educaci%C3%B3n-F%C3%ADsica-Solol%C3%A1-142271406321098");
        insertar_escuelas("C09","Escuela Normal Privada de Sololá","77624433","","Colonia Miralinda a 200 metros de Estadio Xamba 94, Sololá","Privada","https://www.facebook.com/escuelanormal.privadasolola");
        insertar_escuelas("C10","Guauhtemallan LTB","77621955","46535439","7a. Calle 3-25 Zona 1, Barrio San Antonio, Sololá","Privada","https://www.facebook.com/cuauhtemallan.desolola");
        insertar_escuelas("C11","Monte Sión Colegio","77923832","","6a. Av. Zona 2 Barrio El Calvario, Sololá","Privada","https://www.facebook.com/Monte-Sion-Colegio-334952466623064");

    }

    private void insertar_cr_sch(String idSchool, String idCarrera){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues datos = new ContentValues();
        datos.put("id_carrera",idCarrera);
        datos.put("id_school",idSchool);
        long result = db.insert(TABLE_CARRERAS_AND_SCHOOL,null,datos);
        db.close();
        //Log.d("insertar realcion","se ingreso a la funcion");
    }

    public void Registar_relacion(){
        insertar_cr_sch("C01", "CT7");
        insertar_cr_sch("C02", "AC1");
        insertar_cr_sch("C02", "CS2");
        insertar_cr_sch("C02", "CS1");
        insertar_cr_sch("C02", "AF2");
        insertar_cr_sch("C02", "AF1");
        insertar_cr_sch("C03", "BIO1");
        insertar_cr_sch("C03", "CT8");
        insertar_cr_sch("C03", "CS4");
        insertar_cr_sch("C03", "CS3");
        insertar_cr_sch("C04", "CT8");
        insertar_cr_sch("C04", "AF3");
        insertar_cr_sch("C05", "AF4");
        insertar_cr_sch("C06", "CT8");
        insertar_cr_sch("C06", "CT10");
        insertar_cr_sch("C06", "AF6");
        insertar_cr_sch("C06", "AC2");
        insertar_cr_sch("C06", "AF4");
        insertar_cr_sch("C07", "BIO3");
        insertar_cr_sch("C07", "BIO2");
        insertar_cr_sch("C08", "BIO4");
        insertar_cr_sch("C09", "BIO1");
        insertar_cr_sch("C09", "CS4");
        insertar_cr_sch("C09", "CS5");
        insertar_cr_sch("C10", "CT14");
        insertar_cr_sch("C10", "AC4");
        insertar_cr_sch("C10", "CT13");
        insertar_cr_sch("C10", "AF9");
        insertar_cr_sch("C10", "CT12");
        insertar_cr_sch("C10", "CT11");
        insertar_cr_sch("C10", "AF7");
        insertar_cr_sch("C10", "AF1");
        insertar_cr_sch("C11", "BIO8");
        insertar_cr_sch("C11", "BIO7");
        insertar_cr_sch("C11", "AF7");
        insertar_cr_sch("C11", "BIO9");
    }


    //rETORNO DE INFORMACIÓN
    public ArrayList<listCarreras> cargarCarreras(String id){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<listCarreras> carreras = new ArrayList<>();
        Cursor cursorCarrerras = null;

        cursorCarrerras = db.rawQuery("SELECT *FROM "+TABLE_CARRERAS+" WHERE id_carrera LIKE"+"'%"+id+"%"+"'",null);

        if (cursorCarrerras.moveToFirst()){
            do{
                carreras.add(new listCarreras(
                        cursorCarrerras.getString(0)
                        ,cursorCarrerras.getString(1)
                ));
            }while(cursorCarrerras.moveToNext());
        }
        cursorCarrerras.close();
        return carreras;
    }

    public ArrayList<listEscuela> Cargar_Escuelas(){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<listEscuela> carreras = new ArrayList<>();
        Cursor cursorCarrerras = null;

        cursorCarrerras = db.rawQuery("SELECT *FROM "+TABLE_SCHOOL,null);

        if (cursorCarrerras.moveToFirst()){
            do{
                carreras.add(new listEscuela(
                        cursorCarrerras.getString(0)
                        ,cursorCarrerras.getString(1)
                        ,cursorCarrerras.getString(2)
                        ,cursorCarrerras.getString(3)
                        ,cursorCarrerras.getString(4)
                        ,cursorCarrerras.getString(5)
                        ,cursorCarrerras.getString(6)
                ));
            }while(cursorCarrerras.moveToNext());
        }
        cursorCarrerras.close();
        return carreras;
    }

    public ArrayList<listEscuela> BuscarEscuelas(String idCarrera){
        DBHelper dbHelper = new DBHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        ArrayList<listEscuela> Escuela = new ArrayList<>();
        Cursor cursorCarrerras = null;

        //cursorCarrerras = db.rawQuery("SELECT tb_carr_school.id_carrera, tb_carr_school.id_school, tb_school.id_school, tb_school.name_escuela FROM tb_carr_school, tb_school WHERE tb_carr_school.id_carrera="+idCarrera+" AND tb_school.id_school=tb_carr_school.id_school",null);
        cursorCarrerras = db.rawQuery("SELECT T2.id_school,T2.name_escuela,T2.tel_escuela,T2.tel2_escuela,T2.dir_escuela,T2.sector_escuela,T2.url_escuela " +
                "FROM tb_carr_school T1, tb_school T2\n" +
                "WHERE T1.id_carrera='"+idCarrera+"' AND T2.id_school=T1.id_school",null);

        if (cursorCarrerras.moveToFirst()){
            do{
                Escuela.add(new listEscuela(
                        cursorCarrerras.getString(0)
                        ,cursorCarrerras.getString(1)
                        ,cursorCarrerras.getString(2)
                        ,cursorCarrerras.getString(3)
                        ,cursorCarrerras.getString(4)
                        ,cursorCarrerras.getString(5)
                        ,cursorCarrerras.getString(6)
                ));
            }while(cursorCarrerras.moveToNext());
        }else{
            Escuela=null;
        }
        cursorCarrerras.close();
        return Escuela;
    }

}
