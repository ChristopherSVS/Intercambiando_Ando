package com.example.intercambiando_ando;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.storage.FirebaseStorage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OfertasActivity extends AppCompatActivity implements ProductoAdapter.OnClickListener {

    private RecyclerView rvOfertas;
    private TextView tvOfertas;
    private RequestQueue requestQueue;

    public static final String P_PRODUCTO = "P_PRODUCTO";
    public static final String P_CODIGO = "P_CODIGO";
    public static final String P_USER = "P_USER";
    public static final String P_ESTADO = "P_ESTADO";
    public static final String P_CATEGORIA = "P_CATEGORIA";
    public static final String P_ESTATUS = "P_ESTATUS";
    public static final String P_CREACION = "P_CREACION";
    public static final String P_FOTO = "P_FOTO";
    public static final String U_ID = "U_ID";

    private int id = 0;
    FirebaseDatabase database = null;
    DatabaseReference myRef = null;

    private ProductoAdapter adapter;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ofertas);

        tvOfertas = findViewById(R.id.tvOfertas);

        rvOfertas = findViewById(R.id.rvOfertas);

        database = FirebaseDatabase.getInstance("https://intercambiandoando-c1a19-default-rtdb.firebaseio.com/");
        myRef = database.getReference("Imagen");
        storage = FirebaseStorage.getInstance();

        adapter = new ProductoAdapter(this, this);
        rvOfertas.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        rvOfertas.setLayoutManager(linearLayoutManager);
        requestQueue = Volley.newRequestQueue(this);

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
                rvOfertas.scrollToPosition(adapter.getItemCount() -1);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        consultarProductos();
    }

    @Override
    public void onClick(int position) {
        Producto producto = adapter.leer(position);
        Intent intent = new Intent(OfertasActivity.this,DescripcionActivity.class);

        intent.putExtra(P_PRODUCTO, producto.getProducto());
        intent.putExtra(P_CODIGO, producto.getCodigo());
        intent.putExtra(P_USER, producto.getUser());
        intent.putExtra(P_ESTADO, producto.getEstado());
        intent.putExtra(P_CATEGORIA, producto.getCategoria());
        intent.putExtra(P_ESTATUS, producto.getEstatus());
        intent.putExtra(P_CREACION, producto.getCreacion());
        intent.putExtra(P_FOTO, producto.getFoto());
        intent.putExtra(U_ID, id);

        startActivity(intent);

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
                Log.e("OfertasActivity", "Error de comunicacion: " + error.getMessage());
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
                    String usuario = fila.getString("username");
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

}