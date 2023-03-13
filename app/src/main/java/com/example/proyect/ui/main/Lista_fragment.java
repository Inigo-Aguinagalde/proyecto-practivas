package com.example.proyect.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyect.MainActivity;
import com.example.proyect.R;
import com.example.proyect.db.Lista;
import com.example.proyect.db.ViewModelSentenciasDao;

import java.util.List;


public class Lista_fragment extends Fragment {

    private ListaViewModel listaAdapter;
    private HandlerThread ht = new HandlerThread("ht");
    private MainActivity main;
    private RecyclerView rv;

    public Lista_fragment() {

    }

    public Lista_fragment(MainActivity main, ListaViewModel listaAdapter) {

        this.main=main;

        this.listaAdapter = listaAdapter;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Instancion el ViewModel para poder utilizarlo con los observes
        ViewModelSentenciasDao sentencias = new ViewModelProvider(this).get(ViewModelSentenciasDao.class);
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        rv = (RecyclerView) view.findViewById(R.id.lista_compra);
        rv.setLayoutManager(new LinearLayoutManager(main,LinearLayoutManager.VERTICAL,false));

        rv.setAdapter(listaAdapter);


        Observer<List<Lista>> observerName = compra -> {
            if(compra!=null){
                listaAdapter.setCompra(compra);
            }else{
                System.out.println("La lista esta vacia");
            }
        };

        sentencias.getAllitems().observe(main,observerName);

        return view;
    }




}