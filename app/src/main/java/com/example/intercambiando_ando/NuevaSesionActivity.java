package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NuevaSesionActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etContrasena, etContrasenaConfi;
    private TextView tvErrorName, tvErrorCorreo, tvContraError;
    private Button bImagen, bCrearUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_sesion);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etContrasenaConfi = findViewById(R.id.etContrasenaConfi);

        tvErrorName = findViewById(R.id.tvErrorName);
        tvErrorCorreo = findViewById(R.id.tvErrorCorreo);
        tvContraError = findViewById(R.id.tvContraError);

        bImagen = findViewById(R.id.bImagen);
        bCrearUsuario = findViewById(R.id.bCrearUsuario);

    }
}