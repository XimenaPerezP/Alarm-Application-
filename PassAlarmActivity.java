package com.example.rick_hp.myapplication;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;


public class PassAlarmActivity extends AppCompatActivity {
    private static final String TAG = "PassAlarmActivity";

    EditText _contrasenaText;
    EditText _contrasenanuevaText;
    EditText _contrasenanueva2Text;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cam_contra);
    }

    public boolean validate() {
        boolean valid = true;
        String contrasena = _contrasenaText.getText().toString();
        String contrasenanueva = _contrasenanuevaText.getText().toString();
        String contrasenanueva2 = _contrasenanueva2Text.getText().toString();

        if (contrasena.isEmpty() || contrasena.length() < 4 || contrasena.length() > 10) {
            _contrasenaText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _contrasenaText.setError(null);
        }

        if (contrasenanueva.isEmpty() || contrasenanueva.length() < 4 || contrasenanueva.length() > 10) {
            _contrasenanuevaText.setError("Password Do not match");
            valid = false;
        } else {
            _contrasenanuevaText.setError(null);
        }
        if (contrasenanueva2.isEmpty() || contrasenanueva2.length() < 4 || contrasenanueva2.length() > 10 || !(contrasenanueva2.equals(contrasenanueva))) {
            _contrasenanueva2Text.setError("Password Do not match");
            valid = false;
        } else {
            _contrasenanueva2Text.setError(null);
        }

        return valid;
    }
}
