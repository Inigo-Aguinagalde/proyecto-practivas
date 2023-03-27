package com.example.proyect.ui.main;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.proyect.MainActivity;
import com.example.proyect.R;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;
import com.example.proyect.db.ViewModelSentenciasDao;

import java.util.List;


public class Lista_fragment extends Fragment {
    private ListaAdapter mViewModel;
    private ListaAdapter mAdapter;
    private MainActivity main;


    private AppDataBase db;
    public Lista_fragment(MainActivity main, AppDataBase db) {
        this.main=main;
        this.db=db;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAdapter = new ListaAdapter(db);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.lista_compra);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);
        ViewModelSentenciasDao sentenciasVM = new ViewModelProvider(this).get(ViewModelSentenciasDao.class);
        SwipeToDeleteCallback swipeToDeleteCallback = new SwipeToDeleteCallback(mAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeToDeleteCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        Observer<List<Lista>> observerLecrturas = lista -> {

            mAdapter.setListas(lista);
        };

        sentenciasVM.getAllitems().observe(main,observerLecrturas);
        Button button = view.findViewById(R.id.Añadir);
        button.setOnClickListener(v -> {
            AnadirFragment newFragment = new AnadirFragment(db);
            requireActivity().getSupportFragmentManager().beginTransaction().replace(R.id.container, newFragment).commit();

        });
        return view;
    }

}
