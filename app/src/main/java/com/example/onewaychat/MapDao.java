package com.example.onewaychat;

import androidx.appcompat.graphics.drawable.DrawerArrowDrawable;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

@Dao
public interface MapDao {

    @Query("DELETE FROM map")
    void clearTable();
    @Insert
    void insert(Map map);
}
