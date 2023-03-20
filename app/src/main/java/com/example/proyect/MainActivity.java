package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.app.Application;
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
import com.example.proyect.ui.main.Lista_fragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    public static AppDataBase db;
    private ArrayList<Lista> listaCompra =new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


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
                        db.clearAllTables();
                    } catch (JSONException e) {
                        throw new RuntimeException(e);
                    }

                    for (int i = 0; i < valuesArray.length(); i++) {
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
                            seccion = rowArray.getString(1).trim();
                            nombre = rowArray.getString(2).trim();
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

        queue.add(stringRequest);
        Lista_fragment listaFragment = new Lista_fragment(this,db);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, listaFragment);
        fragmentTransaction.commit();

    }

}