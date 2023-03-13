package com.example.proyect.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Lista {

    /**
     *
     * Clase que crea la tabla de las items y representa que tipo de dato es cada elemento
     *
     */

    @PrimaryKey
    @NonNull
    public String id;

    @ColumnInfo(name = "nombre")
    public String nombre;


    @ColumnInfo(name = "seccion")
    public String seccion;

    @ColumnInfo(name = "cantidad")
    public int cantidad;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getSeccion() {
        return seccion;
    }

    public void setSeccion(String seccion) {
        this.seccion = seccion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}