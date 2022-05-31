package com.example.intercambiando_ando;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


public class ProductoAdapter extends RecyclerView.Adapter<ProductoHolder> {

    private ArrayList<Producto> data;
    private Context context;
    private OnClickListener onClickListener;

    public ProductoAdapter(Context context, OnClickListener onClickListener){
        this.context = context;
        data = new ArrayList<>();
        this.onClickListener = onClickListener;
    }

    public ProductoAdapter(MainActivity context, MainActivity mainActivity) {
    }

    public void add(Producto producto){
        data.add(producto);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.productos, parent, false);
        return new ProductoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductoHolder holder, int position) {

        Producto producto = data.get(position);

        holder.getTvArticulo().setText(producto.getProducto());
        holder.getTvPropietario().setText(producto.getUser());
        holder.getTvArticuloEstado().setText(producto.getEstado());
        holder.getTvArticuloCategoria().setText(producto.getCategoria());
        holder.getTvArticuloEstatus().setText(producto.getEstatus());

        long Creacion = producto.getCreacion();
        holder.getTvArticuloCreacion().setText("Publicado: "+ new Timestamp(Creacion));

        Picasso.get().load(producto.getFoto()).into(holder.getIvArticulo());
        holder.getIvArticulo();

    }

    @Override
    public int getItemCount() {

        return data.size();

    }

    public void limpiar() {

        data.clear();

    }

    public Producto leer(int position) {

        return data.get(position);

    }

    public interface OnClickListener {
        void onClick(int position);
    }

}
