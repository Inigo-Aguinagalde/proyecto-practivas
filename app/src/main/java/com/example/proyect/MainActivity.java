package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.widget.Button;


import org.json.JSONArray;
import org.json.JSONException;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;
import com.example.proyect.ui.main.ListaAdapter;
import com.example.proyect.ui.main.Lista_fragment;
import com.example.proyect.ui.main.MainFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    public static AppDataBase db;
    private ArrayList<Lista> listaCompra =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, MainFragment.newInstance())
                    .commitNow();
        }

        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "compra.db").fallbackToDestructiveMigration()
                .build();


        llamadaApi();

    }

    public void llamadaApi() {

        //Creo el request que se necesita para hacer la llamada a la API
        RequestQueue queue = Volley.newRequestQueue(this);
        //Creo un una variable del tipo String con la url necesaria para la llamada a la api
        String api_key = "AIzaSyCWdraDGr7Rh54gPpZ0mr-WwhrR71Yoqrc";
        String url ="https://sheets.googleapis.com/v4/spreadsheets/1DEKerqhUopnL3o7nYLNtuc_L414XZQ6y2dH3ap8X5NY/values/A1:Z?key="+api_key;


        JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET, url, null, response -> {

            HandlerThread ht = new HandlerThread("ht");
            ht.start();
            Handler handler = new Handler(ht.getLooper());


            handler.post(() -> {
                if (response != null) {
                    JSONArray valuesArray = null;
                    try {
                        valuesArray = response.getJSONArray("values");
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    for (int i = 1; i < valuesArray.length(); i++) {
                        JSONArray rowArray = null;
                        try {
                            rowArray = valuesArray.getJSONArray(i);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }

                        // Get the values from each column in the row
                        String id = null;
                        String seccion = null;
                        String nombre = null;
                        String cantidad = null;
                        try {
                            id = rowArray.getString(0);
                            seccion = rowArray.getString(1);
                            nombre = rowArray.getString(2);
                            cantidad = rowArray.getString(3);
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }


                        // Do something with the values (e.g. create a new object and add it to a list)
                        Lista item = new Lista();
                        int cant = Integer.parseInt(cantidad);
                        item.setId(id);
                        item.setSeccion(seccion);
                        item.setNombre(nombre);
                        item.setCantidad(cant);
                        System.out.println(seccion);
                        System.out.println(nombre);
                        System.out.println(cantidad);

                        listaCompra.add(item);
                        db.ListaDao().insertAll(item);
                    }
                }
            });



        }, error -> {
            // TODO: Handle error
            System.out.println("estoy en el error");
            System.out.println(error.networkResponse);
            System.out.println(error.getCause());



        });


        // Add the request to the RequestQueue.
        // System.out.println("Se intentar añadir a la lista");
        queue.add(stringRequest);
        ListaAdapter listaAdapter = new ListaAdapter();
        Fragment fragment = new Lista_fragment(this);

    }

}