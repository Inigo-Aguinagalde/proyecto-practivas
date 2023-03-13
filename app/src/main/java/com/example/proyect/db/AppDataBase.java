package com.example.proyect.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Lista.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    public abstract ListaDao ListaDao();

}
