package com.example.intercambiando_ando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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

    private int id = 0;

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

        bOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescripcionActivity.this, OfertasActivity.class);
                startActivity(intent);
            }
        });

        ibProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescripcionActivity.this, FotoActivity.class);
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

        int id_layout = R.menu.menu_opciones_3;

        if (id!=0){
            id_layout = R.menu.menu_opciones_5;
        }

        getMenuInflater().inflate(id_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_opc_editar:
                editarProducto();
                break;
            case R.id.menu_opc_eliminar:
                eliminarProducto();
                break;
            case R.id.menu_opc_ofer:
                eliminarOferta();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editarProducto() {
    }

    private void eliminarProducto() {
    }

    private void eliminarOferta() {
    }

}