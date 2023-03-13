package com.example.proyect.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.proyect.db.Lista;
import com.example.proyect.db.ListaDao;

import java.util.List;

public class MainViewModel extends ViewModel {

    private ListaDao mRepository;
    private LiveData<List<Lista>> mAllListas;




}