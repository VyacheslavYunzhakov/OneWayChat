package com.example.onewaychat;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ChangeButtons extends AppCompatActivity {
    static int marginForAddButton = 40;
    static int additionalMargin = 180;
    public static int clickCounter = 0;


    public void addAddButton(final FloatingActionButton addButton, final RelativeLayout mainRelativeLayout) {

        final FloatingActionButton messageButton = new FloatingActionButton(ChatActivity.context);
        final FloatingActionButton cameraButton = new FloatingActionButton(ChatActivity.context);
        final FloatingActionButton imageButton = new FloatingActionButton(ChatActivity.context);
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        //relativeLayoutParams.addRule(RelativeLayout.END_OF, R.id.linearLayoutInScrollView);
        relativeLayoutParams.setMargins(marginForAddButton, marginForAddButton, marginForAddButton, marginForAddButton);
        addButton.setImageResource(R.drawable.ic_baseline_add_24);
        addButton.setSize(FloatingActionButton.SIZE_NORMAL);
        addButton.setLayoutParams(relativeLayoutParams);
        addButton.setId(R.id.addButton);

        mainRelativeLayout.addView(addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCounter % 2 == 0) {
                    addButton.setImageResource(R.drawable.ic_baseline_close_24);
                    addButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F76B1C")));
                    clickCounter++;
                    addMessageButton (messageButton, mainRelativeLayout, addButton, cameraButton, imageButton);
                    addCameraButton (cameraButton, mainRelativeLayout, addButton, messageButton, imageButton);
                    addImageButton (cameraButton, mainRelativeLayout, addButton, messageButton, imageButton);
                } else {
                    addButton.setImageResource(R.drawable.ic_baseline_add_24);
                    addButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                    clickCounter++;
                    Log.d("myLogs", "addMessageButton(\"remove\",mainRelativeLayout);");

                   // addMessageButton("remove",mainRelativeLayout);
                    mainRelativeLayout.removeView(messageButton);
                    mainRelativeLayout.removeView(cameraButton);
                    mainRelativeLayout.removeView(imageButton);
                }
            }
        });
    }
        public void addMessageButton(final FloatingActionButton messageButton, final RelativeLayout mainRelativeLayout,
                                            final FloatingActionButton addButton, final FloatingActionButton cameraButton,
                                     final FloatingActionButton imageButton){
            RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams
                    (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
            relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
            messageButton.setImageResource(R.drawable.ic_baseline_textsms_24);
            messageButton.setSize(FloatingActionButton.SIZE_NORMAL);
            relativeLayoutParams.setMargins(marginForAddButton, marginForAddButton, marginForAddButton,
                    marginForAddButton + additionalMargin);
            messageButton.setLayoutParams(relativeLayoutParams);
            mainRelativeLayout.addView(messageButton);
            messageButton.setId(R.id.messageButton);

            messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainRelativeLayout.removeView(messageButton);
                    mainRelativeLayout.removeView(cameraButton);
                    mainRelativeLayout.removeView(imageButton);
                    mainRelativeLayout.removeView(addButton);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.sendMessage(mainRelativeLayout);

                }
            });
        }

    public void addCameraButton(final FloatingActionButton cameraButton, final RelativeLayout mainRelativeLayout,
                                final FloatingActionButton addButton, final FloatingActionButton messageButton,
                                final FloatingActionButton imageButton){

        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        cameraButton.setImageResource(R.drawable.ic_baseline_photo_camera_24);
        cameraButton.setSize(FloatingActionButton.SIZE_NORMAL);
        relativeLayoutParams.setMargins(marginForAddButton, marginForAddButton, marginForAddButton,
                marginForAddButton + additionalMargin * 2);
        cameraButton.setLayoutParams(relativeLayoutParams);
        mainRelativeLayout.addView(cameraButton);
        cameraButton.setId(R.id.cameraButton);

        cameraButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mainRelativeLayout.removeView(cameraButton);
                mainRelativeLayout.removeView(messageButton);
                mainRelativeLayout.removeView(addButton);
                mainRelativeLayout.removeView(imageButton);
                if (ChatActivity.buttonNames.size() == 1){
                    ChatActivity.buttonNames.remove(0);
                }
                ChatActivity.buttonNames.add("Camera");
                ChatActivity.observable.subscribe(ChatActivity.action);
               // PhotoCamera photoCamera = new PhotoCamera();
               // photoCamera.takeAPhoto();

            }
        });
    }

    public void addImageButton(final FloatingActionButton cameraButton, final RelativeLayout mainRelativeLayout,
                                final FloatingActionButton addButton, final FloatingActionButton messageButton,
                               final FloatingActionButton imageButton){

        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        //relativeLayoutParams.addRule(RelativeLayout.START_OF, R.id.addButton);
        imageButton.setImageResource(R.drawable.ic_baseline_collections_24);
        imageButton.setSize(FloatingActionButton.SIZE_NORMAL);
        relativeLayoutParams.setMargins(marginForAddButton, marginForAddButton, marginForAddButton,
                marginForAddButton + additionalMargin * 3);
        imageButton.setLayoutParams(relativeLayoutParams);
        mainRelativeLayout.addView(imageButton);
        imageButton.setId(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mainRelativeLayout.removeView(cameraButton);
                mainRelativeLayout.removeView(messageButton);
                mainRelativeLayout.removeView(imageButton);
                mainRelativeLayout.removeView(addButton);
                if (ChatActivity.buttonNames.size() == 1){
                    ChatActivity.buttonNames.remove(0);
                }
                ChatActivity.buttonNames.add("Image");
                ChatActivity.observable.subscribe(ChatActivity.action);
                // PhotoCamera photoCamera = new PhotoCamera();
                // photoCamera.takeAPhoto();

            }
        });
    }





}
