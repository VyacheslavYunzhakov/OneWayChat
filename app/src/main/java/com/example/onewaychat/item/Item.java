package com.example.onewaychat.item;


import android.view.View;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public String time;

    public String text_or_uri;

    public String type;

    @ColumnInfo(name = "xml_id")
    public int idOfXML;

    @ColumnInfo(name = "view_id")
    public int idOfView;

}