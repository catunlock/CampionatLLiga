package com.sanchez.lopez.alberto.campionatlliga.model;

import com.orm.SugarRecord;

import java.util.Date;

import io.realm.RealmObject;

/**
 * Created by SuNLoCK on 20/03/2016.
 */
public class Partido extends RealmObject{
    Date dataRealitzat;
    Equip equipA;
    Equip equipB;
    int golsA;
    int golsB;
    long dataCreacio;

    public Partido() {
        dataCreacio = System.nanoTime();
    }

    public Partido(Date dataRealitzat, Equip equipA, Equip equipB, int golsA, int golsB) {
        this();
        this.dataRealitzat = dataRealitzat;
        this.equipA = equipA;
        this.equipB = equipB;
        this.golsA = golsA;
        this.golsB = golsB;
    }

    public long getDataCreacio() {
        return dataCreacio;
    }

    public Date getDataRealitzat() {
        return dataRealitzat;
    }

    public void setDataRealitzat(Date dataRealitzat) {
        this.dataRealitzat = dataRealitzat;
    }

    public Equip getEquipA() {
        return equipA;
    }

    public void setEquipA(Equip equipA) {
        this.equipA = equipA;
    }

    public Equip getEquipB() {
        return equipB;
    }

    public void setEquipB(Equip equipB) {
        this.equipB = equipB;
    }

    public int getGolsA() {
        return golsA;
    }

    public void setGolsA(int golsA) {
        this.golsA = golsA;
    }

    public int getGolsB() {
        return golsB;
    }

    public void setGolsB(int golsB) {
        this.golsB = golsB;
    }
}
