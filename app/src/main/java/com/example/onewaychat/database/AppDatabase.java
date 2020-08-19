package com.example.onewaychat.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.example.onewaychat.itemdao.CoordinatesDao;
import com.example.onewaychat.itemdao.ImageDao;
import com.example.onewaychat.itemdao.ItemDao;
import com.example.onewaychat.itemdao.MapDao;
import com.example.onewaychat.itemdao.TextDao;
import com.example.onewaychat.item.Coordinates;
import com.example.onewaychat.item.Image;
import com.example.onewaychat.item.Item;
import com.example.onewaychat.item.Map;
import com.example.onewaychat.item.Text;

@Database(entities = {Item.class, Image.class, Text.class, Map.class, Coordinates.class}, version = 6)

public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract ImageDao imageDao();
    public abstract TextDao textDao();
    public abstract MapDao mapDao();
    public abstract CoordinatesDao coordinatesDao();
}