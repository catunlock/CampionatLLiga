package com.sanchez.lopez.alberto.campionatlliga.model;

import com.orm.SugarRecord;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by SuNLoCK on 20/03/2016.
 */
public class Jornada extends RealmObject {
    RealmList<Partido> partidos;

    public Jornada() {
    }
}
