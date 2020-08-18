package com.example.onewaychat.item;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Coordinates {
    @PrimaryKey(autoGenerate = true)
    public long id;

    public double latitude;

    public double longitude;
}
