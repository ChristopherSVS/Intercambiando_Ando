package com.example.intercambiando_ando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class PerfilActivity extends AppCompatActivity implements ProductoAdapter.OnClickListener {

    public static final String P_PRODUCTO = "P_PRODUCTO";
    public static final String P_CODIGO = "P_CODIGO";
    public static final String P_USER = "P_USER";
    public static final String P_ESTADO = "P_ESTADO";
    public static final String P_CATEGORIA = "P_CATEGORIA";
    public static final String P_ESTATUS = "P_ESTATUS";
    public static final String P_CREACION = "P_CREACION";
    public static final String P_FOTO = "P_FOTO";

    private ImageButton ibUsuario;
    private TextView tvUsuarioCreacion, tvCorreoPerfil, tvID;
    private EditText etUsername, etCat;
    private SearchView svHistorial;
    private Spinner sEst, sStatus;
    private RecyclerView rvHistorial;

    public static final String I_CLAVE = "I_CLAVE";
    public static final String ID_CLAVE = "ID_CLAVE";

    public static final String U_ID = "U_ID";
    public static final String U_USERNAME = "P_USERNAME";
    public static final String U_EMAIL = "P_USERNAME";
    public static final String U_IMAGEN = "P_IMAGEN";
    public static String NombreOriginal = "";

    private int id = 0;

    private RequestQueue requestQueue;

    FirebaseDatabase database = null;
    DatabaseReference myRef = null;

    private ProductoAdapter adapter;
    private FirebaseStorage storage;

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

        database = FirebaseDatabase.getInstance("https://intercambiandoando-c1a19-default-rtdb.firebaseio.com/");
        myRef = database.getReference("Imagen");
        storage = FirebaseStorage.getInstance();

        adapter = new ProductoAdapter(this, this);
        rvHistorial.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvHistorial.setLayoutManager(linearLayoutManager);
        requestQueue = Volley.newRequestQueue(this);

        ConfigIU();

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Producto producto = snapshot.getValue(Producto.class);
                adapter.add(producto);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                super.onChanged();
                rvHistorial.scrollToPosition(adapter.getItemCount() -1);
            }
        });

        ibUsuario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PerfilActivity.this, FotoActivity.class);
                intent.putExtra(U_ID, id);
                intent.putExtra(U_USERNAME, NombreOriginal);
                startActivity(intent);
            }
        });

    }

    private void ConfigIU() {
        String url = MainActivity.BASE_URL + "usuarios.php";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                mostrarUsuario(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("MainActivity", "Error de comunicacion: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }

    private void mostrarUsuario(JSONArray response) {
        Intent intent = getIntent();
        if (intent != null) {
            id = intent.getIntExtra(U_ID,0);
            if (response != null) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject fila = response.getJSONObject(i);

                        int iden = fila.getInt("id");
                        String username = fila.getString("username");
                        String email = fila.getString("email");
                        String imagen = fila.getString("imagen");
                        long fecha_creacion = fila.getLong("fecha_creacion");


                        if (iden == id) {
                            tvID.setText("ID del Usuario" + iden);
                            tvCorreoPerfil.setText(email);
                            etUsername.setText(username);
                            NombreOriginal = username;
                            tvUsuarioCreacion.setText("Fecha de Creacion del Usuario" + (int)fecha_creacion);
                            if (!imagen.equals("")) {
                                Picasso.get().load(imagen).into(ibUsuario);
                            } else {
                                ibUsuario.setImageResource(R.drawable.ic_launcher_foreground);
                            }
                            break;
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void onClick(int position) {
        Producto producto = adapter.leer(position);
        Intent intent = new Intent(PerfilActivity.this,DescripcionActivity.class);

        intent.putExtra(P_PRODUCTO, producto.getProducto());
        intent.putExtra(P_CODIGO, producto.getCodigo());
        intent.putExtra(P_USER, producto.getUser());
        intent.putExtra(P_ESTADO, producto.getEstado());
        intent.putExtra(P_CATEGORIA, producto.getCategoria());
        intent.putExtra(P_ESTATUS, producto.getEstatus());
        intent.putExtra(P_FOTO, producto.getFoto());
        intent.putExtra(U_ID, id);
        intent.putExtra(U_USERNAME, NombreOriginal);
        startActivity(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        consultarProductos();
    }

    private void consultarProductos() {
        String url = MainActivity.BASE_URL + "productos.php";

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

        Intent intent = getIntent();
        String user = intent.getStringExtra(U_USERNAME);

        if(response != null){
            try {
                adapter.limpiar();
                for (int i=0;i<response.length();i++){

                    JSONObject fila = response.getJSONObject(i);

                    int codigo = fila.getInt("codigo");
                    String product = fila.getString("producto");
                    String usuario = fila.getString("username");
                    String estado = fila.getString("estado");
                    String categoria = fila.getString("categoria");
                    String estatus = fila.getString("estatus");
                    String foto = fila.getString("foto");

                    Producto producto = new Producto();

                    producto.setCodigo(codigo);
                    producto.setProducto(product);
                    producto.setUser(usuario);
                    producto.setEstado(estado);
                    producto.setCategoria(categoria);
                    producto.setEstatus(estatus);
                    producto.setFoto(foto);

                    if (user == usuario) {
                        adapter.add(producto);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
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
                String usertag = etUsername.getText().toString().trim();
                if (!usertag.equals(NombreOriginal)) {
                    Intent intent = getIntent();
                    id = intent.getIntExtra(U_ID,id);
                    cambiarNombre();
                }
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
        String usertag = etUsername.getText().toString().trim();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MainActivity.BASE_URL + "username.php";

        Map<String, String> mapa = new HashMap<>();
        mapa.put("id", id + "");
        mapa.put("username", usertag);

        JSONObject parametros = new JSONObject(mapa);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarRespuestaGuardado(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("PerfilActivity", error.getMessage());
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
                Toast.makeText(PerfilActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("PerfilActivity", error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void cambiarContra() {
        Intent intent = new Intent(PerfilActivity.this, NuevaContraActivity.class);
        intent.putExtra(U_ID, id);
        startActivity(intent);
    }

    private void borrarPerfil() {
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = MainActivity.BASE_URL + "eliminar.php";
        String usertag = etUsername.getText().toString().trim();

        Map<String, String> mapa = new HashMap<>();
        mapa.put("username", usertag);

        JSONObject parametros = new JSONObject(mapa);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    if (response.getBoolean("ok")){
                        Intent intent = new Intent(PerfilActivity.this,MainActivity.class);
                        intent.putExtra(U_ID,0);
                        intent.putExtra(U_USERNAME,"");
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(PerfilActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("PerfilActivity", error.getMessage());
            }
        });
        queue.add(request);
    }
}