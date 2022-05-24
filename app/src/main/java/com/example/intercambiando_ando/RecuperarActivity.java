package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RecuperarActivity extends AppCompatActivity {

    private EditText etCorreoRec;
    private TextView tvErrorEmail;
    private Button bCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        etCorreoRec = findViewById(R.id.etCorreoRec);

        tvErrorEmail = findViewById(R.id.tvErrorEmail);

        bCorreo = findViewById(R.id.bCorreo);

    }
}