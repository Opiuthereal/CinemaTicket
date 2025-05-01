package com.example.recyclerviewdemo;

public class ItemHoraire {

    String horaireFilm;
    String vostHoraireFilm;


    public ItemHoraire(String horaireFilm, String vostHoraireFilm) {
        this.horaireFilm = horaireFilm;
        this.vostHoraireFilm = vostHoraireFilm;
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
