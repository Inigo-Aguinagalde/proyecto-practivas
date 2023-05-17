package com.example.proyect.db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity
public class Lista {

    /**
     *
     * Clase que crea la tabla de las items y representa que tipo de dato es cada elemento
     *
     */

    @PrimaryKey()
    @NonNull
    @ColumnInfo(name = "id")
    public String id;

    public Lista() {
        // Generate UUID for the id field
        this.id = UUID.randomUUID().toString();
    }

    @ColumnInfo(name = "nombre")
    @NonNull
    public String nombre;


    @ColumnInfo(name = "seccion")
    @NonNull
    public String seccion;

    @ColumnInfo(name = "cantidad")
    @NonNull
    public float cantidad;

    @ColumnInfo(name = "notas")
    @NonNull
    public String notas;

    @ColumnInfo(name = "roomID")
    public String roomID;



    @ColumnInfo(name = "unidad")
    @NonNull
    public String unidad;
    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
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

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public float getCantidad() {
        return cantidad;
    }

    public void setCantidad(float cantidad) {
        this.cantidad = cantidad;
    }

    public String getUnidad() {
        return unidad;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}