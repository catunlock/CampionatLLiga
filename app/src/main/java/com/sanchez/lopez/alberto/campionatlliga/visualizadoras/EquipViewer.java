package com.sanchez.lopez.alberto.campionatlliga.visualizadoras;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.sanchez.lopez.alberto.campionatlliga.R;
import com.sanchez.lopez.alberto.campionatlliga.model.Equip;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EquipViewer extends AppCompatActivity {

    private Equip equip;

    private EditText txtNomEquip;
    private EditText txtCiutatEquip;
    private ImageView imgEscut;
    private ListView listJugadors;

    private RealmConfiguration realmConfig;
    private Realm realm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip_viewer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);

        txtNomEquip = (EditText) findViewById(R.id.txtNomEquip);
        txtCiutatEquip = (EditText) findViewById(R.id.txtCiutatEquip);
        imgEscut = (ImageView) findViewById(R.id.imgEscut);
        listJugadors = (ListView) findViewById(R.id.listJugadors);

        Intent intent = getIntent();
        String nomEquip = intent.getStringExtra("nomEquip");

        Log.println(Log.DEBUG, "[EQUIP]", nomEquip);
        equip = realm.where(Equip.class).equalTo("nom", nomEquip).findFirst();


        txtNomEquip.setText(equip.getNom());
        txtCiutatEquip.setText(equip.getCiutat());
        int idResourceEscut = Integer.valueOf(equip.getPathEscut());
        imgEscut.setImageResource(idResourceEscut);
        imgEscut.setTag(idResourceEscut);


    }

}
