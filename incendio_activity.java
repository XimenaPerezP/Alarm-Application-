package com.example.rick_hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.AppCompatTextView;
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

public class incendio_activity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    private AppCompatActivity activity = incendio_activity.this;
    public AppCompatTextView textViewName4;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("home");
    DatabaseReference refincendio;
    ToggleButton btnToggle;
    TextView textEstadoPulsador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.incendio);
        refincendio = myRef.child("incendio");
        getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        btnToggle = (ToggleButton) findViewById(R.id.toggleButton3);
        btnToggle.setTextOn("APAGAR");
        btnToggle.setTextOff("ENCENDER");
        textEstadoPulsador = (TextView) findViewById(R.id.textViewPulsador2);
        controlLED(refincendio, btnToggle);
        estadoPulsador(refincendio, textEstadoPulsador);
        Button boton = findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();

            }
        });
    }
    private void controlLED(final DatabaseReference refLed, final ToggleButton toggle_btn) {

        toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refincendio.setValue(isChecked);
            }
        });

        refincendio.addValueEventListener(new ValueEventListener() {
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
    private void estadoPulsador(final DatabaseReference refPulsador_a, final TextView textEstadoPulsador) {

        refPulsador_a.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_pulsador = (Boolean) dataSnapshot.getValue();
                if (estado_pulsador==Boolean.TRUE){
                    textEstadoPulsador.setText("Activado");
                }else {
                    textEstadoPulsador.setText("Desactivado");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        });


    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    //JALAR FIREBASE
    //VALIDAR CUANDO LA ALARMA ESTA ACTIVADA
}
