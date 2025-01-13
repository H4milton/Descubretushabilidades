package com.example.descubretushabilidades;

public class listCarreras {
    private String carreraName, idCarr;

    public listCarreras(String idCarr, String carreraName) {
        this.carreraName = carreraName;
        this.idCarr = idCarr;
    }

    public listCarreras() {

    }

    public String getCarreraName() {
        return carreraName;
    }

    public void setCarreraName(String carreraName) {
        this.carreraName = carreraName;
    }

    public String getIdCarr() {
        return idCarr;
    }

    public void setIdCarr(String idCarr) {
        this.idCarr = idCarr;
    }
}
