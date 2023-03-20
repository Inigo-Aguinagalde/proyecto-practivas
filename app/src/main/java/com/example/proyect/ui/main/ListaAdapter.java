package com.example.proyect.ui.main;


import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;


import com.example.proyect.R;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;


import java.util.ArrayList;
import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {
    private List<Lista> mListas = new ArrayList<>();
    private AppDataBase db;
    public ListaAdapter(AppDataBase db){
        this.db=db;
    }
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

    public void deleteItem(int position) {


        HandlerThread ht = new HandlerThread("ht");
        ht.start();
        Handler handler = new Handler(ht.getLooper());
        Lista item = mListas.get(position);

        handler.post(() -> {db.ListaDao().delete(item);});
        mListas.remove(position);
        notifyItemRemoved(position);
    }


    static class ListaViewHolder extends RecyclerView.ViewHolder {
        int red = Color.parseColor("#FB9999");
        int green = Color.parseColor("#E0F7C0");
        int blue = Color.parseColor("#C0E8F7");

        private TextView mNombreTextView;
        private TextView mSeccionTextView;
        private TextView mCantidadTextView;

        private CardView item;

        public ListaViewHolder(@NonNull View itemView) {
            super(itemView);
            mNombreTextView = itemView.findViewById(R.id.nombre);
            mSeccionTextView = itemView.findViewById(R.id.seccion);
            mCantidadTextView = itemView.findViewById(R.id.cantidad);
            item = itemView.findViewById(R.id.card);
        }

        public void bind(Lista lista) {
            if (item != null) {
                switch (lista.getSeccion().trim()) {
                    case "carniceria":
                        item.setCardBackgroundColor(red);
                        break;
                    case "pescaderia":
                        item.setCardBackgroundColor(blue);
                        break;
                    case "fruteria":
                        item.setCardBackgroundColor(green);
                        break;
                }
            }
            mNombreTextView.setText(lista.getNombre().trim());
            mSeccionTextView.setText(lista.getSeccion().trim());
            mCantidadTextView.setText(String.valueOf(lista.getCantidad()));
        }

    }
}