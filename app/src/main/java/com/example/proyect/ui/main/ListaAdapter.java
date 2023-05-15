package com.example.proyect.ui.main;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyect.R;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListaAdapter extends RecyclerView.Adapter<ListaAdapter.ListaViewHolder> {
    private List<Lista> mListas = new ArrayList<>();
    private AppDataBase db;
    private Context context;


    public ListaAdapter(AppDataBase db, Context context){
        this.db=db;
        this.context = context;
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
        eliminarElemento(item);
        handler.post(() -> {db.ListaDao().delete(item);});
        mListas.remove(position);
        notifyItemRemoved(position);
    }

    private void eliminarElemento(Lista item){
        HandlerThread ht = new HandlerThread("ht");
        ht.start();
        Handler handler = new Handler(ht.getLooper());
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://lista.inigoaginagalde.repl.co/delete_data";
        url = url + "?id=" + item.getId();
        StringRequest request = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Handle successful response
                        // Do something with the response from the Replit
                        System.out.println("estoy dentro del response");
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error response
                        System.out.println("estoy dentro del error");
                        // Display an error message or retry the request
                    }
                });


        queue.add(request);

    }


    static class ListaViewHolder extends RecyclerView.ViewHolder {
        int red = Color.parseColor("#FB9999");
        int green = Color.parseColor("#E0F7C0");
        int blue = Color.parseColor("#C0E8F7");

        int defaul = Color.parseColor("#fcfeff");

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

                switch (lista.getSeccion().trim().toLowerCase()) {
                    case "carniceria":
                        item.setCardBackgroundColor(red);
                        break;
                    case "pescaderia":
                        item.setCardBackgroundColor(blue);
                        break;
                    case "fruteria":
                        item.setCardBackgroundColor(green);
                        break;
                    default:
                        item.setCardBackgroundColor(defaul);
                }
            }
            if(lista.getNotas() != null && !lista.getNotas().isEmpty()){
                mNombreTextView.setText(lista.getNombre().trim()+ "*");

            }else{
                mNombreTextView.setText(lista.getNombre().trim());

            }
            CardView cardView = itemView.findViewById(R.id.card); // Replace with your card view's id

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (lista.getNotas() != null && !lista.getNotas().isEmpty()) {
                        // Show modal or popup with notas message
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Notas")
                                .setMessage(lista.getNotas().trim())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.dismiss();
                                    }
                                });
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }
                }
            });


            mSeccionTextView.setText(lista.getSeccion().trim());
            mCantidadTextView.setText(String.valueOf(lista.getCantidad())+"\n"+lista.getUnidad());
        }

    }
}