package com.example.onewaychat.chat;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.example.onewaychat.R;
import com.example.onewaychat.SendActions.SendImage;
import com.example.onewaychat.SendActions.SendLocation;
import com.example.onewaychat.SendActions.SendMessage;
import com.example.onewaychat.SendActions.SendPhoto;
import com.example.onewaychat.hystoryRoom.LoadHistoryFromDatabase;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.ArrayList;

import rx.Observable;
import rx.functions.Action1;

public class ChatActivity extends AppCompatActivity {

    public static Context context, context1;

    public static LinearLayout linearLayoutInScrollView;
    private static final int CAMERA_REQUEST = 0;
    private final int Pick_image = 1;

    RelativeLayout mainRelativeLayout;

    LayoutInflater ltInflater;
    public static ArrayList buttonNames = new ArrayList();
    public static Observable observable;
    public static Action1<String> action;

    public ScrollView scrollView;

    public static View mapView;

    SendPhoto sendPhoto = new SendPhoto();
    SendImage sendImage = new SendImage();
    SendLocation sendLocation = new SendLocation();
    LoadHistoryFromDatabase loadHistoryFromDatabase = new LoadHistoryFromDatabase();
    ChangeButtons changeButtons = new ChangeButtons();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        scrollView = findViewById(R.id.scrollView);
        linearLayoutInScrollView =findViewById(R.id.linearLayoutInScrollView);

        context = this;

        mainRelativeLayout = findViewById(R.id.mainRelativeLayout);

        changeButtons.setContext(this);
        changeButtons.addAddButton(mainRelativeLayout);
        ltInflater = getLayoutInflater();
        mapView = ltInflater.inflate(R.layout.sharelocation, linearLayoutInScrollView, false);

        observable = Observable.from(buttonNames);

        action = new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals(Integer.toString(R.drawable.ic_baseline_photo_camera_24))){
                    try {
                        sendPhoto.setContext(getContext());
                        sendPhoto.takeAPhoto ();
                        startActivityForResult(sendPhoto.cameraIntent, CAMERA_REQUEST);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if  (s.equals(Integer.toString(R.drawable.ic_baseline_collections_24))){
                    try {
                        sendImage.setContext(getContext());
                        sendImage.putImage();
                        startActivityForResult(sendImage.photoPickerIntent, Pick_image);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if  (s.equals(Integer.toString(R.drawable.ic_baseline_near_me_24))){
                    sendLocation.setContext(getContext());
                    sendLocation.showLocation(mapView);
                    scrollDialogDown();
                    addButton();

                }
                if (s.equals(Integer.toString(R.drawable.ic_baseline_textsms_24))) {
                    RelativeLayout mainRelativeLayout = findViewById(R.id.mainRelativeLayout);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.sendMessage(mainRelativeLayout);
                }

                if (s.equals("Scroll it")){
                    scrollDialogDown();
                }
            }


        };

        loadHistoryFromDatabase.loadHistoryFromDatabase();
        scrollDialogDown();


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            sendPhoto.sendPhotoToChat();
            addButton();
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_CANCELED) {
            addButton();
        }
        if(requestCode == Pick_image && resultCode == RESULT_OK){
            sendImage.sendImageToChat(data);
            addButton();
        }
        if (requestCode == Pick_image && resultCode == RESULT_CANCELED) {
            addButton();
        }
        scrollDialogDown();
    }

    public void addButton(){
        RelativeLayout mainRelativeLayout = findViewById(R.id.mainRelativeLayout);
        FloatingActionButton addButton = new FloatingActionButton(this);
        ChangeButtons changeButtons = new ChangeButtons();
        ChangeButtons.clickCounter++;
        //changeButtons.addAddButton(addButton, mainRelativeLayout);
        changeButtons.setContext(this);
        changeButtons.addAddButton(mainRelativeLayout);
    }

    public void scrollDialogDown() {
        this.context1 = context;
        scrollView.post(new Runnable() {

            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }

    public Context getContext() {
        return this;
    }
}

