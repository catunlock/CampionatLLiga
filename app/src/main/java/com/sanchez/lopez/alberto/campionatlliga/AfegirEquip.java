package com.sanchez.lopez.alberto.campionatlliga;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sanchez.lopez.alberto.campionatlliga.model.Equip;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AfegirEquip extends AppCompatActivity {

    EditText txtNomEquip;
    EditText txtNomCiutat;
    ImageButton ibtnEscut;

    private RealmConfiguration realmConfig;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_equip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);

        txtNomEquip = (EditText) findViewById(R.id.txtNomEquip);
        txtNomCiutat = (EditText) findViewById(R.id.txtNomCiutat);
        ibtnEscut = (ImageButton) findViewById(R.id.ibtnEscut);

        ibtnEscut.setImageResource(R.mipmap.ic_shield);
        ibtnEscut.setTag(R.mipmap.ic_shield);
    }


}
