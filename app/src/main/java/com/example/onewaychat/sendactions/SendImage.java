package com.example.onewaychat.sendactions;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.onewaychat.hystoryRoom.History;
import com.example.onewaychat.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.onewaychat.chat.ChatActivity.linearLayoutInScrollView;

public class SendImage extends AppCompatActivity {
    Context chatActivityContext;
    LayoutInflater ltInflater;
    private Uri imageURI;
    History history = new History();
    public Intent photoPickerIntent;

    public void putImage() throws IOException {
        photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        ltInflater = LayoutInflater.from(chatActivityContext);
    }


    @SuppressLint("CutPasteId")
    public void sendImageToChat(Intent data) {
        final int imageOrienationLayout;
        ImageView imageViewInLayout;

        View imageLayoutView = ltInflater.inflate(R.layout.imageview, linearLayoutInScrollView, false);
        final ImageView imageView = imageLayoutView.findViewById(R.id.imageView);
        final Uri imageUri = data.getData();
        imageView.setImageURI(imageUri);
        BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
        //Log.d("myLogs", "drawable.getIntrinsicHeight()" + drawable.getIntrinsicHeight() +
        //        ", drawable.getIntrinsicWidth()" + drawable.getIntrinsicWidth());
        if (drawable.getIntrinsicHeight() >drawable.getIntrinsicWidth()){
            imageOrienationLayout = R.layout.imageview;
            imageViewInLayout = imageView;
        } else {imageOrienationLayout = R.layout.horizontalimageview;
            imageLayoutView = ltInflater.inflate(R.layout.horizontalimageview, linearLayoutInScrollView, false);
            imageViewInLayout = imageLayoutView.findViewById(R.id.imageView);
            imageViewInLayout.setImageURI(imageUri);
        }
        Bitmap bitmap = drawable.getBitmap();
        try {
            File imageFile = createImageFile();
            FileOutputStream out = new FileOutputStream(imageFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            imageURI = FileProvider.getUriForFile(this,
                    "com.example.onewaychat.fileprovider",
                    imageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        history.saveHistory(imageURI, R.id.imageView, imageOrienationLayout, "image");
        linearLayoutInScrollView.addView(imageViewInLayout);
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
