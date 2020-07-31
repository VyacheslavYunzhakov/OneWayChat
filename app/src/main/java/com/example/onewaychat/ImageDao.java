package com.example.onewaychat;

import android.net.Uri;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface ImageDao {
    @Query("SELECT xml_id FROM (SELECT * FROM image ORDER BY time)")
   // Flowable<List<Integer>> getXmlId();
    List<Integer> getXmlId();

    @Query("SELECT imageUri FROM (SELECT * FROM image ORDER BY time)")
    @TypeConverters({UriConverters.class})
   // Flowable<List<Uri>> getImageUri();
    List<Uri> getImageUri();
    @Query("SELECT item_id FROM (SELECT * FROM image ORDER BY time)")
   // Flowable<List<Integer>> getItemId();
    List<Integer> getItemId();

    @Insert
    void insert(Image image);

    @Update
    void update(Image image);

    @Delete
    void delete(Image image);
}