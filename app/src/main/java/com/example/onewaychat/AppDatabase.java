package com.example.onewaychat;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class, Image.class, Text.class, Map.class}, version = 5)

public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract ImageDao imageDao();
    public abstract TextDao textDao();
    public abstract MapDao mapDao();
}