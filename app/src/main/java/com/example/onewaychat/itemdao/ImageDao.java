package com.example.onewaychat.itemdao;

import android.net.Uri;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.onewaychat.item.Image;

import java.util.List;

@Dao
public interface ImageDao {

    @Query("SELECT xml_id FROM (SELECT * FROM image ORDER BY time)")
   // Flowable<List<Integer>> getXmlId();
    List<Integer> getXmlId();

    @Query("SELECT imageUri FROM (SELECT * FROM image ORDER BY time)")
    @TypeConverters({UriConverters.class})
   // Flowable<List<Uri>> getImageUri();
    List<Uri> getImageUri();
    @Query("SELECT image_id FROM (SELECT * FROM image ORDER BY time)")
   // Flowable<List<Integer>> getItemId();
    List<Integer> getItemId();

    @Query("SELECT type FROM (SELECT * FROM image ORDER BY time)")
        // Flowable<List<Integer>> getItemId();
    List<String> getType();

    @Query("DELETE FROM image")
    void clearTable();

    @Insert
    void insert(Image image);

    @Update
    void update(Image image);

    @Delete
    void delete(Image image);
}
