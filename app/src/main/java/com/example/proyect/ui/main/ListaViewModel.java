package com.example.proyect.ui.main;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;

import java.util.List;

public class ListaViewModel extends AndroidViewModel {

    private LiveData<List<Lista>> mListas;
    private AppDataBase mDatabase;

    public ListaViewModel(@NonNull Application application) {
        super(application);
        mDatabase = AppDataBase.getInstance(application.getApplicationContext());
        mListas = mDatabase.listaDao().getAllListas();
    }

    public LiveData<List<Lista>> getAllListas() {
        return mListas;
    }
}
