package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class NuevaContraActivity extends AppCompatActivity {

    private EditText etNuevaContra, etConfirmaContra;
    private TextView tvErrorContra, tvErrorConfirma;
    private Button bNuevaContra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_contra);

        etNuevaContra = findViewById(R.id.etNuevaContra);
        etConfirmaContra = findViewById(R.id.etConfirmaContra);

        tvErrorContra = findViewById(R.id.tvErrorContra);
        tvErrorConfirma = findViewById(R.id.tvErrorConfirma);

        bNuevaContra = findViewById(R.id.bNuevaContra);

        bNuevaContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                errorNotificacion();
                Intent intent = new Intent(NuevaContraActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    private void errorNotificacion() {
        //Configurar la notificación
        String titulo = "La contaseña es Invalida";
        String pedido = "La contraseña debe";
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel = new NotificationChannel("NotificationFullhd","NotificationFullhd", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        NotificationCompat.Builder notificacion =
                new NotificationCompat.Builder(this,"NotificationFullhd")
                        .setSmallIcon(R.drawable.logotipo)
                        .setContentTitle(titulo)
                        .setContentText(pedido)
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        //Dar un identificador a la notificación
        int notificacionId = 1;
        //Tomar la referencia al servicio de notificaciones
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        ///Retardo de 5 segundos <---
        new CountDownTimer(5000, 1000){

            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                //Ejecutar la notificación
                managerCompat.notify(notificacionId, notificacion.build());
            }
        }.start();

    }

}