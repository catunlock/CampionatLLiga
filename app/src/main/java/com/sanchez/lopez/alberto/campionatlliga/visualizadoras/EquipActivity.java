package com.sanchez.lopez.alberto.campionatlliga.visualizadoras;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.sanchez.lopez.alberto.campionatlliga.R;
import com.sanchez.lopez.alberto.campionatlliga.model.Equip;
import com.sanchez.lopez.alberto.campionatlliga.model.Jugador;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class EquipActivity extends AppCompatActivity {

    private TextView lblEquip;
    private TextView lblCiutat;

    ArrayList<EditText> txtNomTitulars;
    ArrayList<EditText> txtGolsTitulars;
    ArrayList<EditText> txtNomReservas;
    ArrayList<EditText> txtGolsReservas;

    private RealmConfiguration realmConfig;
    private Realm realm;

    private Equip equip;

    boolean locked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_equip);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);

        lblEquip = (TextView) findViewById(R.id.lblEquip);
        lblCiutat = (TextView) findViewById(R.id.lblCiutat);
        txtNomTitulars = new ArrayList<>(5);
        txtGolsTitulars = new ArrayList<>(5);
        txtNomReservas = new ArrayList<>(7);
        txtGolsReservas = new ArrayList<>(7);

        txtNomTitulars.add((EditText) findViewById(R.id.txtNomTitular1));
        txtNomTitulars.add((EditText) findViewById(R.id.txtNomTitular2));
        txtNomTitulars.add((EditText) findViewById(R.id.txtNomTitular3));
        txtNomTitulars.add((EditText) findViewById(R.id.txtNomTitular4));
        txtNomTitulars.add((EditText) findViewById(R.id.txtNomTitular5));

        txtGolsTitulars.add((EditText) findViewById(R.id.txtGolsTitular1));
        txtGolsTitulars.add((EditText) findViewById(R.id.txtGolsTitular2));
        txtGolsTitulars.add((EditText) findViewById(R.id.txtGolsTitular3));
        txtGolsTitulars.add((EditText) findViewById(R.id.txtGolsTitular4));
        txtGolsTitulars.add((EditText) findViewById(R.id.txtGolsTitular5));

        txtNomReservas.add((EditText) findViewById(R.id.txtNomReserva1));
        txtNomReservas.add((EditText) findViewById(R.id.txtNomReserva2));
        txtNomReservas.add((EditText) findViewById(R.id.txtNomReserva3));
        txtNomReservas.add((EditText) findViewById(R.id.txtNomReserva4));
        txtNomReservas.add((EditText) findViewById(R.id.txtNomReserva5));
        txtNomReservas.add((EditText) findViewById(R.id.txtNomReserva6));
        txtNomReservas.add((EditText) findViewById(R.id.txtNomReserva7));

        txtGolsReservas.add((EditText) findViewById(R.id.txtGolsReserva1));
        txtGolsReservas.add((EditText) findViewById(R.id.txtGolsReserva2));
        txtGolsReservas.add((EditText) findViewById(R.id.txtGolsReserva3));
        txtGolsReservas.add((EditText) findViewById(R.id.txtGolsReserva4));
        txtGolsReservas.add((EditText) findViewById(R.id.txtGolsReserva5));
        txtGolsReservas.add((EditText) findViewById(R.id.txtGolsReserva6));
        txtGolsReservas.add((EditText) findViewById(R.id.txtGolsReserva7));

        Intent intent = getIntent();
        String nomEquip = intent.getStringExtra("nomEquip");
        locked = intent.getBooleanExtra("locked", true);

        Log.println(Log.DEBUG, "[EQUIP]", nomEquip);
        equip = realm.where(Equip.class).equalTo("nom", nomEquip).findFirst();

        String nomEquipo = equip.getNom();

        lblEquip.setText(nomEquipo);
        lblCiutat.setText(equip.getCiutat());

        List<Jugador> titulars = equip.getTitulars();
        List<Jugador> reservas = equip.getReservas();

        for (int i = 0; i < txtNomTitulars.size(); ++i) {
            txtNomTitulars.get(i).setText(titulars.get(i).getNom());
            txtGolsTitulars.get(i).setText(String.valueOf(titulars.get(i).getGols()));
        }

        for (int i = 0; i < txtNomReservas.size(); ++i) {
            txtNomReservas.get(i).setText(reservas.get(i).getNom());
            txtGolsReservas.get(i).setText(String.valueOf(reservas.get(i).getGols()));
        }

        updateLockableElements();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_equip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_unlock) {

            if (locked) {
                locked = false;
                item.setIcon(R.drawable.ic_lock_white_48dp);
                updateLockableElements();
            } else {
                locked = true;
                item.setIcon(R.drawable.ic_lock_open_white_48dp);
                updateDataBase();
                updateLockableElements();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void updateDataBase() {
        realm.beginTransaction();
        List<Jugador> titulars = equip.getTitulars();
        List<Jugador> reservas = equip.getReservas();

        for (int i = 0; i < txtNomTitulars.size(); ++i) {
            titulars.get(i).setNom(txtNomTitulars.get(i).getText().toString());
            titulars.get(i).setGols(Integer.valueOf(txtGolsTitulars.get(i).getText().toString()));
        }

        for (int i = 0; i < txtNomReservas.size(); ++i) {
            reservas.get(i).setNom(txtNomReservas.get(i).getText().toString());
            reservas.get(i).setGols(Integer.valueOf(txtGolsReservas.get(i).getText().toString()));
        }

        realm.commitTransaction();
    }

    private void updateLockableElements() {

        boolean enabled = !locked;
        for (EditText txt : txtNomTitulars) {
            txt.setEnabled(enabled);
        }
        for (EditText txt : txtGolsTitulars) {
            txt.setEnabled(enabled);
        }
        for (EditText txt : txtNomReservas) {
            txt.setEnabled(enabled);
        }
        for (EditText txt : txtGolsReservas) {
            txt.setEnabled(enabled);
        }
    }
}
