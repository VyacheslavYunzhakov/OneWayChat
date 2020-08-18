package com.example.onewaychat.item;

import android.net.Uri;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

@Entity
public class Text {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String time;

    public String text;

    public String type;

    @ColumnInfo(name = "xml_id")
    public int idOfXML;

    @ColumnInfo(name = "view_id")
    public int idOfView;
}
