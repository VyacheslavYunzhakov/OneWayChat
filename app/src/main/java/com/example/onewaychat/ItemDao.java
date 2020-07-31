package com.example.onewaychat;


import android.view.View;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemDao {
 //   @Query("SELECT item_view FROM (SELECT * FROM item ORDER BY time)")
//    List<View> getView();

 //   @Query("SELECT item_id FROM (SELECT * FROM item ORDER BY time)")
//    List<Integer> getIdOfXML();

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);
}