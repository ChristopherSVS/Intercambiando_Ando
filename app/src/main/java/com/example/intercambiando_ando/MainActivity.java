package com.example.intercambiando_ando;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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

import com.android.volley.RequestQueue;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    private ImageView ivLogo, ivUsuario;
    private TextView tvUsuario, tvUserCorreo;
    private SearchView svBuscar;
    private Spinner sEstados, sCategorias, sEstatus;
    private RecyclerView rvProductos;
    private int id = 0;

    public static final String DireccionDataImagen= "gs://intercambiandoando-c1a19.appspot.com";

    public static final String DireccionIP= "192.168.100.8";

    private RequestQueue requestQueue;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("https://intercambiandoando-c1a19-default-rtdb.firebaseio.com/");

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
        Intent intent = new Intent(MainActivity.this, NuevaSesionActivity.class);
        startActivity(intent);
    }

    private void registrarNuevoUsuario() {
        Intent intent = new Intent(MainActivity.this, NuevaSesionActivity.class);
        startActivity(intent);
    }

    private void mostrarPerfil() {
        Intent intent = new Intent(MainActivity.this, NuevaSesionActivity.class);
        startActivity(intent);
    }

    private void agregarProducto() {
        Intent intent = new Intent(MainActivity.this, NuevaSesionActivity.class);
        startActivity(intent);
    }

    private void cerrarSesion() {
        Intent intent = new Intent(MainActivity.this, NuevaSesionActivity.class);
        startActivity(intent);
    }

}