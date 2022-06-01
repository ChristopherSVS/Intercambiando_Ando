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

public class NuevaSesionActivity extends AppCompatActivity {

    public static final String I_CLAVE = "I_CLAVE";
    public static final String ID_CLAVE = "ID_CLAVE";
    private EditText etNombre, etCorreo, etContrasena, etContrasenaConfi;
    private TextView tvErrorName, tvErrorCorreo, tvContraError;
    private Button bCrearUsuario;
    private int id = 0;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nueva_sesion);

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etContrasena = findViewById(R.id.etContrasena);
        etContrasenaConfi = findViewById(R.id.etContrasenaConfi);

        tvErrorName = findViewById(R.id.tvErrorName);
        tvErrorCorreo = findViewById(R.id.tvErrorCorreo);
        tvContraError = findViewById(R.id.tvContraError);

        requestQueue = Volley.newRequestQueue(this);

        bCrearUsuario = findViewById(R.id.bCrearUsuario);


        bCrearUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvContraError.setVisibility(View.INVISIBLE);
                tvErrorName.setVisibility(View.INVISIBLE);
                tvErrorCorreo.setVisibility(View.INVISIBLE);
                verificarDatos();
            }
        });

    }

    private void usuarioNotificacion() {
        //Configurar la notificación
        String titulo = "Notificacion del Sistema";
        String pedido = "Su usuario a sido validado";
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

    private void verificarDatos() {

        String url = MainActivity.BASE_URL + "usuarios.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                guardarUsuario(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("NuevaSesionActivity", "Error de comunicacion: " + error.getMessage());
            }
        });
        requestQueue.add(request);


    }

    private void guardarUsuario(JSONArray response) {

        String nom = etNombre.getText().toString().trim();
        String cor = etCorreo.getText().toString().trim();
        String pass = etContrasena.getText().toString().trim();
        String contra = etContrasenaConfi.getText().toString().trim();

        if(response != null){
            try {
                for (int i=0;i<response.length();i++){

                    JSONObject fila = response.getJSONObject(i);

                    int id = fila.getInt("id");
                    String username = fila.getString("username");
                    String password = fila.getString("password");
                    String email = fila.getString("email");
                    String Imagen = fila.getString("Imagen");
                    long creacion = fila.getLong("creacion");

                    Usuarios usuarios = new Usuarios();

                    usuarios.setId(id);
                    usuarios.setUsername(username);
                    usuarios.setPassword(password);
                    usuarios.setEmail(email);
                    usuarios.setImagen(Imagen);
                    usuarios.setFecha_creacion(creacion);

                    if (!nom.equals(username)) {
                        if(!cor.equals(email)){
                            if (pass.equals(contra)) {
                                this.id = id;
                                guardarDatos();
                                break;
                            }else{
                                tvContraError.setVisibility(View.VISIBLE);
                            }
                        }else{
                            tvErrorCorreo.setVisibility(View.VISIBLE);
                        }
                    }else{
                        tvErrorName.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private void guardarDatos() {
        String Nombre = etNombre.getText().toString().trim();
        String Email = etCorreo.getText().toString().trim();
        String Contrasena = etContrasenaConfi.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MainActivity.BASE_URL + "username.php";

        Map<String, String> mapa = new HashMap<>();

        if (id != 0) {
            mapa.put("id", id + "");
        }
        mapa.put("username", Nombre);
        mapa.put("password", Contrasena);
        mapa.put("email", Email);

        JSONObject parametros = new JSONObject(mapa);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarRespuestaGuardado(response);
                usuarioNotificacion();
                Intent intent = new Intent(NuevaSesionActivity.this, FotoActivity.class);
                intent.putExtra(ID_CLAVE, id);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(NuevaSesionActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("NuevaSesionActivity", error.getMessage());
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
                Toast.makeText(NuevaSesionActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("NuevaSesionActivity", error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}