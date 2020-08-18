package com.example.onewaychat.database;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

public class App extends Application {

    public static App instance;

    private AppDatabase database;


    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(final SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE new_image (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "time TEXT," +
                    "imageUri TEXT," +
                    "type TEXT," +
                    "xml_id INTEGER NOT NULL DEFAULT 1," +
                    "image_id INTEGER NOT NULL DEFAULT 1)");
            database.execSQL("INSERT INTO new_image (id, time, imageUri, type, xml_id, image_id) " +
                    "SELECT id, time, imageUri, \"camera\", xml_id, item_id FROM image");
            database.execSQL("DROP TABLE image");
            database.execSQL("ALTER TABLE new_image RENAME TO image");
            //database.execSQL("ALTER TABLE image ADD COLUMN type STRING DEFAULT \"camera\"");
           // database.execSQL("ALTER TABLE image RENAME COLUMN item_id TO image_id");
        }
    };

    public static final Migration MIGRATION_2_3 = new Migration(2, 3) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE text (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "time TEXT," +
                    "text TEXT," +
                    "type TEXT," +
                    "xml_id INTEGER NOT NULL DEFAULT 1," +
                    "view_id INTEGER NOT NULL DEFAULT 1)");
        }
    };
    public static final Migration MIGRATION_3_4 = new Migration(3, 4) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("DROP TABLE item");
            database.execSQL("CREATE TABLE item (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "time TEXT," +
                    "text_or_uri TEXT," +
                    "type TEXT," +
                    "xml_id INTEGER NOT NULL DEFAULT 1," +
                    "view_id INTEGER NOT NULL DEFAULT 1)");
        }
    };
    public static final Migration MIGRATION_4_5 = new Migration(4, 5) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE map (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "time TEXT," +
                    "text_or_uri TEXT," +
                    "type TEXT," +
                    "xml_id INTEGER NOT NULL DEFAULT 1," +
                    "view_id INTEGER NOT NULL DEFAULT 1)");
        }
    };
    public static final Migration MIGRATION_5_6 = new Migration(5, 6) {

        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE coordinates (" +
                    "id INTEGER PRIMARY KEY NOT NULL," +
                    "latitude DOUBLE NOT NULL DEFAULT 1," +
                    "longitude DOUBLE NOT NULL DEFAULT 1)");
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                  .allowMainThreadQueries()
                  .build();
    }

    public static App getInstance() {
        return instance;
    }

    public AppDatabase getDatabase() {
        return database;
    }
}