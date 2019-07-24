package com.example.rick_hp.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.util.regex.Pattern;


public class cambiarcontra_Activity extends AppCompatActivity {
    private EditText editText1,editText2;
    //conexión con la base de datos
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("home");
    DatabaseReference refcontrasena;
    public static final String REGEX_LETRAS = "^[a-d0-9]+$";
    //solo para ingresar numero de 0-9 y letras de a-d


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_contra);
        getSupportActionBar().hide();
        refcontrasena = myRef.child("contrasena");
        editText1 = (EditText) findViewById(R.id.input_contranueva);
        editText2 = (EditText) findViewById(R.id.input_contranueva2);
        Button b1 = findViewById(R.id.buttonok);
        Button boton2 = findViewById(R.id.buttoncancel);
        boton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }
        });
        b1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String strPass1 = editText1.getText().toString();
                String strPass2 = editText2.getText().toString();
                    if (strPass1.equals(strPass2)) {
                        if (validarCampo(strPass1)) { //llamar al metodo para validar
                            byte[] base64Data = strPass2.getBytes();
                            String base64Str = "";
                            try{
                                base64Str = base64.encryptBASE64(base64Data);
                            } catch (Exception e){
                                e.printStackTrace();
                            }
                            String strPass3 = base64Str.replaceAll("\n","");
                            refcontrasena.setValue(strPass3); //establecer el valor en la bd
                            editText2.setText(base64Str);
                            Toast.makeText(getApplicationContext(),
                                    "Cambio de contraseña exitoso...", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Las contraseñas no son iguales!", Toast.LENGTH_SHORT).show();
                    }

            }
        });
    }

    public boolean validarCampo(String l){
        Pattern patron = Pattern.compile(REGEX_LETRAS); //caracteres permitidos
        String stCampoNumero = editText1.getText().toString().trim();
        if(patron.matcher(stCampoNumero).matches()){ //verificar que la contraseña no tenga caracteres erroneos
            Toast.makeText(this, "Validación correcta", Toast.LENGTH_SHORT).show();
            return true;
        }else{
            Toast.makeText(this, "Caracteres no permitidos, vuelva a ingresar", Toast.LENGTH_SHORT).show();
            return false;
        }

    }
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }


    }

    //VALIDAR ENTRADA DE CONTRASEÑA
