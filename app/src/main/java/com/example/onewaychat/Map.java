package com.example.onewaychat;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Map {

    @PrimaryKey(autoGenerate = true)
    public long id;

    String time;

    public String text_or_uri;

    public String type;

    @ColumnInfo(name = "xml_id")
    public int idOfXML;

    @ColumnInfo(name = "view_id")
    public int idOfView;
}
