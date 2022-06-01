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
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class NuevaContraActivity extends AppCompatActivity {

    private int id = 0;
    public static final String U_ID = "U_ID";
    public static final String U_USERNAME = "U_USERNAME";
    public static final String U_EMAIL = "U_EMAIL";
    public static final String U_IMAGEN = "U_IMAGEN";

    private EditText etNuevaContra, etConfirmaContra;
    private TextView tvErrorContra, tvErrorConfirma;
    private Button bNuevaContra;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_contra);

        requestQueue = Volley.newRequestQueue(this);

        etNuevaContra = findViewById(R.id.etNuevaContra);
        etConfirmaContra = findViewById(R.id.etConfirmaContra);

        tvErrorContra = findViewById(R.id.tvErrorContra);
        tvErrorConfirma = findViewById(R.id.tvErrorConfirma);

        bNuevaContra = findViewById(R.id.bNuevaContra);

        Intent intent = getIntent();
        this.id = intent.getIntExtra(U_ID, id);

        bNuevaContra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvErrorContra.setVisibility(View.INVISIBLE);
                tvErrorConfirma.setVisibility(View.INVISIBLE);
                String contra = etNuevaContra.getText().toString().trim();
                String confir = etConfirmaContra.getText().toString().trim();
                if(contra.equals(confir)) {
                    validacionContrasena();
                }else{
                    tvErrorConfirma.setVisibility(View.VISIBLE);
                }
            }
        });

    }

    private void validacionContrasena() {
        String url = MainActivity.BASE_URL + "Usuarios.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                procesarLista(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainActivity", "Error de comunicacion: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }

    private void procesarLista(JSONArray response) {

        String contra = etNuevaContra.getText().toString().trim();

        if(response != null){
            try {
                for (int i=0;i<response.length();i++){

                    JSONObject fila = response.getJSONObject(i);

                    int id = fila.getInt("id");
                    String username = fila.getString("username");
                    String password = fila.getString("password");
                    String email = fila.getString("email");
                    String Image = fila.getString("Image");

                    Usuarios usuarios = new Usuarios();

                    usuarios.setId(id);
                    usuarios.setUsername(username);
                    usuarios.setPassword(password);
                    usuarios.setEmail(email);


                    if (!contra.equals(password)) {
                        guardarNuevaContra();
                        cambioNotificacion();
                        this.id = id;
                        Intent intent = new Intent(NuevaContraActivity.this, MainActivity.class);
                        intent.putExtra(U_ID, id);
                        intent.putExtra(U_USERNAME, username);
                        intent.putExtra(U_EMAIL, email);
                        intent.putExtra(U_IMAGEN, Image);
                        startActivity(intent);
                    }else{
                        tvErrorContra.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void guardarNuevaContra() {

        String nuevaContra = etNuevaContra.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MainActivity.BASE_URL + "guardar.php";

        Map<String, String> mapa = new HashMap<>();
        if (id != 0) {
            mapa.put("id", id + "");
        }
        mapa.put("password", nuevaContra);

        JSONObject parametros = new JSONObject(mapa);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarRespuestaGuardado(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NuevaContraActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("NuevaContraActivity", error.getMessage());
            }
        });
        queue.add(request);

    }

    private void procesarRespuestaGuardado(JSONObject response) {
        try {
            boolean ok = response.getBoolean("ok");
            String error = response.getString("error");
            if (ok){
                finish();
            }else {
                Toast.makeText(NuevaContraActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("NuevaContraActivity", error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void cambioNotificacion() {
        //Configurar la notificación
        String titulo = "Notificacion del Sistema";
        String pedido = "Su contraseña a sido cambiada";
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