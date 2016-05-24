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
            titulars.add(new Jugador("Nom Titular", 0));
        }

        reservas = new RealmList<>();
        for (int i = 0; i < NUM_RESERVAS; ++i) {
            reservas.add(new Jugador("Nom Reserva", 0));
        }

        punts = 0;
    }

    @Override
    public String toString() {
        return "EquipActivity{" +
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
