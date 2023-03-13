package com.example.proyect.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;
@Dao
public interface ListaDao {


    @Query("SELECT * FROM Lista order by seccion")
    LiveData<List<Lista>> getAll();

    @Insert(onConflict =  OnConflictStrategy.IGNORE)
    void insertAll(Lista... messages);

    @Query("SELECT * FROM Lista WHERE nombre =:name")
    Lista selectByName(String name);

    @Delete
    void delete(Lista message);

    @Query("select * from Lista")
    List<Lista>getitemsLecturas();

//    @Query("UPDATE Lista set selecionada = 'true' where id =:id")
//    void setTrue(String id);
//
//    @Query("UPDATE items SET selecionada = 'false' WHERE id = :id")
//    void setFalse(String id);



}