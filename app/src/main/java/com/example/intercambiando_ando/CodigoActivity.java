package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CodigoActivity extends AppCompatActivity {

    private TextView tvVerificaCod, tvErrorCodigo;
    private EditText etCodigo;
    private Button bVerifica;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_codigo);

        tvVerificaCod = findViewById(R.id.tvVerificaCod);
        tvErrorCodigo = findViewById(R.id.tvErrorCodigo);

        etCodigo = findViewById(R.id.etCodigo);

        bVerifica = findViewById(R.id.bVerifica);

    }
}