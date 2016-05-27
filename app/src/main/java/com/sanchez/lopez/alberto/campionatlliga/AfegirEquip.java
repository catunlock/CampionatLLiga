package com.sanchez.lopez.alberto.campionatlliga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;

import com.sanchez.lopez.alberto.campionatlliga.model.Equip;
import com.sanchez.lopez.alberto.campionatlliga.visualizadoras.EquipActivity;

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

        txtNomEquip = (EditText) findViewById(R.id.lblNomJugador);
        txtNomCiutat = (EditText) findViewById(R.id.txtNomCiutat);
        ibtnEscut = (ImageButton) findViewById(R.id.ibtnEscut);

        ibtnEscut.setImageResource(R.mipmap.ic_shield);
        ibtnEscut.setTag(R.mipmap.ic_shield);
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
            Equip e = new Equip(nomEquip, nomCiutat, tagEscut);

            realm.copyToRealm(e);
            realm.commitTransaction();

            Intent intent = new Intent(this, EquipActivity.class);
            intent.putExtra("nomEquip", nomEquip);
            intent.putExtra("locked", false);

            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
