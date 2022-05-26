package com.example.intercambiando_ando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

public class PerfilActivity extends AppCompatActivity {

    private ImageButton ibUsuario;
    private TextView tvUsuarioCreacion, tvCorreoPerfil, tvID;
    private EditText etUsername, etCat;
    private SearchView svHistorial;
    private Spinner sEst, sStatus;
    private RecyclerView rvHistorial;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        ibUsuario = findViewById(R.id.ibUsuario);

        etUsername = findViewById(R.id.etUsername);
        etCat = findViewById(R.id.etCat);

        tvCorreoPerfil = findViewById(R.id.tvCorreoPerfil);
        tvID = findViewById(R.id.tvID);
        tvUsuarioCreacion = findViewById(R.id.tvUsuarioCreacion);

        svHistorial = findViewById(R.id.svHistorial);

        sEst = findViewById(R.id.sEst);
        sStatus = findViewById(R.id.sStatus);

        rvHistorial = findViewById(R.id.rvHistorial);

        ibUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilActivity.this, FotoActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        int id_layout = R.menu.menu_opciones_4;
        getMenuInflater().inflate(id_layout,menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_opc_nom:
                cambiarNombre();
                break;
            case R.id.menu_opc_cam:
                cambiarContra();
                break;
            case R.id.menu_opc_borra:
                borrarPerfil();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void cambiarNombre() {

    }

    private void cambiarContra() {
        Intent intent = new Intent(PerfilActivity.this, NuevaContraActivity.class);
        startActivity(intent);
    }

    private void borrarPerfil() {

    }

}