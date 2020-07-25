package com.example.onewaychat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rx.Observable;
import rx.Observer;
import rx.functions.Action1;

public class ChatActivity extends AppCompatActivity {

    public static Context context;
    private Uri photoURI;

    //  public static Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    public static LinearLayout linearLayoutInScrollView;
    private static final int CAMERA_REQUEST = 0;
    private ImageView imageFromCamera;
    private ImageView cameraImageView;
    LayoutInflater ltInflater;
    public static ArrayList buttonNames = new ArrayList();
    public static Observable observable;
    public static Action1<String> action;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        FloatingActionButton addButton = new FloatingActionButton(this);
        FloatingActionButton messageButton = new FloatingActionButton(this);
        RelativeLayout mainRelativeLayout = findViewById(R.id.mainRelativeLayout);
        linearLayoutInScrollView =findViewById(R.id.linearLayoutInScrollView);
        imageFromCamera = findViewById(R.id.cameraView);
        context = this;
        ChangeButtons changeButtons = new ChangeButtons();
        changeButtons.addAddButton(addButton,mainRelativeLayout);

        observable = Observable.from(buttonNames);

        action = new Action1<String>() {
            @Override
            public void call(String s) {
                if (s == "Camera"){
                    Log.d("callLogs", "onNext: " + s);
                    try {
                        takeAPhoto ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }
        };



    }
    public void takeAPhoto () throws IOException {

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),
                "test.jpg");
        File photoFile = null;
        photoFile = createImageFile();
        photoURI = FileProvider.getUriForFile(this,
                "com.example.onewaychat.fileprovider",
                photoFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        ltInflater = getLayoutInflater();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            View cameraView = ltInflater.inflate(R.layout.cameraview, linearLayoutInScrollView, false);
            ImageView cameraImageView = cameraView.findViewById(R.id.cameraView);
            cameraImageView.setImageURI(photoURI);
            linearLayoutInScrollView.addView(cameraView);
            ChangeButtons.clickCounter++;
            RelativeLayout mainRelativeLayout = findViewById(R.id.mainRelativeLayout);
            FloatingActionButton addButton = new FloatingActionButton(this);
            ChangeButtons changeButtons = new ChangeButtons();
            changeButtons.addAddButton(addButton, mainRelativeLayout);
        }
        //else {Log.d("myLogs", "fu");}
    }
    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        String mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    }

