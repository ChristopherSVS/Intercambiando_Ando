package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private ImageView ivLogo, ivUsuario;
    private TextView tvUsuario, tvUserCorreo;
    private SearchView svBuscar;
    private Spinner sEstados, sCategorias, sEstatus;
    private RecyclerView rvProductos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsuario = findViewById(R.id.tvUsuario);
        tvUserCorreo = findViewById(R.id.tvUserCorreo);

        ivLogo = findViewById(R.id.ivLogo);
        ivUsuario = findViewById(R.id.ivUsuario);

        svBuscar = findViewById(R.id.svBuscar);

        sEstados = findViewById(R.id.sEstados);
        sCategorias = findViewById(R.id.sCategorias);
        sEstatus = findViewById(R.id.sEstatus);

        rvProductos = findViewById(R.id.rvProductos);
    }



}