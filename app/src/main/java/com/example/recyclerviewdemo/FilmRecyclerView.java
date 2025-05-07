package com.example.recyclerviewdemo;

public class FilmRecyclerView {

    String titre;
    String horaire1;
    String horaire2;
    String horaire3;
    String horaire4;

    String vost1;
    String vost2;
    String vost3;
    String vost4;

    String nomCinoche;
    int imageResId;

    int ageRequis;

    public FilmRecyclerView(String titre, String horaire1, String horaire2, String horaire3, String horaire4, String vost1, String vost2, String vost3, String vost4,String nomCinoche, int imageResId, int ageRequis) {
        this.titre = titre;
        this.horaire1 = horaire1;
        this.horaire2 = horaire2;
        this.horaire3 = horaire3;
        this.horaire4 = horaire4;
        this.vost1 = vost1;
        this.vost2 = vost2;
        this.vost3 = vost3;
        this.vost4 = vost4;
        this.nomCinoche = nomCinoche;
        this.imageResId = imageResId;
        this.ageRequis = ageRequis;
    }

    public String getTitre() {
        return titre;
    }

    public String getHoraire1() {
        return horaire1;
    }

    public String getHoraire2() {
        return horaire2;
    }

    public String getHoraire3() {
        return horaire3;
    }

    public String getHoraire4() {
        return horaire4;
    }

    public String getVost1() {
        return vost1;
    }

    public String getVost2() {
        return vost2;
    }

    public String getVost3() {
        return vost3;
    }

    public String getVost4() {
        return vost4;
    }

    public String getNomCinoche() { return nomCinoche; }

    public int getImageResId() {
        return imageResId;
    }

    public int getAgeRequis() {
        return ageRequis;
    }
}
