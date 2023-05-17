package com.example.proyect.db;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyect.MainActivity;

import java.util.List;

public class ViewModelSentenciasDao extends ViewModel {


    /**
     *
     * Clase necesaria para utilizar los liveData y los observer para monitorizar los cambios en la base de datos
     * y hacer las sentencias SQLS correspondientes
     *
     */


    public ViewModelSentenciasDao(){

    }

    public LiveData<List<Lista>> getAllitems() {
        return MainActivity.db.ListaDao().getAll();
    };



}