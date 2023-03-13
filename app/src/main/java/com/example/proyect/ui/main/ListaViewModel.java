package com.example.proyect.ui.main;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;


import com.example.proyect.R;
import com.example.proyect.db.Lista;


import java.util.List;

public class ListaViewModel extends RecyclerView.Adapter<ListaViewModel.ViewHolder> {

    private List<Lista> lista;






    public ListaViewModel() {

    }

    public void setCompra(List<Lista> lista){
        this.lista=lista;
        notifyDataSetChanged();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.fragment_item_compra,parent,false);
        return new ViewHolder(view);

    }


    /**
     *
     *
     * En este metedo lo que hace es comprobar si la baliza esta seleccionada en la base de datos y represetar el switch en funcion
     * de si esta activada la baliza o no
     * @param holder
     * @param position
     */

    @Override
    public void onBindViewHolder(ListaViewModel.ViewHolder holder, int position) {
        int posicion=position;
        holder.mNombreView.setText(lista.get(position).nombre);
        holder.mCantidadView.setText(lista.get(position).cantidad);
        holder.mseccionView.setText(lista.get(position).seccion);

    }


    @Override
    public int getItemViewType(int position) {
        return position;
    }


    @Override
    public int getItemCount() {
        if (lista == null) {
            return 0;
        } else
            return lista.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final TextView mNombreView;
        public final TextView mseccionView;
        public final TextView mCantidadView;


        public ViewHolder(View view) {
            super(view);
            mNombreView = view.findViewById(R.id.nombre);
            mseccionView = view.findViewById(R.id.seccion);
            mCantidadView = view.findViewById(R.id.cantidad);



        }



    }

}
