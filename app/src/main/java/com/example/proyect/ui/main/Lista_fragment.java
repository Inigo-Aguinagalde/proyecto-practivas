package com.example.proyect.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.proyect.MainActivity;
import com.example.proyect.R;
import com.example.proyect.db.Lista;
import com.example.proyect.db.ViewModelSentenciasDao;

import java.util.List;


public class Lista_fragment extends Fragment {
    private ListaAdapter mViewModel;
    private ListaAdapter mAdapter;
    private MainActivity main;
    public Lista_fragment(MainActivity main) {
        this.main=main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ListaAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.lista_compra);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        ViewModelSentenciasDao sentenciasVM = new ViewModelProvider(this).get(ViewModelSentenciasDao.class);

        Observer<List<Lista>> observerLecrturas = lecturas -> {

            mAdapter.setListas(lecturas);
        };

        sentenciasVM.getAllitems().observe(main,observerLecrturas);
        return view;
    }

}
