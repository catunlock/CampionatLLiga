package com.sanchez.lopez.alberto.campionatlliga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sanchez.lopez.alberto.campionatlliga.model.Equip;
import com.sanchez.lopez.alberto.campionatlliga.model.Jugador;
import com.sanchez.lopez.alberto.campionatlliga.visualizadoras.EquipViewer;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AfegirEquip extends AppCompatActivity {

    EditText txtNomEquip;
    EditText txtNomCiutat;
    ImageButton ibtnEscut;

    ArrayList<EditText> txtNomTitulars = new ArrayList<>();
    ArrayList<EditText> txtNomReservas = new ArrayList<>();

    Equip equip;

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

        txtNomEquip = (EditText) findViewById(R.id.lblNomJugador);
        txtNomCiutat = (EditText) findViewById(R.id.txtNomCiutat);
        ibtnEscut = (ImageButton) findViewById(R.id.ibtnEscut);

        txtNomTitulars = new ArrayList<>(5);
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular1));
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular2));
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular3));
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular4));
        txtNomTitulars.add((EditText) findViewById(R.id.txtTitular5));

        txtNomReservas = new ArrayList<>(7);
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva1));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva2));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva3));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva4));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva5));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva6));
        txtNomReservas.add((EditText) findViewById(R.id.txtReserva7));

        ibtnEscut.setImageResource(R.mipmap.ic_shield);
        ibtnEscut.setTag(R.mipmap.ic_shield);

        Intent intent = getIntent();
        String nomEquip = intent.getStringExtra("nomEquip");

        if (nomEquip != null) {
            loadData(nomEquip);
        }

    }

    private void loadData(String nomEquip) {
        Log.println(Log.DEBUG, "[EQUIP]", "Cargando equipo: " + nomEquip);
        equip = realm.where(Equip.class).equalTo("nom", nomEquip).findFirst();

        txtNomEquip.setText(equip.getNom());
        txtNomCiutat.setText(equip.getCiutat());

        int idResourceEscut = Integer.valueOf(equip.getPathEscut());
        ibtnEscut.setImageResource(idResourceEscut);
        ibtnEscut.setTag(idResourceEscut);

        List<Jugador> titulars = equip.getTitulars();
        List<Jugador> reservas = equip.getReservas();

        for (int i = 0; i < txtNomTitulars.size(); ++i) {
            txtNomTitulars.get(i).setText(titulars.get(i).getNom());
        }

        for (int i = 0; i < txtNomReservas.size(); ++i) {
            txtNomReservas.get(i).setText(reservas.get(i).getNom());
        }

        String nomEquipo = equip.getNom();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_afegir_equip, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.action_save) {
            realm.beginTransaction();

            String nomEquip = txtNomEquip.getText().toString();
            String nomCiutat = txtNomCiutat.getText().toString();
            String tagEscut = ibtnEscut.getTag().toString();
            if (equip == null) {
                equip = new Equip(nomEquip, nomCiutat, tagEscut);
            }


            List<Jugador> titulars = equip.getTitulars();
            List<Jugador> reservas = equip.getReservas();

            for (int i = 0; i < txtNomTitulars.size(); ++i) {
                EditText control = txtNomTitulars.get(i);
                String nom = control.getText().toString();
                titulars.get(i).setNom(nom);
            }

            for (int i = 0; i < txtNomReservas.size(); ++i) {
                String nom = txtNomReservas.get(i).getText().toString();
                reservas.get(i).setNom(nom);
            }

            realm.copyToRealm(equip);
            realm.commitTransaction();

            Intent intent = new Intent(this, EquipViewer.class);
            intent.putExtra("nomEquip", nomEquip);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
