package com.example.intercambiando_ando;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class IniciarSesionActivity extends AppCompatActivity {

    public static final String U_USERNAME = "U_USERNAME";
    public static final String U_EMAIL = "U_EMAIL";
    public static final String U_IMAGEN = "U_IMAGEN";
    public static final String U_ID = "U_ID";

    private TextView tvErrorUser, tvErrorContrasena;
    private EditText etCorreo2, etContra;
    private Button bRecuperar, bIniciar;
    private CheckBox cbVisibilidad;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        requestQueue = Volley.newRequestQueue(this);

        etCorreo2 = findViewById(R.id.etCorreo2);
        etContra = findViewById(R.id.etContra);

        tvErrorUser = findViewById(R.id.tvErrorUser);
        tvErrorContrasena = findViewById(R.id.tvErrorContrasena);

        bRecuperar = findViewById(R.id.bRecuperar);
        bIniciar = findViewById(R.id.bIniciar);

        cbVisibilidad = findViewById(R.id.cbVisibilidad);

        cbVisibilidad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        bIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                verificaDatos();
            }
        });

        bRecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IniciarSesionActivity.this, RecuperarActivity.class);
                startActivity(intent);

            }
        });

    }

    private void verificaDatos() {

        String url = MainActivity.BASE_URL + "usuarios.php";
        tvErrorUser.setVisibility(View.INVISIBLE);
        tvErrorContrasena.setVisibility(View.INVISIBLE);
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                procesarLista(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("IniciarSesionActivity", "Error de comunicacion: " + error.getMessage());
            }
        });
        requestQueue.add(request);

    }

    private void procesarLista(JSONArray response) {

        String correo = etCorreo2.getText().toString().trim();
        String codigo = etContra.getText().toString().trim();

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
                    usuarios.setImagen(Image);

                    if (correo.equals(email)) {
                        if (codigo.equals(password)) {
                            Intent intent = new Intent(IniciarSesionActivity.this, MainActivity.class);
                            intent.putExtra(U_ID, usuarios.getId());
                            intent.putExtra(U_USERNAME, usuarios.getUsername());
                            intent.putExtra(U_EMAIL, usuarios.getEmail());
                            intent.putExtra(U_IMAGEN, usuarios.getImagen());
                            startActivity(intent);
                            break;
                        }else{
                            tvErrorContrasena.setVisibility(View.VISIBLE);
                        }
                    }else{
                        tvErrorUser.setVisibility(View.VISIBLE);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

}