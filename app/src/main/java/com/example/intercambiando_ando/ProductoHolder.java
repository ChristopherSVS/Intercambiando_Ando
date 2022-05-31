package com.example.intercambiando_ando;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ProductoHolder extends RecyclerView.ViewHolder {

    private ImageView ivArticulo;
    private TextView tvArticulo, tvPropietario, tvArticuloEstado, tvArticuloCategoria, tvArticuloEstatus, tvArticuloCreacion;

    public ProductoHolder(@NonNull View itemView) {
        super(itemView);

        ivArticulo = itemView.findViewById(R.id.ivArticulo);

        tvArticulo = itemView.findViewById(R.id.tvArticulo);
        tvPropietario = itemView.findViewById(R.id.tvPropietario);
        tvArticuloEstado = itemView.findViewById(R.id.tvArticuloEstado);
        tvArticuloCategoria = itemView.findViewById(R.id.tvArticuloCategoria);
        tvArticuloEstatus = itemView.findViewById(R.id.tvArticuloEstatus);
        tvArticuloCreacion = itemView.findViewById(R.id.tvArticuloCreacion);

    }

    public ImageView getIvArticulo() {
        return ivArticulo;
    }

    public void setIvArticulo(ImageView ivArticulo) {
        this.ivArticulo = ivArticulo;
    }

    public TextView getTvArticulo() {
        return tvArticulo;
    }

    public void setTvArticulo(TextView tvArticulo) {
        this.tvArticulo = tvArticulo;
    }

    public TextView getTvPropietario() {
        return tvPropietario;
    }

    public void setTvPropietario(TextView tvPropietario) {
        this.tvPropietario = tvPropietario;
    }

    public TextView getTvArticuloEstado() {
        return tvArticuloEstado;
    }

    public void setTvArticuloEstado(TextView tvArticuloEstado) {
        this.tvArticuloEstado = tvArticuloEstado;
    }

    public TextView getTvArticuloCategoria() {
        return tvArticuloCategoria;
    }

    public void setTvArticuloCategoria(TextView tvArticuloCategoria) {
        this.tvArticuloCategoria = tvArticuloCategoria;
    }

    public TextView getTvArticuloEstatus() {
        return tvArticuloEstatus;
    }

    public void setTvArticuloEstatus(TextView tvArticuloEstatus) {
        this.tvArticuloEstatus = tvArticuloEstatus;
    }

    public TextView getTvArticuloCreacion() {
        return tvArticuloCreacion;
    }

    public void setTvArticuloCreacion(TextView tvArticuloCreacion) {
        this.tvArticuloCreacion = tvArticuloCreacion;
    }
}
