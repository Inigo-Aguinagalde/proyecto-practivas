package com.example.proyect.ui.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyect.R;
import com.example.proyect.db.Lista;

import java.util.List;

public class ListaFragment extends Fragment {

    private ListaAdapter mAdapter;
    private ListaViewModel mViewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ListaViewModel.class);
        mAdapter = new ListaAdapter();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.lista_compra);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mAdapter);

        mViewModel.getAllListas().observe(getViewLifecycleOwner(), new Observer<List<Lista>>() {
            @Override
            public void onChanged(List<Lista> listas) {
                if (listas.size() > 0) {
                    mAdapter.setListas(listas);
                } else {
                    // Handle empty state
                }
            }
        });

        return view;
    }
}
