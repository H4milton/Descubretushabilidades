package com.example.descubretushabilidades;

public class listPreguntas {
    private int id_area;
    private String pregunta;

    public listPreguntas(int id_area, String pregunta) {
        this.id_area = id_area;
        this.pregunta = pregunta;
    }

    public int getId_area() {
        return id_area;
    }

    public void setId_area(int id_area) {
        this.id_area = id_area;
    }

    public String getPregunta() {
        return pregunta;
    }

    public void setPregunta(String pregunta) {
        this.pregunta = pregunta;
    }
}
