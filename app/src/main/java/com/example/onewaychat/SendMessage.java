package com.example.onewaychat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.ColumnInfo;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Update;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import static com.example.onewaychat.ChatActivity.linearLayoutInScrollView;

public class SendMessage extends AppCompatActivity  {

    public static ImageButton sendMessageButton;
    Context context;
    LayoutInflater ltInflater;
    //LinearLayout linearLayoutInScrollView;
    History history = new History();

    public void sendMessage(final RelativeLayout mainRelativeLayout) {


        this.context = ChatActivity.context;
        ltInflater = LayoutInflater.from(context);

        final View sendMessage = ltInflater.inflate(R.layout.sendmessage, mainRelativeLayout, false);
        mainRelativeLayout.addView(sendMessage);

        sendMessageButton = mainRelativeLayout.findViewById(R.id.sendMessageButton);
       // linearLayoutInScrollView = mainRelativeLayout.findViewById(R.id.linearLayoutInScrollView);
        sendMessageButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {



                EditText sendText = mainRelativeLayout.findViewById(R.id.inputMessage);
                switch (v.getId()) {
                    case R.id.sendMessageButton:
                        View text = ltInflater.inflate(R.layout.text, linearLayoutInScrollView, false);
                        TextView textMessage = text.findViewById(R.id.textMessage);

                        textMessage.setText(sendText.getText());
                        Log.d("Size","" + text.getWidth());
                        history.saveHistory("" + textMessage.getText(), R.id.textMessage, R.layout.text, "text");
                        sendText.setText("");
                        linearLayoutInScrollView.addView(text);
                        FloatingActionButton addButton = new FloatingActionButton(context);
                        mainRelativeLayout.removeView(sendMessage);
                        ChangeButtons.clickCounter++;
                        ChangeButtons changeButtons = new ChangeButtons();
                        changeButtons.addAddButton(addButton, mainRelativeLayout);
                }
            }
        });
    }
}


