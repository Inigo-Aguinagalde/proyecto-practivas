package com.example.proyect.ui.main;

import android.os.Handler;
import android.os.HandlerThread;

import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyect.db.Lista;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private ListaAdapter mAdapter;

    public SwipeToDeleteCallback(ListaAdapter adapter) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        mAdapter = adapter;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // Don't handle move events
        return false;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getBindingAdapterPosition();

        mAdapter.deleteItem(position);
    }


}