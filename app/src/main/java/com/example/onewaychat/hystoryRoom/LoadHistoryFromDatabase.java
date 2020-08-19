package com.example.onewaychat.hystoryRoom;

import android.util.Log;

import com.example.onewaychat.database.App;
import com.example.onewaychat.database.AppDatabase;

import java.util.ArrayList;
import java.util.List;

public class LoadHistoryFromDatabase {

    AppDatabase database = App.getInstance().getDatabase();
    List<Integer> itemXmlIdList = new ArrayList<>();
    List<String> itemTextOrUriList = new ArrayList<>();
    List<String> itemTypeList = new ArrayList<>();
    List<Integer> itemViewIdList = new ArrayList<>();
    History history = new History();
    public void loadHistoryFromDatabase() {
       // database.itemDao().clearTable();
        itemTextOrUriList = database.itemDao().getTextOrUri();
        itemViewIdList = database.itemDao().getViewId();
        itemXmlIdList = database.itemDao().getXmlId();
        itemTypeList = database.itemDao().getType();

        Log.d("loadLogs", "" + itemXmlIdList.size());
        for (int i = 0; i < itemXmlIdList.size(); i++) {
            Log.d("myLogs", " " + itemTypeList.get(i));
            history.loadHistory(itemTextOrUriList.get(i), itemViewIdList.get(i), itemXmlIdList.get(i), itemTypeList.get(i));
        }
    }
}
