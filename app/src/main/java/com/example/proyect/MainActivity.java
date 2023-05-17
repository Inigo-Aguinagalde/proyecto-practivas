package com.example.proyect;

import androidx.appcompat.app.AppCompatActivity;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.room.Room;

import android.app.AlertDialog;
import android.app.Application;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;
import com.example.proyect.ui.main.AnadirFragment;
import com.example.proyect.ui.main.Lista_fragment;
import com.example.proyect.ui.main.LlamadaAPIEscribir;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private Button btn;
    public static AppDataBase db;
    private ArrayList<Lista> listaCompra =new ArrayList<>();
    private Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        context = getApplicationContext();
        db = Room.databaseBuilder(getApplicationContext(), AppDataBase.class, "compra.db").fallbackToDestructiveMigration()
                .build();
     

        llamadaApi();

    }


    public void llamadaApi() {


        RequestQueue queue = Volley.newRequestQueue(this);


        String url ="https://lista.inigoaginagalde.repl.co/read_data";

        List<String> listaIds = new ArrayList<>();
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


                            String seccion = null;
                            String producto = null;
                            float cantidad;
                            String unidad = null;
                            String nota = null;
                            String roomID = null;

                            try {
                                roomID = jsonObject.getString("roomID");
                                seccion = jsonObject.getString("seccion");
                                producto = jsonObject.getString("nombre");
                                cantidad = jsonObject.getInt("cantidad");
                                unidad = jsonObject.getString("unidad");
                                nota = jsonObject.getString("notas");
                            } catch (JSONException e) {
                                throw new RuntimeException(e);
                            }



                            DecimalFormat df = new DecimalFormat("#.##");
                            String formattedValue = df.format(cantidad);
                            float result = Float.parseFloat(formattedValue);

                            // Do something with the values (e.g. create a new object and add it to a list)
                            Lista item = new Lista();
                            item.setRoomID(roomID);
                            item.setSeccion(seccion.toLowerCase());
                            item.setNombre(producto.toLowerCase());
                            item.setNotas(nota);
                            item.setCantidad(result);
                            item.setUnidad(unidad);



                            listaCompra.add(item);

                        }
                        checkMongo(listaCompra);
                    }
                }
            });



        }, error -> {
            // TODO: Handle error

            System.out.println(error.networkResponse);
            System.out.println(error.getCause());

        });

        queue.add(stringRequest);
        Lista_fragment listaFragment = new Lista_fragment(this, db);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, listaFragment).commit();


    }
    private boolean mBackPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (!mBackPressedOnce) {
            Lista_fragment listaFragment = new Lista_fragment(this,db);
            getSupportFragmentManager().beginTransaction().replace(R.id.container, listaFragment).commit();
            Toast.makeText(this, "Press back again to minimize the app", Toast.LENGTH_SHORT).show();
            mBackPressedOnce = true;
            LlamadaAPIEscribir write = new LlamadaAPIEscribir(db,context);
            write.escribir();
        } else {
            // Call the default behavior to minimize the app
            mBackPressedOnce = false;
            new AlertDialog.Builder(this)
                    .setTitle("Exit app")
                    .setMessage("Are you sure you want to exit the app?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // Close the app
                            finish();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();
        }
    }



    private void checkMongo(ArrayList<Lista> listaCompra) {
        List<Lista> lista = new ArrayList<>();

        // Perform your database operations here
        lista = db.ListaDao().getitemsLista();


        boolean sizeZero = false;
        if(lista.size()==0){
            sizeZero =true;
        }
        for (Lista element : listaCompra) {
            if(sizeZero){
                lista.add(element);
            }else{
                boolean esta = false;
                for (int i = 0; i < lista.size(); i++) {
                    Lista item = lista.get(i);
                    if(element.getId().equalsIgnoreCase(item.getRoomID())) {
                        esta = true;
                        break;
                    }

                }
                if(!esta){
                    lista.add(element);
                }
            }

        }

    }

}