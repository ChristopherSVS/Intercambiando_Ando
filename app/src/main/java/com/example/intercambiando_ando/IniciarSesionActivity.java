package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class IniciarSesionActivity extends AppCompatActivity {

    private TextView tvErrorUser, tvErrorContrasena;
    private EditText etCorreo2, etContra;
    private Button bRecuperar, bIniciar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        etCorreo2 = findViewById(R.id.etCorreo2);
        etContra = findViewById(R.id.etContra);

        tvErrorUser = findViewById(R.id.tvErrorUser);
        tvErrorContrasena = findViewById(R.id.tvErrorContrasena);

        bRecuperar = findViewById(R.id.bRecuperar);
        bIniciar = findViewById(R.id.bIniciar);

        bIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesionActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        bRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesionActivity.this, RecuperarActivity.class);
                startActivity(intent);
            }
        });

    }
}