package com.example.intercambiando_ando;

import static com.example.intercambiando_ando.NuevaSesionActivity.ID_CLAVE;
import static com.example.intercambiando_ando.NuevaSesionActivity.I_CLAVE;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FotoActivity extends AppCompatActivity {

    public static final String U_ID = "U_ID";

    private ImageView ivFoto;
    private FloatingActionButton fabSubir;
    private static final int ACCION_SELECCION_IMAGEN = 1;
    private RequestQueue requestQueue;
    private int id = 0;

    FirebaseDatabase database = null;
    DatabaseReference myRef = null;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

        requestQueue = Volley.newRequestQueue(this);

        database = FirebaseDatabase.getInstance("https://intercambiandoando-c1a19-default-rtdb.firebaseio.com/");
        myRef = database.getReference("Imagen");
        storage = FirebaseStorage.getInstance();

        ivFoto = findViewById(R.id.ivFoto);

        fabSubir = findViewById(R.id.fabSubir);

        fabSubir.setOnClickListener(new View.OnClickListener() {
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
        String iden = intent.getStringExtra(ID_CLAVE);

        String url = MainActivity.BASE_URL + "Usuarios.php";

        Map<String, String> mapa = new HashMap<>();

        this.id = Integer.getInteger(iden);
        mapa.put("id",String.valueOf(id));
        mapa.put("imagen", toString);

        JSONObject parametros = new JSONObject(mapa);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, parametros, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                procesarRespuestaGuardado(response);
                Intent intent = new Intent(FotoActivity.this, MainActivity.class);
                intent.putExtra(U_ID, id);
                startActivity(intent);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(FotoActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("FotoActivity", error.getMessage());
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
                Toast.makeText(FotoActivity.this, "Ha sucedido un error", Toast.LENGTH_SHORT).show();
                Log.e("FotoActivity", error);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}