package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NuevaContraActivity extends AppCompatActivity {

    private EditText etNuevaContra, etConfirmaContra;
    private TextView tvErrorContra, tvErrorConfirma;
    private Button bNuevaContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_contra);

        etNuevaContra = findViewById(R.id.etNuevaContra);
        etConfirmaContra = findViewById(R.id.etConfirmaContra);

        tvErrorContra = findViewById(R.id.tvErrorContra);
        tvErrorConfirma = findViewById(R.id.tvErrorConfirma);

        bNuevaContra = findViewById(R.id.bNuevaContra);

    }
}