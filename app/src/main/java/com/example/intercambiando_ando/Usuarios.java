package com.example.intercambiando_ando;

public class Usuarios {

    private int id;
    private String username, password, email;
    private long Fecha_creacion;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public long getFecha_creacion() {
        return Fecha_creacion;
    }

    public void setFecha_creacion(long fecha_creacion) {
        Fecha_creacion = fecha_creacion;
    }

}
