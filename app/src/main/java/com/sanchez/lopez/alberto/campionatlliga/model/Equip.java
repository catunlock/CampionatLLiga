package com.sanchez.lopez.alberto.campionatlliga.model;

import com.orm.SugarRecord;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.Required;

/**
 * Created by SuNLoCK on 20/03/2016.
 */
public class Equip extends RealmObject {
    public static final int NUM_TITULARS = 5;
    public static final int NUM_RESERVAS = 7;

    @Required
    String nom;
    @Required
    String ciutat;
    @Required
    String pathEscut;

    int punts;
    int guanyats;
    int perduts;
    int empatats;

    public void setPathEscut(String pathEscut) {
        this.pathEscut = pathEscut;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

    public int getGuanyats() {
        return guanyats;
    }

    public int getPerduts() {
        return perduts;
    }

    public int getEmpatats() {
        return empatats;
    }

    public void setGuanyats(int guanyats) {
        this.guanyats = guanyats;
    }

    public void setPerduts(int perduts) {
        this.perduts = perduts;
    }

    public void setEmpatats(int empatats) {
        this.empatats = empatats;
    }

    public void empata() {
        empatats += 1;
        punts += 1;
    }

    public void perd() {
        perduts += 1;
    }

    public void guanya() {
        guanyats += 1;
        punts += 3;
    }

    RealmList<Jugador> titulars;
    RealmList<Jugador> reservas;

    public Equip() {
    }

    public Equip(String nom, String ciutat, String pathEscut) {
        this.nom = nom;
        this.ciutat = ciutat;
        this.pathEscut = pathEscut;

        titulars = new RealmList<>();
        for (int i = 0; i < NUM_TITULARS; ++i) {
            titulars.add(new Jugador("", 0, this));
        }

        reservas = new RealmList<>();
        for (int i = 0; i < NUM_RESERVAS; ++i) {
            reservas.add(new Jugador("", 0, this));
        }

        punts = 0;
    }

    @Override
    public String toString() {
        return "EquipJugadors{" +
                "nom='" + nom + '\'' +
                ", ciutat='" + ciutat + '\'' +
                ", pathEscut='" + pathEscut + '\'' +
                ", titulars=" + titulars +
                ", reservas=" + reservas +
                '}';
    }

    public String getNom() {
        return nom;
    }

    public String getCiutat() {
        return ciutat;
    }

    public String getPathEscut() {
        return pathEscut;
    }

    public RealmList<Jugador> getTitulars() {
        return titulars;
    }

    public RealmList<Jugador> getReservas() {
        return reservas;
    }

    public int getSizeTitulars() {
        return titulars.size();
    }

    public int getSizeReservas() {
        return reservas.size();
    }

    public void setTitular(int i, Jugador j) {
        titulars.set(i, j);
    }

    public void setReserva(int i, Jugador r) {
        reservas.set(i,r);
    }

    public int getPunts() {
        return punts;
    }

    public void setPunts(int punts) {
        this.punts = punts;
    }
}
