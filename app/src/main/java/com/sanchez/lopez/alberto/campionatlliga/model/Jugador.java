package com.sanchez.lopez.alberto.campionatlliga.model;

import com.orm.SugarRecord;

import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by SuNLoCK on 20/03/2016.
 */
public class Jugador extends RealmObject {
    @Required
    String nom;
    int gols;

    public Jugador() {

    }

    public Jugador(String nom) {
        this.nom = nom;
        gols = 0;
    }

    public Jugador(String nom, int gols) {
        this.nom = nom;
        this.gols = gols;
    }

    public int getGols() {
        return gols;
    }

    public void setGols(int gols) {
        this.gols = gols;
    }

    public void addGols(int gols) { this.gols += gols; }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }
}
