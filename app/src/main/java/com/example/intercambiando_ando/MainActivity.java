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
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    public static final String P_PRODUCTO = "P_PRODUCTO";
    public static final String P_CODIGO = "P_CODIGO";
    public static final String P_USER = "P_USER";
    public static final String P_ESTADO = "P_ESTADO";
    public static final String P_CATEGORIA = "P_CATEGORIA";
    public static final String P_ESTATUS = "P_ESTATUS";
    public static final String P_CREACION = "P_CREACION";
    public static final String P_FOTO = "P_FOTO";


    private ImageView ivLogo, ivUsuario;
    private TextView tvUsuario, tvUserCorreo;
    private SearchView svBuscar;
    private Spinner sEstados, sCategorias, sEstatus;
    private RecyclerView rvProductos;
    private int id = 0;

    public static final String BASE_URL = "http://192.168.100.8/intercambiando/";

    private RequestQueue requestQueue;

    FirebaseDatabase database = null;
    DatabaseReference myRef = null;

    private ProductoAdapter adapter;
    private FirebaseStorage storage;

    // Write a message to the database
    /*
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("");
    */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tvUsuario = findViewById(R.id.tvUsuario);
        tvUserCorreo = findViewById(R.id.tvUserCorreo);

        ivLogo = findViewById(R.id.ivLogo);
        ivUsuario = findViewById(R.id.ivUsuario);

        svBuscar = findViewById(R.id.svBuscar);

        sEstados = findViewById(R.id.sEstados);
        sCategorias = findViewById(R.id.sCategorias);
        sEstatus = findViewById(R.id.sEstatus);

        rvProductos = findViewById(R.id.rvProductos);

        database = FirebaseDatabase.getInstance("https://intercambiandoando-c1a19-default-rtdb.firebaseio.com/");
        myRef = database.getReference("Imagen");
        storage = FirebaseStorage.getInstance();

        adapter = new ProductoAdapter(this, this);
        rvProductos.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvProductos.setLayoutManager(linearLayoutManager);
        requestQueue = Volley.newRequestQueue(this);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Producto producto = snapshot.getValue(Producto.class);;
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
                rvProductos.scrollToPosition(adapter.getItemCount() -1);
            }
        });

        /*
        myRef.setValue("");

        // Read from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
        */
    }



    @Override
    protected void onStart() {

        super.onStart();
        consultarProductos();

    }

    public void onClick(int position) {
        Producto producto = adapter.leer(position);
        Intent intent = new Intent(MainActivity.this,DescripcionActivity.class);

        intent.putExtra(P_PRODUCTO, producto.getProducto());
        intent.putExtra(P_CODIGO, producto.getCodigo());
        intent.putExtra(P_USER, producto.getUser());
        intent.putExtra(P_ESTADO, producto.getEstado());
        intent.putExtra(P_CATEGORIA, producto.getCategoria());
        intent.putExtra(P_ESTATUS, producto.getEstatus());
        intent.putExtra(P_CREACION, producto.getCreacion());
        intent.putExtra(P_FOTO, producto.getFoto());

        startActivity(intent);
    }

    private void consultarProductos() {
        String url = BASE_URL + "Productos.php";

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
        if(response != null){
            try {
                adapter.limpiar();
                for (int i=0;i<response.length();i++){

                    JSONObject fila = response.getJSONObject(i);

                    int codigo = fila.getInt("codigo");
                    String product = fila.getString("producto");
                    String usuario = fila.getString("precio");
                    String estado = fila.getString("estado");
                    String categoria = fila.getString("categoria");
                    String estatus = fila.getString("estatus");
                    String foto = fila.getString("foto");
                    long creacion = fila.getLong("creacion");

                    Producto producto = new Producto();

                    producto.setCodigo(codigo);
                    producto.setProducto(product);
                    producto.setUser(usuario);
                    producto.setEstado(estado);
                    producto.setCategoria(categoria);
                    producto.setEstatus(estatus);
                    producto.setCreacion(creacion);
                    producto.setFoto(foto);

                    adapter.add(producto);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        int id_layout = R.menu.menu_opciones_1;

        if (id!=0){
            id_layout = R.menu.menu_opciones_2;
        }

        getMenuInflater().inflate(id_layout,menu);
        
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_opc_iniciar:
                iniciarSesion();
                break;
            case R.id.menu_opc_registrar:
                registrarNuevoUsuario();
                break;
            case R.id.menu_opc_perfil:
                mostrarPerfil();
                break;
            case R.id.menu_opc_nuevo:
                agregarProducto();
                break;
            case R.id.menu_opc_cerrar:
                cerrarSesion();
                break;
        }
        
        return super.onOptionsItemSelected(item);
    }

    private void iniciarSesion() {
        Intent intent = new Intent(MainActivity.this, IniciarSesionActivity.class);
        startActivity(intent);
    }

    private void registrarNuevoUsuario() {
        Intent intent = new Intent(MainActivity.this, NuevaSesionActivity.class);
        startActivity(intent);
    }

    private void mostrarPerfil() {
        Intent intent = new Intent(MainActivity.this, PerfilActivity.class);
        startActivity(intent);
    }

    private void agregarProducto() {
        Intent intent = new Intent(MainActivity.this, DescripcionActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        id = 0;
        tvUsuario.setText("Bienvenido usuario anonimo");
        tvUserCorreo.setText("Usuario no registrado");
    }

}