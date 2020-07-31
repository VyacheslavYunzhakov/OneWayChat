package com.example.onewaychat;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Image {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String time;

    @TypeConverters({UriConverters.class})
    public  Uri imageUri;

    @ColumnInfo(name = "xml_id")
    public int idOfXML;

    @ColumnInfo(name = "item_id")
    public int idOfItem;
}
