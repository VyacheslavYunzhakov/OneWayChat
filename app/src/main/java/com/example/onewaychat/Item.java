package com.example.onewaychat;


import android.view.View;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Item {
  @PrimaryKey(autoGenerate = true)
  public long id;

    String time;// = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());

 //  @ColumnInfo(name = "item_view")
 //  View view;

   @ColumnInfo(name = "item_id")
    int idOfXML;

}