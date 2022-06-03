package com.example.intercambiando_ando;

import android.content.Context;

import java.util.ArrayList;

public class UsuariosAdapter {
    private ArrayList<Usuarios> data;
    private Context context;

    public UsuariosAdapter(Context context) {
        this.context = context;
        data = new ArrayList<>();
    }

    public void add(Usuarios usuarios){
        data.add( usuarios );
    }



}
