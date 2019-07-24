package com.example.rick_hp.myapplication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.support.v7.widget.AppCompatTextView;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.rick_hp.myapplication.activities.LoginActivity;
import com.example.rick_hp.myapplication.activities.UsersListActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_SIGNUP = 0;
    private AppCompatActivity activity = MainActivity.this;
    public AppCompatTextView textViewName4;

    // Escribir un mensaje a la bd
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("home");
    DatabaseReference refalarma;
    ToggleButton btnToggle;
    TextView textEstadoPulsador;
    Button signOut;
    private FirebaseAuth auth;
    private long backPressedTime = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        signOut = (Button) findViewById(R.id.button2);

        refalarma = myRef.child("alarma");
        auth = FirebaseAuth.getInstance();

        getSupportActionBar().hide();
        getSupportActionBar().setTitle("");
        initViews();
        initObjects();

        btnToggle = (ToggleButton) findViewById(R.id.toggleButton);
        btnToggle.setTextOn("APAGAR");
        btnToggle.setTextOff("ENCENDER");
        textEstadoPulsador = (TextView) findViewById(R.id.textViewPulsador);
        controlLED(refalarma, btnToggle);
        estadoPulsador(refalarma, textEstadoPulsador);

        Button boton1 = findViewById(R.id.btn1);
        boton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent i = new Intent(getApplicationContext(), cambiarcontra_Activity.class);
                    startActivity(i);
                    finish();
            }
        });

        final Button boton2 = findViewById(R.id.btn2);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), temperatura_Activity.class);
                startActivity(i);
                finish();

            }
        });
        Button boton3 = findViewById(R.id.btn3);
        boton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), palabraclave_Activity.class);
                startActivity(i);
                finish();

            }
        });
        Button boton4 = findViewById(R.id.btn4);
        boton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), incendio_activity.class);
                startActivity(i);
                finish();

            }
        });
        Button boton5 = findViewById(R.id.btn5);
        boton5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), correo_Activity.class);
                startActivity(i);
                finish();

            }
        });
        Button boton6 = findViewById(R.id.btn6);
        boton6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), Ayuda_Activity.class);
                startActivity(i);
                finish();

            }
        });
        Button boton7 = findViewById(R.id.button2);
        boton7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(i);
                finish();

            }
        });
        Button boton8 = findViewById(R.id.btn9);
        boton8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), UsersListActivity.class);
                startActivity(i);
                finish();

            }
        });

            signOut.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            signOut();
        }
    });

}
    //metodo cerrar sesión
    public void signOut() {
        auth.signOut();
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
        finish();
    }

    public void initViews() {
        textViewName4 = (AppCompatTextView) findViewById(R.id.textViewName3);
    }

    public void initObjects() {
        String emailFromIntent = getIntent().getStringExtra("EMAIL");
        textViewName4.setText(emailFromIntent);
    }

    private void controlLED(final DatabaseReference refLed, final ToggleButton toggle_btn) {
        toggle_btn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                refalarma.setValue(isChecked); //cambia el valor en la bd de refalarma
            }
        });
        refalarma.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Boolean estado_led = (Boolean) dataSnapshot.getValue(); //obtiene el valor de refalarma de la bd
                toggle_btn.setChecked(estado_led); //cambia el estado del marcador
                if (estado_led) { //segun el estado cambia lo que dice el boton
                    toggle_btn.setTextOn("APAGAR");
                } else {
                    toggle_btn.setTextOff("ENCENDER");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) { //ocurre un error en la bd
            }
        });
    }
        private void estadoPulsador(final DatabaseReference refPulsador_a, final TextView textEstadoPulsador) {
            refPulsador_a.addValueEventListener(new ValueEventListener() { //obtener estado de la base de datos
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Boolean estado_pulsador = (Boolean) dataSnapshot.getValue(); //obtiene el valor de refalarma de la bd
                    if (estado_pulsador==Boolean.TRUE){  //segun el valor del estado, muestra el Textview
                        textEstadoPulsador.setText("Activado");
                    }else {
                        textEstadoPulsador.setText("Desactivado");
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) { } //ocurre un error en la bd
            });


    }
    public void onBackPressed() {        // Prevenir la desconexión accidental
        long t = System.currentTimeMillis();
        if (t - backPressedTime > 2000) {    // 2 secs
            backPressedTime = t;
            Toast.makeText(this, "Presiona otra vez para cerrar sesión",
                    Toast.LENGTH_SHORT).show();
        } else {
            auth.signOut();
            super.onBackPressed();       // bye
        }
    }

}


