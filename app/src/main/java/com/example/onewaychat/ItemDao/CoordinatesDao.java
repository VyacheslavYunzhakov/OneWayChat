package com.example.onewaychat.ItemDao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.onewaychat.item.Coordinates;

@Dao
public interface CoordinatesDao {

    @Query("DELETE FROM coordinates")
    void clearTable();

    @Query("SELECT longitude FROM coordinates")
    double getLongitude();

    @Query("SELECT latitude FROM coordinates")
    double getLatitude();

    @Insert
    void insert(Coordinates coordinates);
}
