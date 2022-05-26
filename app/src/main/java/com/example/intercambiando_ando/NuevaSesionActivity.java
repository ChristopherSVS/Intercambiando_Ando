package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NuevaSesionActivity extends AppCompatActivity {

    private EditText etNombre, etCorreo, etContrasena, etContrasenaConfi;
    private TextView tvErrorName, tvErrorCorreo, tvContraError;
    private Button bCrearUsuario;

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


        bCrearUsuario = findViewById(R.id.bCrearUsuario);


        bCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(NuevaSesionActivity.this, FotoActivity.class);
                startActivity(intent);
            }
        });

    }
}