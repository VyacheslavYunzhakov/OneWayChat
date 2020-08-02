package com.example.onewaychat;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Item.class, Image.class, Text.class}, version = 4)

public abstract class AppDatabase extends RoomDatabase {
    public abstract ItemDao itemDao();
    public abstract ImageDao imageDao();
    public abstract TextDao textDao();
}