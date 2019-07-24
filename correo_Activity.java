package com.example.rick_hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class correo_Activity extends AppCompatActivity {
    private static final String TAGLOG = "firebase-db";
    private TextView lblcorreo;
    private ValueEventListener eventListener;
    private EditText inputcorreo;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("home");
    DatabaseReference refcorreo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.correo);
        getSupportActionBar().hide();
        refcorreo = myRef.child("correo");
        inputcorreo = (EditText) findViewById(R.id.input_ncorreo);
        Button b1 = findViewById(R.id.buttonok);
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
           if (isEditTextContainEmail(inputcorreo)) { //verificar que cumpla con el patron
                refcorreo.setValue(inputcorreo.getText().toString()); //establece correo en la bd
                inputcorreo.setText("");
                Toast.makeText(getApplicationContext(),"Cambio de correo exitoso...", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
           } else {
               Toast.makeText(getApplicationContext(), "Vuelva a ingresar el correo", Toast.LENGTH_SHORT).show();
           }
           }
        });
        Button boton = findViewById(R.id.button);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        lblcorreo = (TextView)findViewById(R.id.input_correo);
        eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Prediccion pred = dataSnapshot.getValue(Prediccion.class); //obtener correo de la base de datos
                lblcorreo.setText("Correo actual: " + pred.getCorreo()); //muestra correo actual en el textview
                Log.e(TAGLOG, "onDataChange:" + dataSnapshot.getValue().toString());
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e(TAGLOG, "Error!", databaseError.toException());
            }
        };
        myRef.addValueEventListener(eventListener);
    }

    public static boolean isEditTextContainEmail(EditText argEditText) {
        try {
            Pattern pattern = Pattern.compile("^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
            //definir un patron de secuencia x@x.com
            Matcher matcher = pattern.matcher(argEditText.getText());
            //verifica si cumple el patron
            return matcher.matches(); //envia un estado de true o false si cumple con el patron
        } catch (Exception e) {
            e.printStackTrace(); //imprimir el registro del stack donde se ha iniciado la excepción
            return false;
        }
    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


}
