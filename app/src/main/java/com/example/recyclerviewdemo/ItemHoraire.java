package com.example.recyclerviewdemo;

public class ItemHoraire {

    String dateFilm;

    String horaireFilm;
    String vostHoraireFilm;


    public ItemHoraire(String dateFilm, String horaireFilm, String vostHoraireFilm) {
        this.dateFilm = dateFilm;
        this.horaireFilm = horaireFilm;
        this.vostHoraireFilm = vostHoraireFilm;
    }

    public String getDateFilm() {
        return dateFilm;
    }

    public void setDateFilm(String dateFilm) {
        this.dateFilm = dateFilm;
    }

    public String getHoraireFilm() {
        return horaireFilm;
    }

    public void setHoraireFilm(String horaireFilm) {
        this.horaireFilm = horaireFilm;
    }

    public String getVostHoraireFilm() {
        return vostHoraireFilm;
    }

    public void setVostHoraireFilm(String vostHoraireFilm) {
        this.vostHoraireFilm = vostHoraireFilm;
    }
}
