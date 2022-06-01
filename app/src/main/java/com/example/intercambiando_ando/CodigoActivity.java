package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class CodigoActivity extends AppCompatActivity {

    public static final String U_ID = "U_ID";
    private int id = 0;

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

        Intent intent = getIntent();
        this.id = intent.getIntExtra(RecuperarActivity.U_ID, id);

        bVerifica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvErrorCodigo.setVisibility(View.INVISIBLE);
                String codigo = etCodigo.getText().toString().trim();
                if (codigo == "112233") {
                    comprobarCodigo();
                }
                else{
                    tvErrorCodigo.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void comprobarCodigo() {
        Intent intent = new Intent(CodigoActivity.this, NuevaContraActivity.class);
        intent.putExtra(U_ID, this.id);
        startActivity(intent);
    }


}