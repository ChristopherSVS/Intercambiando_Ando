package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

public class DescripcionActivity extends AppCompatActivity {

    private ImageButton ibProducto;
    private TextView tvUserProducto, tvIDProducto;
    private EditText etNomProducto;
    private Spinner sEstProducto, sEstusProdcto, sCatProducto;
    private Button bOfertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        ibProducto = findViewById(R.id.ibProducto);

        tvUserProducto = findViewById(R.id.tvUserProduto);
        tvIDProducto = findViewById(R.id.tvIDProducto);

        etNomProducto = findViewById(R.id.etNomProducto);

        sEstProducto = findViewById(R.id.sEstProducto);
        sEstusProdcto = findViewById(R.id.sEstusProducto);
        sCatProducto = findViewById(R.id.sCatProducto);

        bOfertas = findViewById(R.id.bOfertas);

    }
}