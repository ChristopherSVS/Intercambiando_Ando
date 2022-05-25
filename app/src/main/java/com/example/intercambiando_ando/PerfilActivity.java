package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    private ImageButton ibUsuario;
    private TextView tvUsuarioCreacion, tvCorreoPerfil, tvID;
    private EditText etUsername;
    private SearchView svHistorial;
    private Spinner sEst, sCat, sStatus;
    private RecyclerView rvHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ibUsuario = findViewById(R.id.ibUsuario);

        tvCorreoPerfil = findViewById(R.id.tvCorreoPerfil);
        tvID = findViewById(R.id.tvID);
        tvUsuarioCreacion = findViewById(R.id.tvUsuarioCreacion);

        svHistorial = findViewById(R.id.svHistorial);

        sEst = findViewById(R.id.sEst);
        sCat = findViewById(R.id.sCat);
        sStatus = findViewById(R.id.sStatus);

        rvHistorial = findViewById(R.id.rvHistorial);

    }
}