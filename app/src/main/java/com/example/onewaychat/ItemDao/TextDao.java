package com.example.onewaychat.ItemDao;

import android.net.Uri;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.onewaychat.item.Text;

import java.util.List;

@Dao
public interface TextDao {

    @Query("DELETE FROM text")
    void clearTable();

    @Query("SELECT xml_id FROM (SELECT * FROM text ORDER BY time)")
        // Flowable<List<Integer>> getXmlId();
    List<Integer> getXmlId();

    @Query("SELECT text FROM (SELECT * FROM text ORDER BY time)")
    @TypeConverters({UriConverters.class})
        // Flowable<List<Uri>> getText();
    List<Uri> getText();

    @Query("SELECT view_id FROM (SELECT * FROM text ORDER BY time)")
        // Flowable<List<Integer>> getViewId();
    List<Integer> getViewId();

    @Query("SELECT type FROM (SELECT * FROM text ORDER BY time)")
        // Flowable<List<Integer>> getItemId();
    List<String> getType();


    @Insert
    void insert(Text text);

    @Update
    void update(Text text);

    @Delete
    void delete(Text text);
}