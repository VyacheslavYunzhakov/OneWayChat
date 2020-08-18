package com.example.onewaychat.chat;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;


import com.example.onewaychat.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class ChangeButtons extends AppCompatActivity {
    static int marginForAddButton = 40;
    static int additionalMargin = 180;
    public static int clickCounter = 0;
    private static int counterForMarginForButtons = 1;
    public static ArrayList<FloatingActionButton> buttonsContainer = new ArrayList<>();
    Context chatActivityContext;


   // public void addAddButton(final FloatingActionButton addButton, final RelativeLayout mainRelativeLayout) {

    public void addAddButton(final RelativeLayout mainRelativeLayout) {

        final FloatingActionButton addButton = new FloatingActionButton(chatActivityContext);
        buttonsContainer.add(addButton);
        final FloatingActionButton messageButton = new FloatingActionButton(chatActivityContext);
        buttonsContainer.add(messageButton);
        final FloatingActionButton cameraButton = new FloatingActionButton(chatActivityContext);
        buttonsContainer.add(cameraButton);
        final FloatingActionButton imageButton = new FloatingActionButton(chatActivityContext);
        buttonsContainer.add(imageButton);
        final FloatingActionButton geolocationButton = new FloatingActionButton(chatActivityContext);
        buttonsContainer.add(geolocationButton);


        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        relativeLayoutParams.setMargins(marginForAddButton, marginForAddButton, marginForAddButton, marginForAddButton);
        addButton.setImageResource(R.drawable.ic_baseline_add_24);
        addButton.setSize(FloatingActionButton.SIZE_NORMAL);
        addButton.setLayoutParams(relativeLayoutParams);

        mainRelativeLayout.addView(addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCounter % 2 == 0) {
                    addButton.setImageResource(R.drawable.ic_baseline_close_24);
                    addButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F76B1C")));
                    clickCounter++;
                    createButtons(messageButton, mainRelativeLayout, R.drawable.ic_baseline_textsms_24);
                    createButtons(cameraButton, mainRelativeLayout, R.drawable.ic_baseline_photo_camera_24);
                    createButtons(imageButton, mainRelativeLayout, R.drawable.ic_baseline_collections_24);
                    createButtons(geolocationButton, mainRelativeLayout, R.drawable.ic_baseline_near_me_24);
                } else {
                    addButton.setImageResource(R.drawable.ic_baseline_add_24);
                    addButton.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FF9800")));
                    clickCounter++;
                    counterForMarginForButtons = 1;
                   // addMessageButton("remove",mainRelativeLayout);
                    for (FloatingActionButton i:  buttonsContainer){
                        if (i != addButton) {
                            removeButtons(mainRelativeLayout, i);
                        }
                    }
                }
            }
        });
    }

    private void removeButtons(final RelativeLayout mainRelativeLayout,FloatingActionButton removingButton){
        Log.d ("removingList","" + removingButton.toString());
        mainRelativeLayout.removeView(removingButton);
    }

    private  void createButtons(final FloatingActionButton floatingActionButton, final RelativeLayout mainRelativeLayout,
                                final int intImageOfButton){
        RelativeLayout.LayoutParams relativeLayoutParams = new RelativeLayout.LayoutParams
                (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        relativeLayoutParams.addRule(RelativeLayout.ALIGN_PARENT_END);
        floatingActionButton.setImageResource(intImageOfButton);
        floatingActionButton.setSize(FloatingActionButton.SIZE_NORMAL);
        relativeLayoutParams.setMargins(marginForAddButton, marginForAddButton, marginForAddButton,
                marginForAddButton + additionalMargin * counterForMarginForButtons);
        counterForMarginForButtons++;
        floatingActionButton.setLayoutParams(relativeLayoutParams);
        mainRelativeLayout.addView(floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (FloatingActionButton i:  buttonsContainer){
                    removeButtons(mainRelativeLayout, i);
                }
                if (ChatActivity.buttonNames.size() == 1){
                    ChatActivity.buttonNames.remove(0);
                }
                counterForMarginForButtons = 1;
                ChatActivity.buttonNames.add(Integer.toString(intImageOfButton));
                ChatActivity.observable.subscribe(ChatActivity.action);
            }
        });
    }

    public void setContext(Context context){
        chatActivityContext = context;
    }


}
