package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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

        bVerifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String codigo = etCodigo.getText().toString().trim();
                if (codigo == "112233") {
                    Intent intent = new Intent(CodigoActivity.this, NuevaContraActivity.class);
                    startActivity(intent);
                }
                else{
                    tvErrorCodigo.setVisibility(View.VISIBLE);
                }
            }
        });

    }
}