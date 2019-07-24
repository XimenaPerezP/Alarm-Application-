package com.example.rick_hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class temperatura_Activity extends AppCompatActivity {
    private static final String TAGLOG = "firebase-db";
    private TextView lblTemperatura;
    private TextView lblHumedad;
    private ValueEventListener eventListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("home");
    DatabaseReference reftemperatura,refestadotemp;
    ToggleButton btnToggle2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temperatura);
        getSupportActionBar().hide();
        reftemperatura = myRef.child("temperatura");
        refestadotemp = myRef.child("estado_temp");
        btnToggle2 = (ToggleButton) findViewById(R.id.toggleButton2);
        btnToggle2.setTextOn("APAGAR");
        btnToggle2.setTextOff("ENCENDER");
        estadotemp(refestadotemp,btnToggle2);

        Button boton = findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();

            }
        });
        lblTemperatura = (TextView)findViewById(R.id.textView5);
        lblHumedad = (TextView)findViewById(R.id.textView6);
        eventListener = new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Prediccion pred = dataSnapshot.getValue(Prediccion.class); //obtiene valores de la base de datos
                    lblTemperatura.setText(pred.getTemperatura() + "ºC"); //obtiene solo temperatura y la muestra en el textview
                    lblHumedad.setText(pred.getHumedad() + "%");  //obtiene humedad y la muestra en el textview
                    Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
                }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };

        myRef.addValueEventListener(eventListener);

        }
    private void estadotemp(final DatabaseReference refLed, final ToggleButton toggle_btn) {

        toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refestadotemp.setValue(isChecked);
            }
        });

        refestadotemp.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_led = (Boolean) dataSnapshot.getValue();
                toggle_btn.setChecked(estado_led);
                if (estado_led) {
                    toggle_btn.setTextOn("APAGAR");
                } else {
                    toggle_btn.setTextOff("ENCENDER");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }

        });
    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    }



