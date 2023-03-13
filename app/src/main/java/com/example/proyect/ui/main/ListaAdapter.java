package com.example.proyect.ui.main;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


import com.example.proyect.R;
import com.example.proyect.db.Lista;


import java.util.ArrayList;
import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {
    private List<Lista> mListas = new ArrayList<>();

    @NonNull
    @Override
    public ListaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ListaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListaViewHolder holder, int position) {
        Lista lista = mListas.get(position);
        holder.bind(lista);
    }

    @Override
    public int getItemCount() {
        return mListas.size();
    }

    public void setListas(List<Lista> listas) {
        mListas = listas;
        notifyDataSetChanged();
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder {

        private TextView mNombreTextView;
        private TextView mSeccionTextView;
        private TextView mCantidadTextView;

        public ListaViewHolder(@NonNull View itemView) {
            super(itemView);
            mNombreTextView = itemView.findViewById(R.id.nombre);
            mSeccionTextView = itemView.findViewById(R.id.seccion);
            mCantidadTextView = itemView.findViewById(R.id.cantidad);
        }

        public void bind(Lista lista) {
            mNombreTextView.setText(lista.getNombre());
            mSeccionTextView.setText(lista.getSeccion());
            mCantidadTextView.setText(String.valueOf(lista.getCantidad()));
        }

    }
}