package com.example.onewaychat.itemdao;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverters;
import androidx.room.Update;

import com.example.onewaychat.item.Item;

import java.util.List;

@Dao
public interface ItemDao {
 //   @Query("SELECT item_view FROM (SELECT * FROM item ORDER BY time)")
//    List<View> getView();

 //   @Query("SELECT item_id FROM (SELECT * FROM item ORDER BY time)")
//    List<Integer> getIdOfXML();

    @Query("DELETE FROM item")
    void clearTable();
    @Query("SELECT id, time, imageUri as text_or_uri, type, xml_id, image_id as view_id FROM image"  +
            " UNION ALL " +
            "SELECT id, time, text as text_or_uri, type, xml_id, view_id as view_id FROM text "+
            " UNION ALL " +
            "SELECT id, time, text_or_uri, type, xml_id, view_id as view_id FROM map "+
            "ORDER BY time")
    List<Item> getAll();

    @Query("SELECT xml_id FROM ("+
            "SELECT id, time, imageUri as text_or_uri, type, xml_id, image_id as view_id FROM image"  +
            " UNION ALL " +
            "SELECT id, time, text as text_or_uri, type, xml_id, view_id as view_id FROM text "+
            " UNION ALL " +
            "SELECT id, time, text_or_uri, type, xml_id, view_id as view_id FROM map "+
            "ORDER BY time)")
    List<Integer> getXmlId();

    @Query("SELECT text_or_uri FROM ("+
            "SELECT id, time, imageUri as text_or_uri, type, xml_id, image_id as view_id FROM image"  +
            " UNION ALL " +
            "SELECT id, time, text as text_or_uri, type, xml_id, view_id as view_id FROM text "+
            " UNION ALL " +
            "SELECT id, time, text_or_uri, type, xml_id, view_id as view_id FROM map "+
            "ORDER BY time)")
    @TypeConverters({UriConverters.class})
    List<String> getTextOrUri();

    @Query("SELECT view_id FROM ("+
            "SELECT id, time, imageUri as text_or_uri, type, xml_id, image_id as view_id FROM image"  +
            " UNION ALL " +
            "SELECT id, time, text as text_or_uri, type, xml_id, view_id as view_id FROM text "+
            " UNION ALL " +
            "SELECT id, time, text_or_uri, type, xml_id, view_id as view_id FROM map "+
            "ORDER BY time)")
    List<Integer> getViewId();

    @Query("SELECT type FROM ("+
            "SELECT id, time, imageUri as text_or_uri, type, xml_id, image_id as view_id FROM image"  +
            " UNION ALL " +
            "SELECT id, time, text as text_or_uri, type, xml_id, view_id as view_id FROM text "+
            " UNION ALL " +
            "SELECT id, time, text_or_uri, type, xml_id, view_id as view_id FROM map "+
            "ORDER BY time)")
    List<String> getType();

    @Insert
    void insert(Item item);

    @Update
    void update(Item item);

    @Delete
    void delete(Item item);
}