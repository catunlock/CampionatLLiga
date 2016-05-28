package com.sanchez.lopez.alberto.campionatlliga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.sanchez.lopez.alberto.campionatlliga.model.Jornada;
import com.sanchez.lopez.alberto.campionatlliga.visualizadoras.JornadaActivity;

import java.util.Date;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AfegirJornada extends AppCompatActivity {

    private RealmConfiguration realmConfig;
    private Realm realm;

    private DatePicker datePicker;
    private TextView lblNumJornada;
    private Button btnBack;
    private Button btnFoward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_afegir_jornada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        datePicker = (DatePicker) findViewById(R.id.datePicker);
        lblNumJornada = (TextView) findViewById(R.id.lblNumJornada);
        btnBack = (Button) findViewById(R.id.btnBack);
        btnFoward = (Button) findViewById(R.id.btnFoward);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numJornada = Integer.parseInt(lblNumJornada.getText().toString());
                if (numJornada > 1) {
                    lblNumJornada.setText(String.valueOf(numJornada-1));
                }

            }
        });

        btnFoward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int numJornada = Integer.parseInt(lblNumJornada.getText().toString());
                lblNumJornada.setText(String.valueOf(numJornada+1));
            }
        });

        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_afegir_jornada, menu);
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

            Jornada j = new Jornada();

            j.setData(new Date(datePicker.getYear(), datePicker.getMonth()+1, datePicker.getDayOfMonth()));
            j.setNumJornada(Integer.valueOf(lblNumJornada.getText().toString()));
            realm.copyToRealm(j);
            realm.commitTransaction();

            Intent intent = new Intent(this, JornadaActivity.class);
            intent.putExtra("numJornada", j.getNumJornada());
            intent.putExtra("locked", true);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
