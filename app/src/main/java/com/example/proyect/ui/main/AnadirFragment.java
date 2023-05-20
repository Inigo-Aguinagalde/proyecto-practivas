package com.example.proyect.ui.main;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.HandlerThread;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.proyect.R;
import com.example.proyect.db.AppDataBase;
import com.example.proyect.db.Lista;

import org.json.JSONArray;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class AnadirFragment extends Fragment {

    private AppDataBase db;

    public AnadirFragment(AppDataBase db) {
        this.db= db;
    }

    public AnadirFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    public void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.add_item_fragment, container, false);

        // Retrieve the Button widget from the inflated view
        Button button = view.findViewById(R.id.Añadir);
        EditText producto = view.findViewById(R.id.addProduc);
        EditText nota = view.findViewById(R.id.addNota);



        InputFilter filterNota = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // Prevent new line characters
                for (int i = start; i < end; i++) {
                    if (source.charAt(i) == '\n') {
                        nota.clearFocus();
                        hideKeyboard(nota);

                    }
                }
                return null;  // No changes needed
            }
        };
        InputFilter filterProducto = new InputFilter() {
            public CharSequence filter(CharSequence source, int start, int end,
                                       Spanned dest, int dstart, int dend) {
                // Prevent new line characters
                for (int i = start; i < end; i++) {
                    if (source.charAt(i) == '\n') {
                        producto.clearFocus();
                        hideKeyboard(producto);


                    }
                }
                return null;  // No changes needed
            }
        };
        producto.setFilters(new InputFilter[]{filterProducto});
        nota.setFilters(new InputFilter[]{filterNota});
        Drawable originalDrawable = producto.getBackground();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean error = false;
                EditText  producto = view.findViewById(R.id.addProduc);
                Spinner seccion = view.findViewById(R.id.dropdownSec);
                EditText cantidad= view.findViewById(R.id.addCant);
                Spinner unidad = view.findViewById(R.id.dropdownUnit);
                EditText  nota = view.findViewById(R.id.addNota);


                String productoText = producto.getText().toString().trim();
                String seccionText = seccion.getSelectedItem().toString().trim();
                String cantidadText = cantidad.getText().toString();
                String unidadText = unidad.getSelectedItem().toString().trim();
                String notaText = nota.getText().toString().trim();
                int cant = 0;

                // Check if the fields are empty and change border color to red if they are
                if (productoText.isEmpty()) {
                    producto.setBackgroundResource(R.drawable.edit_text_red_border);
                    error = true;
                }else{
                    producto.setBackground(originalDrawable);
                }

                if (seccionText.equalsIgnoreCase("seccion")) {
                    seccion.setBackgroundResource(R.drawable.edit_text_red_border);
                    error = true;
                }else{
                    seccion.setBackground(originalDrawable);
                }

                if (cantidadText.isEmpty()) {
                    cantidad.setBackgroundResource(R.drawable.edit_text_red_border);
                    error = true;
                } else{
                    cant = Integer.valueOf(cantidadText);
                    cantidad.setBackground(originalDrawable);
                }

                if (unidadText.equals("Unidad")) {
                    unidad.setBackgroundResource(R.drawable.edit_text_red_border);
                    error = true;
                }else{
                    unidad.setBackground(originalDrawable);
                }



                if(!error){

                    HandlerThread ht = new HandlerThread("ht");
                    ht.start();
                    Handler handler = new Handler(ht.getLooper());
                    int cantidadInt = cant;

                    handler.post(() -> {
                    Lista item = new Lista();
                    item.setNombre(productoText);
                    item.setSeccion(seccionText);
                    item.setCantidad(cantidadInt);
                    item.setUnidad(unidadText);
                    item.setNotas(notaText);
                    String id = UUID.randomUUID().toString();
                    item.setId(id);
                    item.setRoomID(id);
                    db.ListaDao().insertAll(item);

                            });
                    producto.setText("");
                    seccion.setSelection(0);
                    cantidad.setText("");
                    unidad.setSelection(0);
                    nota.setText("");

                    Toast.makeText(getContext(), "Producto añadido", Toast.LENGTH_SHORT).show();
                    

                }

            }
        });

        return view;
    }



}
