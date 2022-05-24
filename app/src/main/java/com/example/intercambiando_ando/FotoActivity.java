package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class FotoActivity extends AppCompatActivity {

    private ImageButton ibFoto;
    private FloatingActionButton fabSubir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        ibFoto = findViewById(R.id.ibFoto);

        fabSubir = findViewById(R.id.fabSubir);

    }
}