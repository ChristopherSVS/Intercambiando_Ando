package com.example.intercambiando_ando;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class FotoActivity extends AppCompatActivity {

    private ImageView ivFoto;
    private FloatingActionButton fabSubir;
    private static final int ACCION_SELECCION_IMAGEN = 1;

    FirebaseDatabase database = null;
    DatabaseReference myRef = null;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foto);

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
                /*
                StorageReference storageRef = storage.getReference().child("images/" + uri.getLastPathSegment() + "_" + System.currentTimeMillis());

                storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {

                                Mensaje mensaje = new Mensaje();
                                mensaje.setNombre("User");
                                mensaje.setFechaHora(System.currentTimeMillis());
                                mensaje.setImagen(uri.toString());
                                myRef.push().setValue(mensaje);

                            }
                        });

                    }
                });
                */
            }
        }
    }
}