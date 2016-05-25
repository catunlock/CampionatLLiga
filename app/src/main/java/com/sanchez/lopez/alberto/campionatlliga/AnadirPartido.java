package com.sanchez.lopez.alberto.campionatlliga;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;
import android.widget.TextView;

import com.sanchez.lopez.alberto.campionatlliga.model.Equip;
import com.sanchez.lopez.alberto.campionatlliga.model.Partido;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class AnadirPartido extends AppCompatActivity {

    private RealmConfiguration realmConfig;
    private Realm realm;
    private List<Equip> equips;
    private Spinner spnCasa;
    private Spinner spnFora;
    private TextView lblGolsCasa;
    private TextView lblGolsFora;

    private Dialog dialogData;

    private Equip equipCasa;
    private Equip equipFora;
    private Date date;

    private HashMap<String, Equip> mapEquips;

    private ArrayList<TextView> lblNomTitularsCasa;
    private ArrayList<Button> btnMinusTitularsCasa;
    private ArrayList<TextView> lblGolsTitularsCasa;
    private ArrayList<Button> btnPlusTitularsCasa;

    private ArrayList<TextView> lblNomReservaCasa;
    private ArrayList<Button> btnMinusReservaCasa;
    private ArrayList<TextView> lblGolsReservaCasa;
    private ArrayList<Button> btnPlusReservaCasa;

    private ArrayList<TextView> lblNomTitularsFora;
    private ArrayList<Button> btnMinusTitularsFora;
    private ArrayList<TextView> lblGolsTitularsFora;
    private ArrayList<Button> btnPlusTitularsFora;

    private ArrayList<TextView> lblNomReservaFora;
    private ArrayList<Button> btnMinusReservaFora;
    private ArrayList<TextView> lblGolsReservaFora;
    private ArrayList<Button> btnPlusReservaFora;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_partido);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle("--/--/----");

        getAllViewsById();
        createAlertDialog();

        // Create a RealmConfiguration which is to locate Realm file in package's "files" directory.
        realmConfig = new RealmConfiguration.Builder(this).build();
        realm = Realm.getInstance(realmConfig);

        equips = realm.where(Equip.class).findAll();
        mapEquips = new HashMap<>(equips.size());

        ArrayList<String> nomEquips = new ArrayList<>(equips.size());

        for (Equip e : equips) {
            String nomEquip = e.getNom();

            nomEquips.add(nomEquip);
            mapEquips.put(nomEquip, e);

        }

// Application of the Array to the Spinner
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(this,   android.R.layout.simple_spinner_item, nomEquips);
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); // The drop down view

        spnFora.setAdapter(spinnerArrayAdapter);
        spnCasa.setAdapter(spinnerArrayAdapter);


        spnFora.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nomEquipFora = spnFora.getSelectedItem().toString();
                Log.d("SPINNER", "nomEquipFora: " + nomEquipFora);

                equipFora = mapEquips.get(nomEquipFora);

                for (int i = 0; i < 5; ++i) {
                    String nomTitularFora = equipFora.getTitulars().get(i).getNom();
                    lblNomTitularsFora.get(i).setText(nomTitularFora);
                }
                for (int i = 0; i < 7; ++i) {
                    String nomReservaFora = equipFora.getReservas().get(i).getNom();
                    lblNomReservaFora.get(i).setText(nomReservaFora);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spnCasa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String nomEquipCasa = spnCasa.getSelectedItem().toString();
                Log.d("SPINNER", "nomEquipCasa: " + nomEquipCasa);

                equipCasa = mapEquips.get(nomEquipCasa);

                for (int i = 0; i < 5; ++i) {
                    String nomTitularCasa = equipCasa.getTitulars().get(i).getNom();
                    lblNomTitularsCasa.get(i).setText(nomTitularCasa);
                }
                for (int i = 0; i < 7; ++i) {
                    String nomReservaCasa = equipCasa.getReservas().get(i).getNom();
                    lblNomReservaCasa.get(i).setText(nomReservaCasa);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_anadir_partido, menu);
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

        if (id == R.id.action_calendar) {
            showDatePicker();

        }

        if (id == R.id.action_save) {
            if (date != null) {
                guardarPartit(true);
            }else {
                dialogData.show();
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getAllViewsById() {
        spnCasa = (Spinner) findViewById(R.id.spnCasa);
        spnFora = (Spinner) findViewById(R.id.spnFora);

        lblGolsCasa = (TextView) findViewById(R.id.lblGolsCasa);
        lblGolsFora = (TextView) findViewById(R.id.lblGolsFora);

        lblNomTitularsCasa = new ArrayList<>();
        btnMinusTitularsCasa = new ArrayList<>();
        lblGolsTitularsCasa = new ArrayList<>();
        btnPlusTitularsCasa = new ArrayList<>();

        lblNomReservaCasa = new ArrayList<>();
        btnMinusReservaCasa = new ArrayList<>();
        lblGolsReservaCasa = new ArrayList<>();
        btnPlusReservaCasa = new ArrayList<>();

        lblNomTitularsFora = new ArrayList<>();
        btnMinusTitularsFora = new ArrayList<>();
        lblGolsTitularsFora = new ArrayList<>();
        btnPlusTitularsFora = new ArrayList<>();

        lblNomReservaFora = new ArrayList<>();
        btnMinusReservaFora = new ArrayList<>();
        lblGolsReservaFora = new ArrayList<>();
        btnPlusReservaFora = new ArrayList<>();

        lblNomTitularsCasa.add((TextView) findViewById(R.id.lblTitularCasa1));
        lblNomTitularsCasa.add((TextView) findViewById(R.id.lblTitularCasa2));
        lblNomTitularsCasa.add((TextView) findViewById(R.id.lblTitularCasa3));
        lblNomTitularsCasa.add((TextView) findViewById(R.id.lblTitularCasa4));
        lblNomTitularsCasa.add((TextView) findViewById(R.id.lblTitularCasa5));

        lblNomReservaCasa.add((TextView) findViewById(R.id.lblReservaCasa1));
        lblNomReservaCasa.add((TextView) findViewById(R.id.lblReservaCasa2));
        lblNomReservaCasa.add((TextView) findViewById(R.id.lblReservaCasa3));
        lblNomReservaCasa.add((TextView) findViewById(R.id.lblReservaCasa4));
        lblNomReservaCasa.add((TextView) findViewById(R.id.lblReservaCasa5));
        lblNomReservaCasa.add((TextView) findViewById(R.id.lblReservaCasa6));
        lblNomReservaCasa.add((TextView) findViewById(R.id.lblReservaCasa7));

        lblNomTitularsFora.add((TextView) findViewById(R.id.lblTitularFora1));
        lblNomTitularsFora.add((TextView) findViewById(R.id.lblTitularFora2));
        lblNomTitularsFora.add((TextView) findViewById(R.id.lblTitularFora3));
        lblNomTitularsFora.add((TextView) findViewById(R.id.lblTitularFora4));
        lblNomTitularsFora.add((TextView) findViewById(R.id.lblTitularFora5));

        lblNomReservaFora.add((TextView) findViewById(R.id.lblReservaFora1));
        lblNomReservaFora.add((TextView) findViewById(R.id.lblReservaFora2));
        lblNomReservaFora.add((TextView) findViewById(R.id.lblReservaFora3));
        lblNomReservaFora.add((TextView) findViewById(R.id.lblReservaFora4));
        lblNomReservaFora.add((TextView) findViewById(R.id.lblReservaFora5));
        lblNomReservaFora.add((TextView) findViewById(R.id.lblReservaFora6));
        lblNomReservaFora.add((TextView) findViewById(R.id.lblReservaFora7));

        btnMinusTitularsCasa.add((Button) findViewById(R.id.btnTitularMinusCasa1));
        btnMinusTitularsCasa.add((Button) findViewById(R.id.btnTitularMinusCasa2));
        btnMinusTitularsCasa.add((Button) findViewById(R.id.btnTitularMinusCasa3));
        btnMinusTitularsCasa.add((Button) findViewById(R.id.btnTitularMinusCasa4));
        btnMinusTitularsCasa.add((Button) findViewById(R.id.btnTitularMinusCasa5));

        btnMinusReservaCasa.add((Button) findViewById(R.id.btnReservaMinusCasa1));
        btnMinusReservaCasa.add((Button) findViewById(R.id.btnReservaMinusCasa2));
        btnMinusReservaCasa.add((Button) findViewById(R.id.btnReservaMinusCasa3));
        btnMinusReservaCasa.add((Button) findViewById(R.id.btnReservaMinusCasa4));
        btnMinusReservaCasa.add((Button) findViewById(R.id.btnReservaMinusCasa5));
        btnMinusReservaCasa.add((Button) findViewById(R.id.btnReservaMinusCasa6));
        btnMinusReservaCasa.add((Button) findViewById(R.id.btnReservaMinusCasa7));

        btnMinusTitularsFora.add((Button) findViewById(R.id.btnTitularForaMinus1));
        btnMinusTitularsFora.add((Button) findViewById(R.id.btnTitularForaMinus2));
        btnMinusTitularsFora.add((Button) findViewById(R.id.btnTitularForaMinus3));
        btnMinusTitularsFora.add((Button) findViewById(R.id.btnTitularForaMinus4));
        btnMinusTitularsFora.add((Button) findViewById(R.id.btnTitularForaMinus5));

        btnMinusReservaFora.add( (Button) findViewById(R.id.btnReservaMinusFora1));
        btnMinusReservaFora.add( (Button) findViewById(R.id.btnReservaMinusFora2));
        btnMinusReservaFora.add( (Button) findViewById(R.id.btnReservaMinusFora3));
        btnMinusReservaFora.add( (Button) findViewById(R.id.btnReservaMinusFora4));
        btnMinusReservaFora.add( (Button) findViewById(R.id.btnReservaMinusFora5));
        btnMinusReservaFora.add( (Button) findViewById(R.id.btnReservaMinusFora6));
        btnMinusReservaFora.add( (Button) findViewById(R.id.btnReservaMinusFora7));

        lblGolsTitularsCasa.add( (TextView) findViewById(R.id.lblGolsCasaTitular1));
        lblGolsTitularsCasa.add( (TextView) findViewById(R.id.lblGolsCasaTitular2));
        lblGolsTitularsCasa.add( (TextView) findViewById(R.id.lblGolsCasaTitular3));
        lblGolsTitularsCasa.add( (TextView) findViewById(R.id.lblGolsCasaTitular4));
        lblGolsTitularsCasa.add( (TextView) findViewById(R.id.lblGolsCasaTitular5));

        lblGolsReservaCasa.add((TextView) findViewById(R.id.lblGolsCasaReserva1));
        lblGolsReservaCasa.add((TextView) findViewById(R.id.lblGolsCasaReserva2));
        lblGolsReservaCasa.add((TextView) findViewById(R.id.lblGolsCasaReserva3));
        lblGolsReservaCasa.add((TextView) findViewById(R.id.lblGolsCasaReserva4));
        lblGolsReservaCasa.add((TextView) findViewById(R.id.lblGolsCasaReserva5));
        lblGolsReservaCasa.add((TextView) findViewById(R.id.lblGolsCasaReserva6));
        lblGolsReservaCasa.add((TextView) findViewById(R.id.lblGolsCasaReserva7));

        lblGolsTitularsFora.add((TextView) findViewById(R.id.lblGolsForaTitular1));
        lblGolsTitularsFora.add((TextView) findViewById(R.id.lblGolsForaTitular2));
        lblGolsTitularsFora.add((TextView) findViewById(R.id.lblGolsForaTitular3));
        lblGolsTitularsFora.add((TextView) findViewById(R.id.lblGolsForaTitular4));
        lblGolsTitularsFora.add((TextView) findViewById(R.id.lblGolsForaTitular5));

        lblGolsReservaFora.add((TextView) findViewById(R.id.lblGolsForaReserva1));
        lblGolsReservaFora.add((TextView) findViewById(R.id.lblGolsForaReserva2));
        lblGolsReservaFora.add((TextView) findViewById(R.id.lblGolsForaReserva3));
        lblGolsReservaFora.add((TextView) findViewById(R.id.lblGolsForaReserva4));
        lblGolsReservaFora.add((TextView) findViewById(R.id.lblGolsForaReserva5));
        lblGolsReservaFora.add((TextView) findViewById(R.id.lblGolsForaReserva6));
        lblGolsReservaFora.add((TextView) findViewById(R.id.lblGolsForaReserva7));

        btnPlusTitularsCasa.add((Button) findViewById(R.id.btnTitularPlusCasa1));
        btnPlusTitularsCasa.add((Button) findViewById(R.id.btnTitularPlusCasa2));
        btnPlusTitularsCasa.add((Button) findViewById(R.id.btnTitularPlusCasa3));
        btnPlusTitularsCasa.add((Button) findViewById(R.id.btnTitularPlusCasa4));
        btnPlusTitularsCasa.add((Button) findViewById(R.id.btnTitularPlusCasa5));

        btnPlusReservaCasa.add((Button) findViewById(R.id.btnReservaPlusCasa1));
        btnPlusReservaCasa.add((Button) findViewById(R.id.btnReservaPlusCasa2));
        btnPlusReservaCasa.add((Button) findViewById(R.id.btnReservaPlusCasa3));
        btnPlusReservaCasa.add((Button) findViewById(R.id.btnReservaPlusCasa4));
        btnPlusReservaCasa.add((Button) findViewById(R.id.btnReservaPlusCasa5));
        btnPlusReservaCasa.add((Button) findViewById(R.id.btnReservaPlusCasa6));
        btnPlusReservaCasa.add((Button) findViewById(R.id.btnReservaPlusCasa7));

        btnPlusTitularsFora.add((Button) findViewById(R.id.btnTitularPlusFora1));
        btnPlusTitularsFora.add((Button) findViewById(R.id.btnTitularPlusFora2));
        btnPlusTitularsFora.add((Button) findViewById(R.id.btnTitularPlusFora3));
        btnPlusTitularsFora.add((Button) findViewById(R.id.btnTitularPlusFora4));
        btnPlusTitularsFora.add((Button) findViewById(R.id.btnTitularPlusFora5));

        btnPlusReservaFora.add((Button) findViewById(R.id.btnReservaPlusFora1));
        btnPlusReservaFora.add((Button) findViewById(R.id.btnReservaPlusFora2));
        btnPlusReservaFora.add((Button) findViewById(R.id.btnReservaPlusFora3));
        btnPlusReservaFora.add((Button) findViewById(R.id.btnReservaPlusFora4));
        btnPlusReservaFora.add((Button) findViewById(R.id.btnReservaPlusFora5));
        btnPlusReservaFora.add((Button) findViewById(R.id.btnReservaPlusFora6));
        btnPlusReservaFora.add((Button) findViewById(R.id.btnReservaPlusFora7));


        for (int i = 0; i < 5; i++) {
            btnMinusTitularsCasa.get(i).setOnClickListener(new MinusClickListener(lblGolsTitularsCasa.get(i), lblGolsCasa));
            btnMinusTitularsFora.get(i).setOnClickListener(new MinusClickListener(lblGolsTitularsFora.get(i), lblGolsFora));

            btnPlusTitularsCasa.get(i).setOnClickListener(new PlusClickListener(lblGolsTitularsCasa.get(i),lblGolsCasa));
            btnPlusTitularsFora.get(i).setOnClickListener(new PlusClickListener(lblGolsTitularsFora.get(i),lblGolsFora));
        }

        for (int i = 0; i < 7; i++) {
            btnMinusReservaCasa.get(i).setOnClickListener(new MinusClickListener(lblGolsReservaCasa.get(i),lblGolsCasa));
            btnMinusReservaFora.get(i).setOnClickListener(new MinusClickListener(lblGolsReservaFora.get(i),lblGolsFora));

            btnPlusReservaCasa.get(i).setOnClickListener(new PlusClickListener(lblGolsReservaCasa.get(i),lblGolsCasa));
            btnPlusReservaFora.get(i).setOnClickListener(new PlusClickListener(lblGolsReservaFora.get(i),lblGolsFora));
        }
    }

    class PlusClickListener implements View.OnClickListener {

        TextView gols;
        TextView golsEquip;

        public PlusClickListener(TextView gols, TextView golsEquip) {
            this.gols = gols;
            this.golsEquip = golsEquip;
        }

        @Override
        public void onClick(View v) {
            int nGols = Integer.parseInt(gols.getText().toString());
            nGols++;
            gols.setText(String.valueOf(nGols));

            int nGolsEquip = Integer.parseInt(golsEquip.getText().toString());
            nGolsEquip++;
            golsEquip.setText((String.valueOf(nGolsEquip)));
        }
    }

    class MinusClickListener implements View.OnClickListener {

        TextView gols;
        TextView golsEquip;

        public MinusClickListener(TextView gols, TextView golsEquip) {
            this.gols = gols;
            this.golsEquip = golsEquip;
        }

        @Override
        public void onClick(View v) {
            int nGols = Integer.parseInt(gols.getText().toString());
            if (nGols > 0) {
                nGols--;
                gols.setText(String.valueOf(nGols));

                int nGolsEquip = Integer.parseInt(golsEquip.getText().toString());
                nGolsEquip--;
                golsEquip.setText((String.valueOf(nGolsEquip)));
            }


        }
    }

    private void showDatePicker() {
        DatePickerFragment date = new DatePickerFragment();
        /**
         * Set Up Current Date Into dialog
         */
        Calendar calender = Calendar.getInstance();
        Bundle args = new Bundle();
        args.putInt("year", calender.get(Calendar.YEAR));
        args.putInt("month", calender.get(Calendar.MONTH));
        args.putInt("day", calender.get(Calendar.DAY_OF_MONTH));
        date.setArguments(args);
        /**
         * Set Call back to capture selected date
         */
        date.setCallBack(ondate);
        date.show(getSupportFragmentManager(), "Date Picker");
    }

    DatePickerDialog.OnDateSetListener ondate = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
        String fecha = String.valueOf(dayOfMonth) + "/" + String.valueOf(monthOfYear+1)
                + "/" + String.valueOf(year);
        Log.println(Log.DEBUG,"FECHA ELEGIDA", fecha);

        setTitle(fecha);

        date = new Date(year, monthOfYear+1, dayOfMonth);
        }
    };

    private void createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setMessage("Ha de seleccionar quan s'ha jugat el partit.").setTitle("Seleccionar data");
        builder.setPositiveButton("Seleccionar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                showDatePicker();
                //guardarPartit(true);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        dialogData = builder.create();
    }

    private void guardarPartit(boolean tanca) {
        realm.beginTransaction();

        Partido p = new Partido();
        p.setEquipA(equipCasa);
        p.setEquipB(equipFora);
        p.setDataRealitzat(date);

        p.setGolsA(Integer.valueOf(lblGolsCasa.getText().toString()));
        p.setGolsB(Integer.valueOf(lblGolsFora.getText().toString()));

        for (int i = 0; i < 5; ++i) {
            p.getEquipA().getTitulars().get(i).addGols(Integer.valueOf(lblGolsTitularsCasa.get(i).getText().toString()));
            p.getEquipB().getTitulars().get(i).addGols(Integer.valueOf(lblGolsTitularsFora.get(i).getText().toString()));
        }

        for (int i = 0; i < 7; ++i) {
            p.getEquipA().getReservas().get(i).addGols(Integer.valueOf(lblGolsReservaCasa.get(i).getText().toString()));
            p.getEquipB().getReservas().get(i).addGols(Integer.valueOf(lblGolsReservaFora.get(i).getText().toString()));
        }

        realm.copyToRealm(p);
        realm.commitTransaction();

        if (tanca) {
            Intent intent = new Intent(this, JornadaActivity.class);
            startActivity(intent);
        }

    }
}
