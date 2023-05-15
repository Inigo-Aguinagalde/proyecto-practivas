package com.example.proyect.ui.main;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LlamadaAPIEscribir extends Activity {
    private AppDataBase db;
    private Context context;

    public LlamadaAPIEscribir(AppDataBase db, Context context) {
        this.db = db;
        this.context = context;
    }

    public void escribir() {
        Thread thread = new Thread(() -> {
            List<Lista> lista = checkMongo();

            JSONArray values = new JSONArray();

            for (Lista a : lista) {
                JSONObject  row = new JSONObject ();
                try {
                    row.put("roomID",a.getId());
                    row.put("seccion",a.getSeccion());
                    row.put("nombre",a.getNombre());
                    row.put("cantidad",a.getCantidad());
                    row.put("unidad",a.getUnidad());
                    row.put("notas",a.getNotas());
                    values.put(row);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }

            JSONObject payload = new JSONObject();
            System.out.println(values);
            try {
                payload.put("values", values);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }

            RequestQueue queue = Volley.newRequestQueue(context);
            StringRequest request = new StringRequest(Request.Method.POST, "https://lista.inigoaginagalde.repl.co/write_data",
                    response -> {
                        Toast.makeText(context, "Los datos se han guardado con éxito", Toast.LENGTH_SHORT).show();
                        System.out.println("estoy dentro del response");
                    },
                    error -> {
                        System.out.println(error);
                        Toast.makeText(context, "Los datos no se han podido guardar, por favor inténtalo más tarde", Toast.LENGTH_SHORT).show();
                        System.out.println("estoy dentro del error");
                    }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return payload.toString().getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
            };

            queue.add(request);
        });

        thread.start();
    }

    private List<Lista> checkMongo() {
        List<Lista> lista = new ArrayList<>();

        // Perform your database operations here
        lista = db.ListaDao().getitemsLista();
        List<String> id = consultarIDs();
        for (Lista element : lista) {
            for (int i = 0; i < id.size(); i++) {
                String item = id.get(i);
                if(!element.getId().equalsIgnoreCase(item)){
                   lista.add(element);
                   break;
                }
            }

        }
        return lista;
    }

    private List<String> consultarIDs() {
        List<String> ids = new ArrayList<String>();
        RequestQueue queue = Volley.newRequestQueue(context);
        String url = "https://lista.inigoaginagalde.repl.co/read_data";

        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    // Process the JSON response here
                    if (response != null && response.length() != 0) {
                        JSONArray responseArray = response;

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject jsonObject;
                            try {
                                jsonObject = responseArray.getJSONObject(i);
                                ids.add(jsonObject.getString("roomID"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                },
                error -> {
                    // Handle error response
                    System.out.println(error.networkResponse);
                    System.out.println(error.getCause());
                });

        queue.add(stringRequest);
        return ids;
    }
}