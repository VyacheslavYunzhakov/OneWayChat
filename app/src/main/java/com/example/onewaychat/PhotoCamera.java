package com.example.onewaychat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import static com.example.onewaychat.ChatActivity.linearLayoutInScrollView;
//import static com.example.onewaychat.ChatActivity.cameraIntent;
import static android.app.Activity.RESULT_OK;

public class PhotoCamera extends AppCompatActivity {
    private static final int CAMERA_REQUEST = 0;
    private ImageView cameraImageView;
    Context context;
    LayoutInflater ltInflater;

    public void takeAPhoto () {
        this.context = ChatActivity.context;
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA_REQUEST);
        ltInflater = LayoutInflater.from(context);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
            // Фотка сделана, извлекаем картинку
            Bitmap thumbnailBitmap = (Bitmap) data.getExtras().get("data");
            cameraImageView.setImageBitmap(thumbnailBitmap);
            View cameraView = ltInflater.inflate(R.layout.cameraview, linearLayoutInScrollView, false);
            ImageView cameraImage = cameraView.findViewById(R.id.cameraView);
            cameraImage = cameraImageView;
            linearLayoutInScrollView.addView(cameraView);
        }
    }
}
