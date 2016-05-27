package com.sanchez.lopez.alberto.campionatlliga;

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
import com.sanchez.lopez.alberto.campionatlliga.visualizadoras.EquipViewer;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AfegirEquipJugadors extends AppCompatActivity {

    private TextView lblEquip;
    private TextView lblCiutat;

    ArrayList<EditText> txtNomTitulars;
    ArrayList<EditText> txtNomReservas;

    private RealmConfiguration realmConfig;
    private Realm realm;

    private Equip equip;


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
        txtNomReservas = new ArrayList<>(7);


        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular1));
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular2));
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular3));
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular4));
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular5));

        txtNomReservas.add((EditText) findViewById(R.id.txtReserva1));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva2));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva3));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva4));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva5));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva6));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva7));


        Intent intent = getIntent();
        String nomEquip = intent.getStringExtra("nomEquip");

        Log.println(Log.DEBUG, "[EQUIP]", nomEquip);
        equip = realm.where(Equip.class).equalTo("nom", nomEquip).findFirst();

        String nomEquipo = equip.getNom();

        lblEquip.setText(nomEquipo);
        lblCiutat.setText(equip.getCiutat());

        List<Jugador> titulars = equip.getTitulars();
        List<Jugador> reservas = equip.getReservas();

        for (int i = 0; i < txtNomTitulars.size(); ++i) {
            txtNomTitulars.get(i).setText(titulars.get(i).getNom());
        }

        for (int i = 0; i < txtNomReservas.size(); ++i) {
            txtNomReservas.get(i).setText(reservas.get(i).getNom());
        }

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

        if (id == R.id.action_save) {

            updateDataBase();
            // TODO: Arreglar esto del save de los nombres de los jugadors i pase a la siguiente.

            Intent intent = new Intent(this, EquipViewer.class);
            intent.putExtra("nomEquip", equip.getNom());
            intent.putExtra("locked", false);

            startActivity(intent);

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
        }

        for (int i = 0; i < txtNomReservas.size(); ++i) {
            reservas.get(i).setNom(txtNomReservas.get(i).getText().toString());
        }

        realm.commitTransaction();
    }
}
