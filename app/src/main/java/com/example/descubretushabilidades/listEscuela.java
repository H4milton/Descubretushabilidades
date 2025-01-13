package com.example.descubretushabilidades;

public class listEscuela {
    private String idEscuela;
    private String Nescuela;
    private String TelEscuela;
    private String TelEscuela2;
    private String DirEscuela;
    private String SectorEscuela;
    private String urlEscuela;

    public listEscuela(String idEscuela, String nescuela, String telEscuela, String telEscuela2, String dirEscuela, String sectorEscuela, String urlEscuela) {
        this.idEscuela = idEscuela;
        this.Nescuela = nescuela;
        this.TelEscuela = telEscuela;
        this.TelEscuela2 = telEscuela2;
        this.DirEscuela = dirEscuela;
        this.SectorEscuela = sectorEscuela;
        this.urlEscuela = urlEscuela;
    }

    public String getTelEscuela2() {
        return TelEscuela2;
    }

    public void setTelEscuela2(String telEscuela2) {
        TelEscuela2 = telEscuela2;
    }

    public String getSectorEscuela() {
        return SectorEscuela;
    }

    public void setSectorEscuela(String sectorEscuela) {
        SectorEscuela = sectorEscuela;
    }

    public String getUrlEscuela() {
        return urlEscuela;
    }

    public void setUrlEscuela(String urlEscuela) {
        this.urlEscuela = urlEscuela;
    }

    public String getIdEscuela() {
        return idEscuela;
    }

    public void setIdEscuela(String idEscuela) {
        this.idEscuela = idEscuela;
    }

    public String getNescuela() {
        return Nescuela;
    }

    public void setNescuela(String nescuela) {
        Nescuela = nescuela;
    }

    public String getTelEscuela() {
        return TelEscuela;
    }

    public void setTelEscuela(String telEscuela) {
        TelEscuela = telEscuela;
    }

    public String getDirEscuela() {
        return DirEscuela;
    }

    public void setDirEscuela(String dirEscuela) {
        DirEscuela = dirEscuela;
    }
}
