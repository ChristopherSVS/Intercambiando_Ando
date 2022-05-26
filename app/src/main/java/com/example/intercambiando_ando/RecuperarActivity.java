package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RecuperarActivity extends AppCompatActivity {

    private EditText etCorreoRec;
    private TextView tvErrorEmail;
    private Button bCorreo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        etCorreoRec = findViewById(R.id.etCorreoRec);

        tvErrorEmail = findViewById(R.id.tvErrorEmail);

        bCorreo = findViewById(R.id.bCorreo);

        bCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("text/html");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"androdfast@gmail.com"});
                emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Intercambiando-Ando, Recuperacion de Contraseña");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Codigo para recuperar su contraseña de usuario");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Codigo de recuperacion: 112233");
                try {
                    //Enviamos el Correo iniciando una nueva Activity con el emailIntent.
                    startActivity(Intent.createChooser(emailIntent, "Enviar Correo de Recuperacion"));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(RecuperarActivity.this, "No hay ningun usuario con ese correo.", Toast.LENGTH_SHORT).show();
                }
                enviarCorreo();
            }
        });

    }

    private void enviarCorreo() {
        Intent intent = new Intent(RecuperarActivity.this, CodigoActivity.class);
        startActivity(intent);
    }
}