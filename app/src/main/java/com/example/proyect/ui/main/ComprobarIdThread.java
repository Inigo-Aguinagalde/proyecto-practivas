package com.example.proyect.ui.main;

import android.app.Activity;
import android.os.Handler;
import android.os.HandlerThread;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class ComprobarIdThread  extends Activity implements Runnable  {

    private AppDataBase db;
    public ComprobarIdThread(AppDataBase db) {
        this.db = db;
    }

    @Override
    public void run() {

        checkMongo();

    }

    private List<Lista> checkMongo() {
        List<Lista> lista = null;
        HandlerThread ht = new HandlerThread("ht");
        ht.start();
        Handler handler = new Handler(ht.getLooper());
        handler.post(() -> {
            List<Lista> room = db.ListaDao().getitemsLista();
            List<String> id = consultarIDs();
            for (Lista element: room) {


            };
        });

        return lista;
    }


    private List<String> consultarIDs(){

        RequestQueue queue = Volley.newRequestQueue(this);

        String url ="https://lista.inigoaginagalde.repl.co/get_ids";

        List<String> ids = null;
        JsonArrayRequest stringRequest = new JsonArrayRequest(Request.Method.GET, url, null, response -> {

            HandlerThread ht = new HandlerThread("ht");
            ht.start();
            Handler handler = new Handler(ht.getLooper());


            handler.post(() -> {

                if (response!= null) {

                    if(response.length()!=0) {
                        JSONArray responseArray = response;

                        for (int i = 0; i < responseArray.length(); i++) {
                            JSONObject jsonObject = null;
                            try {
                                jsonObject  = responseArray.getJSONObject(i);
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }


                            try {
                                ids.add(jsonObject.getString("_id"));
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    }
                }
            });



        }, error -> {
            // TODO: Handle error

            System.out.println(error.networkResponse);
            System.out.println(error.getCause());

        });

        queue.add(stringRequest);
        return ids;
    }

}
