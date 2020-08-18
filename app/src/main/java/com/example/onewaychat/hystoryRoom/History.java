package com.example.onewaychat.hystoryRoom;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.onewaychat.chat.ChatActivity;
import com.example.onewaychat.ItemDao.ImageDao;
import com.example.onewaychat.R;
import com.example.onewaychat.database.App;
import com.example.onewaychat.database.AppDatabase;
import com.example.onewaychat.item.Coordinates;
import com.example.onewaychat.item.Image;
import com.example.onewaychat.item.Map;
import com.example.onewaychat.item.Text;

import java.text.DateFormat;
import java.util.Calendar;

import static com.example.onewaychat.chat.ChatActivity.linearLayoutInScrollView;
import static com.example.onewaychat.chat.ChatActivity.mapView;
import static com.example.onewaychat.SendActions.SendLocation.latitude;
import static com.example.onewaychat.SendActions.SendLocation.longitude;

public class History {
    Context context;
    AppDatabase database = App.getInstance().getDatabase();
    ImageDao imageDao = database.imageDao();

    public void loadHistory(String text_or_uri, int viewId, int xmlId, String type) {
            this.context = ChatActivity.context;
            View view = null;
            LayoutInflater ltInflater = LayoutInflater.from(context);
            if (!type.equals("location")) {
                view = ltInflater.inflate(xmlId, linearLayoutInScrollView, false);
            }
            if (type.equals("image") || type.equals("camera")) {
                ImageView cameraImageView = view.findViewById(viewId);
                Uri imageUri = Uri.parse(text_or_uri);
                cameraImageView.setImageURI(imageUri);
                linearLayoutInScrollView.addView(view);
            }
            if (type.equals("text")){
                TextView textMessage = view.findViewById(R.id.textMessage);
                textMessage.setText(text_or_uri);
                linearLayoutInScrollView.addView(view);
            }
            if (type.equals("location")){
                latitude = database.coordinatesDao().getLatitude();
                longitude = database.coordinatesDao().getLongitude();
                linearLayoutInScrollView.addView(mapView);
            }
        }


    public void saveHistory (Uri imageUri, int viewId, int xmlId, String type){
        Image image = new Image();
        image.time =  DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        image.imageUri = imageUri;
        image.idOfImage = viewId;
        image.idOfXML = xmlId;
        image.type = type;
        database.imageDao().insert(image);
    }
    public void saveHistory (String text, int viewId, int xmlId, String type){
        Text text1 = new Text();
        text1.time =  DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        text1.text = text;
        text1.idOfView = viewId;
        text1.idOfXML = xmlId;
        text1.type = type;
        database.textDao().insert(text1);
    }
    public void saveHistory(String type){
        Map map = new Map();
        database.mapDao().clearTable();
        map.time = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        map.idOfView = 0;
        map.idOfXML = 0;
        map.text_or_uri = "";
        map.type = type;
        database.mapDao().insert(map);
        Coordinates coordinates = new Coordinates();
        database.coordinatesDao().clearTable();
        coordinates.latitude = latitude;
        coordinates.longitude = longitude;
        database.coordinatesDao().insert(coordinates);

    }

}
