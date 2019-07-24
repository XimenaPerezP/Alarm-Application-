package com.example.rick_hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Random;

public class palabraclave_Activity extends AppCompatActivity {
    private static final String TAGLOG = "firebase-db";
    private ValueEventListener eventListener;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("home");
    DatabaseReference refpalclave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.palabraclave);
        getSupportActionBar().hide();
        refpalclave = myRef.child("palclave"); //obtener variable de la bd
        final Random r = new Random(); // declara variable random
        final String[] elementos = {"seguridad","innovador","prototipo","icono","algoritmo" };
        //array de posibles palabras clave que se pueden seleccionar por random
        final TextView txtScram = (TextView) findViewById(R.id.textView4);
        Button boton = findViewById(R.id.button);
        Button btnObtener = (Button) findViewById(R.id.buttonok);
        Button button7 = (Button)findViewById(R.id.button7);

        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refpalclave.setValue(txtScram.getText().toString()); //establecer palabra clave en bd
                txtScram.setText("");
                Toast.makeText(getApplicationContext(),
                        "Palabra clave guardada", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        btnObtener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ranEle = elementos[r.nextInt(elementos.length)];//seleccion palabra por random
                txtScram.setText(ranEle); //escribir palabra en textview

            }

        });
        button7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();


            }
        });
    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();

    }
    //GENERAR desde la aplicacion
    // SEGURIDAD INNOVADOR TECNOLOGICO ANDROIDE PROTOTIPO
    //ENVIAR FIREBASE
    // Jalar del firebase

}
