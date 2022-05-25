package com.example.intercambiando_ando;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

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

    public static final String DireccionDataImagen= "gs://intercambiandoando-c1a19.appspot.com";

    public static final String DireccionIP= "192.168.100.8";

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

    }



}