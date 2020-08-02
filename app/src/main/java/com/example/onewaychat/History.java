package com.example.onewaychat;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.DateFormat;
import java.util.Calendar;

import static com.example.onewaychat.ChatActivity.linearLayoutInScrollView;

public class History {
    Context context;
    AppDatabase database = App.getInstance().getDatabase();
    ImageDao imageDao = database.imageDao();

    public void loadHistory(String text_or_uri, int viewId, int xmlId, String type) {
            this.context = ChatActivity.context;
            LayoutInflater ltInflater = LayoutInflater.from(context);
            View view = ltInflater.inflate(xmlId, linearLayoutInScrollView, false);
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

}
