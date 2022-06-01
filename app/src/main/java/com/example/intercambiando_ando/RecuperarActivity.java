package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class RecuperarActivity extends AppCompatActivity {

    public static final String U_ID = "U_ID";
    private EditText etCorreoRec;
    private TextView tvErrorEmail;
    private Button bCorreo;

    private int id = 0;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar);

        etCorreoRec = findViewById(R.id.etCorreoRec);

        tvErrorEmail = findViewById(R.id.tvErrorEmail);

        bCorreo = findViewById(R.id.bCorreo);

        requestQueue = Volley.newRequestQueue(this);

        bCorreo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tvErrorEmail.setVisibility(View.INVISIBLE);
                validacionEmail();
            }
        });

    }

    private void validacionEmail() {

        String url = MainActivity.BASE_URL + "usuarios.php";
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                procesarLista(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("RecuperaActivity", "Error de comunicacion: " + error.getMessage());
            }
        });
        requestQueue.add(request);

    }

    private void procesarLista(JSONArray response) {

        String correo = etCorreoRec.getText().toString().trim();

        if(response != null){
            try {
                for (int i=0;i<response.length();i++){

                    JSONObject fila = response.getJSONObject(i);

                    int id = fila.getInt("id");
                    String username = fila.getString("username");
                    String email = fila.getString("email");

                    Usuarios usuarios = new Usuarios();

                    usuarios.setId(id);
                    usuarios.setEmail(email);
                    usuarios.setUsername(username);

                    if (correo.equals(email)) {
                        this.id = id;
                        enviarCorreo(email,username);
                        break;
                    }else{
                        tvErrorEmail.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private void enviarCorreo(String email, String username) {
        Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        emailIntent.putExtra(android.content.Intent.EXTRA_TITLE, "Intercambiando-Ando, Recuperacion de Contraseña");
        emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Codigo para recuperar el usuario "+ username);
        emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Codigo de recuperacion: 112233");
        try {
            //Enviamos el Correo iniciando una nueva Activity con el emailIntent.
            startActivity(Intent.createChooser(emailIntent, "Enviar Correo de Recuperacion"));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(RecuperarActivity.this, "No hay ningun usuario con ese correo.", Toast.LENGTH_SHORT).show();
        }
        envioCodigo();
    }

    private void envioCodigo() {
        Intent intent = new Intent(RecuperarActivity.this, CodigoActivity.class);
        intent.putExtra(U_ID, this.id);
        startActivity(intent);
    }
}