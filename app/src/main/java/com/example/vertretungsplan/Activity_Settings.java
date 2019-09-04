package com.example.vertretungsplan;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;


public class Activity_Settings extends AppCompatActivity{
    private String stufe;
    private SharedPreferences speicher;
    private SharedPreferences.Editor editor;
    private ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__settings);

        Toolbar toolbar = (Toolbar)findViewById(R.id.toolBar);

        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        speicher = getApplicationContext().getSharedPreferences("Daten", 0);
        editor = speicher.edit();

        Switch schalter = (Switch) findViewById(R.id.schalter);
        schalter.setChecked(speicher.getBoolean("Daten2", false));
        schalter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    editor.putBoolean("Daten2", true);
                    editor.apply();
                    Spinner auswahl = (Spinner)findViewById(R.id.auswahl);
                    auswahl.setEnabled(true);

                }else{
                    editor.putBoolean("Daten2", false);
                    editor.putString("Daten1", "");
                    editor.apply();
                    stufe = "";
                    findViewById(R.id.auswahl).setEnabled(false);
                }
            }
        });

        final Spinner auswahl = (Spinner) findViewById(R.id.auswahl);
        adapter = ArrayAdapter.createFromResource(this, R.array.stufen, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        auswahl.setAdapter(adapter);
        if(!schalter.isChecked())
        {
            auswahl.setEnabled(false);
        }
        auswahl.setSelection(adapter.getPosition(speicher.getString("Daten1", "Klappt nicht")));
        auswahl.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stufe = auswahl.getItemAtPosition(position).toString();
                editor.putString("Daten1", stufe);
                editor.apply();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        int idHome = android.R.id.home;

        if (id == idHome) {
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
