package com.example.onewaychat.ItemDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.onewaychat.item.Map;

@Dao
public interface MapDao {

    @Query("DELETE FROM map")
    void clearTable();
    @Insert
    void insert(Map map);
}
