package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

public class OfertasActivity extends AppCompatActivity {

    private RecyclerView rvOfertas;
    private TextView tvOfertas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);

        tvOfertas = findViewById(R.id.tvOfertas);

        rvOfertas = findViewById(R.id.rvOfertas);

    }
}