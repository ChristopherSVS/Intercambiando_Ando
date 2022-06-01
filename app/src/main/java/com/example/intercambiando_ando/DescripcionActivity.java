package com.example.intercambiando_ando;

import static com.example.intercambiando_ando.NuevaSesionActivity.ID_CLAVE;
import static com.example.intercambiando_ando.NuevaSesionActivity.I_CLAVE;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DescripcionActivity extends AppCompatActivity {

    private ImageButton ibProducto;
    private TextView tvUserProducto, tvIDProducto;
    private EditText etNomProducto, etCatProducto;
    private Button bOfertas;
    private RadioGroup rgEstado, rgEstatus;
    private RadioButton rbNuevo, rbViejo, rbActivo, rbInactivo;

    public static final String P_PRODUCTO = "P_PRODUCTO";
    public static final String P_CODIGO = "P_CODIGO";
    public static final String P_USER = "P_USER";
    public static final String P_ESTADO = "P_ESTADO";
    public static final String P_CATEGORIA = "P_CATEGORIA";
    public static final String P_ESTATUS = "P_ESTATUS";
    public static final String P_CREACION = "P_CREACION";
    public static final String P_FOTO = "P_FOTO";

    public static final String U_ID = "U_ID";
    private static final int ACCION_SELECCION_IMAGEN = 1;

    FirebaseDatabase database = null;
    DatabaseReference myRef = null;
    private FirebaseStorage storage;

    private RequestQueue requestQueue;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descripcion);

        ibProducto = findViewById(R.id.ibProducto);

        tvUserProducto = findViewById(R.id.tvUserProduto);
        tvIDProducto = findViewById(R.id.tvIDProducto);

        etNomProducto = findViewById(R.id.etNomProducto);

        etCatProducto = findViewById(R.id.etCatProducto);

        rgEstado = findViewById(R.id.rgEstado);
        rgEstatus = findViewById(R.id.rgEstadus);

        rbNuevo = findViewById(R.id.rbNuevo);
        rbViejo = findViewById(R.id.rbViejo);
        rbActivo = findViewById(R.id.rbActivo);
        rbInactivo = findViewById(R.id.rbInactivo);

        bOfertas = findViewById(R.id.bOfertas);

        database = FirebaseDatabase.getInstance("https://intercambiandoando-c1a19-default-rtdb.firebaseio.com/");
        myRef = database.getReference("Imagen");
        storage = FirebaseStorage.getInstance();
        requestQueue = Volley.newRequestQueue(this);

        bOfertas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DescripcionActivity.this, OfertasActivity.class);
                startActivity(intent);
            }
        });

        ibProducto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent, ACCION_SELECCION_IMAGEN);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == ACCION_SELECCION_IMAGEN) {
                Uri uri = data.getData();

                StorageReference storageRef = storage.getReference().child("images/" + uri.getLastPathSegment() + "_" + System.currentTimeMillis());

                storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                GuardarFoto(uri.toString());
                            }
                        });

                    }
                });

            }
        }
    }

    private void GuardarFoto(String toString) {
        RequestQueue queue = Volley.newRequestQueue(this);

        Intent intent = getIntent();
        String iden = intent.getStringExtra(U_ID);
        String url = MainActivity.BASE_URL + "foto.php";

        Map<String, String> mapa = new HashMap<>();

        this.id = Integer.getInteger(iden);
        mapa.put("id",String.valueOf(id));
        mapa.put("imagen", toString);

        JSONObject parametros = new JSONObject(mapa);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarRespuestaGuardado(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DescripcionActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("DescripcionActivity", error.getMessage());
            }
        });
        queue.add(request);
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
                Log.e("DescripcionActivity", "Error de comunicacion: " + error.getMessage());
            }
        });
        requestQueue.add(request);
    }

    private void procesarLista(JSONArray response) {
        Intent intent = getIntent();
        if (intent != null) {
            tvUserProducto.setText(intent.getStringExtra(P_USER));
            etNomProducto.setText(intent.getStringExtra(P_PRODUCTO));
            String username = tvUserProducto.getText().toString().trim();
            String prop = etNomProducto.getText().toString().trim();
            if (response != null) {
                try {
                    for (int i = 0; i < response.length(); i++) {

                        JSONObject fila = response.getJSONObject(i);

                        int codigo = fila.getInt("codigo");
                        String product = fila.getString("producto");
                        String user = fila.getString("user");
                        String estado = fila.getString("estado");
                        String estatus = fila.getString("estatus");
                        String categoria = fila.getString("categoria");
                        String foto = fila.getString("foto");

                        Producto producto = new Producto();

                        producto.setCodigo(codigo);
                        producto.setProducto(product);
                        producto.setUser(user);
                        producto.setEstado(estado);
                        producto.setEstatus(estatus);
                        producto.setCategoria(categoria);
                        producto.setFoto(foto);

                        if (product.equals(prop) && user.equals(username)) {
                            tvIDProducto.setText("Id del Producto: " + codigo);
                            etCatProducto.setText(categoria);
                            if(estado.equals("Nuevo")){
                                rbNuevo.setChecked(true);
                            }else{
                                rbViejo.setChecked(true);
                            }
                            if(estatus.equals("Activo")){
                                rbActivo.setChecked(true);
                            }else{
                                rbInactivo.setChecked(true);
                            }
                            if (!foto.equals("")) {
                                Picasso.get().load(foto).into(ibProducto);
                            } else {
                                ibProducto.setImageResource(R.drawable.ic_launcher_foreground);
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
    public boolean onCreateOptionsMenu(Menu menu) {

        int id_layout = R.menu.menu_opciones_3;

        getMenuInflater().inflate(id_layout,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()){
            case R.id.menu_opc_editar:
                editarProducto();
                break;
            case R.id.menu_opc_eliminar:
                eliminarProducto();
                break;
            case R.id.menu_opc_ofer:
                eliminarOferta();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void editarProducto() {

        String producto = etNomProducto.getText().toString().trim();
        String categoria = etCatProducto.getText().toString().trim();

        String Estado = "";
        if (rbNuevo.isChecked()){
            Estado = "Nuevo";
        }else if(rbViejo.isChecked()){
            Estado = "Viejo";
        }
        String Estatus = "";
        if (rbActivo.isChecked()){
            Estatus = "Activo";
        }else if(rbInactivo.isChecked()){
            Estatus = "Inactivo";
        }

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = MainActivity.BASE_URL + "guardar.php";

        Map<String, String> mapa = new HashMap<>();
        if (id != 0) {
            mapa.put("id", id + "");
        }

        mapa.put("producto", producto);
        mapa.put("categoria", categoria);
        mapa.put("estatus", Estatus);
        mapa.put("estado", Estado);

        JSONObject parametros = new JSONObject(mapa);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarRespuestaGuardado(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(DescripcionActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("DescripcionActivity", error.getMessage());
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
                Toast.makeText(DescripcionActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("DescripcionActivity", error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void eliminarProducto() {

    }

    private void eliminarOferta() {
    }

}