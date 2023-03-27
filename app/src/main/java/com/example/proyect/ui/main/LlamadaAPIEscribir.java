package com.example.proyect.ui.main;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.List;

public class LlamadaAPIEscribir {
    private AppDataBase db;
    private Context context;
    public LlamadaAPIEscribir(AppDataBase db, Context context) {
        this.db = db;
        this.context=context;

    }

    public void escribir(){

       HandlerThread ht = new HandlerThread("ht");
       ht.start();
       Handler handler = new Handler(ht.getLooper());
        RequestQueue queue = Volley.newRequestQueue(context);
        handler.post(() -> {
            JSONArray values = new JSONArray();
            List<Lista> lista = db.ListaDao().getitemsLista();
            for (Lista a: lista
                 ) {
                System.out.println(a.getNombre());
                values.put(new JSONArray(Arrays.asList(a.getId(), a.getSeccion(), a.getNombre(), a.getCantidad(),a.getUnidad(),a.getNotas())));
            }
            String api_key = "AIzaSyCWdraDGr7Rh54gPpZ0mr-WwhrR71Yoqrc";
            String url = "https://sheets.googleapis.com/v4/spreadsheets/1DEKerqhUopnL3o7nYLNtuc_L414XZQ6y2dH3ap8X5NY/values/A1:Z?valueInputOption=RAW&key="+api_key;

            JSONObject payload = new JSONObject();
            try {

                payload.put("majorDimension", "ROWS");
                payload.put("values", values);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }



            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, payload,
                    response -> {
                        System.out.println("exito");
                    },
                    error -> {
                        // Handle error response
                    });

            queue.add(request);
        });
    }
}
