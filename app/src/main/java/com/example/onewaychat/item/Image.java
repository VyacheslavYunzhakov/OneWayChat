package com.example.onewaychat.item;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.onewaychat.ItemDao.UriConverters;

@Entity
public class Image {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String time;

    @TypeConverters({UriConverters.class})
    public  Uri imageUri;

    public String type;

    @ColumnInfo(name = "xml_id")
    public int idOfXML;

    @ColumnInfo(name = "image_id")
    public int idOfImage;
}
