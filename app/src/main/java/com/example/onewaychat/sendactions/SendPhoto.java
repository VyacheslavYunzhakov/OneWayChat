package com.example.onewaychat.sendactions;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.onewaychat.hystoryRoom.History;
import com.example.onewaychat.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

;
import static com.example.onewaychat.chat.ChatActivity.linearLayoutInScrollView;


public class SendPhoto extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 0;
    private Uri photoURI;
    public Intent cameraIntent;
    Context chatActivityContext;

    LayoutInflater ltInflater;
    History history = new History();

    public void takeAPhoto () throws IOException {

        cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory(),
                "test.jpg");
        File photoFile = null;
        photoFile = createImageFile();
        photoURI = FileProvider.getUriForFile(chatActivityContext,
                "com.example.onewaychat.fileprovider",
                photoFile);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
        //chatActivity.getContext().startActivityForResult(cameraIntent, CAMERA_REQUEST);
        ltInflater = LayoutInflater.from(chatActivityContext);
    }
    public void sendPhotoToChat (){
            View cameraView = ltInflater.inflate(R.layout.imageview, linearLayoutInScrollView, false);
            ImageView cameraImageView = cameraView.findViewById(R.id.imageView);
            cameraImageView.setImageURI(photoURI);
            history.saveHistory(photoURI, R.id.imageView, R.layout.imageview, "camera");
            Log.d("cameraURI1", "" + photoURI);
            linearLayoutInScrollView.addView(cameraView);
        }


    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = chatActivityContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        String mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public void setContext(Context context){
        chatActivityContext = context;
    }
}
