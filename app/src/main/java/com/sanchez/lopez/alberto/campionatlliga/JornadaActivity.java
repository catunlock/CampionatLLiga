package com.sanchez.lopez.alberto.campionatlliga;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.sanchez.lopez.alberto.campionatlliga.model.Equip;
import com.sanchez.lopez.alberto.campionatlliga.model.Partido;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class JornadaActivity extends AppCompatActivity {


    private RealmConfiguration realmConfig;
    private Realm realm;

    private ListView listPartit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jornada);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        listPartit = (ListView) findViewById(R.id.listPartits);

        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this);
        listPartit.setAdapter(adapter);
    }

    private class MySimpleArrayAdapter extends ArrayAdapter<Partido> {
        private final Context context;
        private final List<Partido> partidos;

        public MySimpleArrayAdapter(Context context) {
            super(context, -1);
            partidos = realm.where(Partido.class).findAll();
            this.addAll(partidos);
            this.context = context;
        }

        private int getResourceId(String pVariableName, String pResourcename, String pPackageName)
        {
            try {
                return getResources().getIdentifier(pVariableName, pResourcename, pPackageName);
            } catch (Exception e) {
                e.printStackTrace();
                return -1;
            }
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View rowView = inflater.inflate(R.layout.listview_partits, parent, false);

            TextView lblCasaNom = (TextView) rowView.findViewById(R.id.lblCasaNom);
            TextView lblForaNom = (TextView) rowView.findViewById(R.id.lblForaNom);
            TextView lblCasaGols = (TextView) rowView.findViewById(R.id.lblCasaGols);
            TextView lblForaGols = (TextView) rowView.findViewById(R.id.lblForaGols);

            ImageView imgCasaEscut = (ImageView) rowView.findViewById(R.id.imgCasaEscut);
            ImageView imgForaEscut = (ImageView) rowView.findViewById(R.id.imgForaEscut);

            Partido p = partidos.get(position);

            lblCasaNom.setText(p.getEquipA().getNom());
            lblForaNom.setText(p.getEquipB().getNom());
            lblCasaGols.setText(String.valueOf(p.getGolsA()));
            lblCasaGols.setText(String.valueOf(p.getGolsB()));

            int idResourceEscutCasa = Integer.valueOf(p.getEquipA().getPathEscut());
            imgCasaEscut.setImageResource(idResourceEscutCasa);
            imgCasaEscut.setTag(idResourceEscutCasa);

            int idResourceEscutFora = Integer.valueOf(p.getEquipB().getPathEscut());
            imgForaEscut.setImageResource(idResourceEscutFora);
            imgForaEscut.setTag(idResourceEscutFora);

            return rowView;
        }
    }

}
