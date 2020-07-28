package com.example.onewaychat;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import rx.Observable;
import rx.functions.Action1;

public class ChatActivity extends AppCompatActivity {

    public static Context context;
    private Uri photoURI;

    public static LinearLayout linearLayoutInScrollView;
    private static final int CAMERA_REQUEST = 0;
    private final int Pick_image = 1;
    public static Double latiude,longitude;
    private LocationManager locationManager;

    LayoutInflater ltInflater;
    public static ArrayList buttonNames = new ArrayList();
    public static Observable observable;
    public static Action1<String> action;


    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        linearLayoutInScrollView =findViewById(R.id.linearLayoutInScrollView);

        context = this;
        FloatingActionButton addButton = new FloatingActionButton(this);
        RelativeLayout mainRelativeLayout = findViewById(R.id.mainRelativeLayout);
        ChangeButtons changeButtons = new ChangeButtons();
        changeButtons.addAddButton(addButton,mainRelativeLayout);



        observable = Observable.from(buttonNames);

        action = new Action1<String>() {
            @Override
            public void call(String s) {
                if (s.equals(Integer.toString(R.drawable.ic_baseline_photo_camera_24))){
                    try {
                        takeAPhoto ();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if  (s.equals(Integer.toString(R.drawable.ic_baseline_collections_24))){
                        putImage();
                }
                if  (s.equals(Integer.toString(R.drawable.ic_baseline_near_me_24))){
                        showLocation();
                }
                if (s.equals(Integer.toString(R.drawable.ic_baseline_textsms_24))) {
                    RelativeLayout mainRelativeLayout = findViewById(R.id.mainRelativeLayout);
                    SendMessage sendMessage = new SendMessage();
                    sendMessage.sendMessage(mainRelativeLayout);
                }

            }


        };



    }

    private void showLocation() {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast toast = Toast.makeText(this, "Permission failed", Toast.LENGTH_SHORT);
            toast.show();
            startActivity(new Intent(
                    android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                1000 * 10, 10, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 10, 10,
                locationListener);

        ltInflater = getLayoutInflater();
        View mapView = ltInflater.inflate(R.layout.sharelocation, linearLayoutInScrollView, false);
        linearLayoutInScrollView.addView(mapView);
        addButton();
    }

    private void putImage() {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, Pick_image);
        ltInflater = getLayoutInflater();
        addButton();
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
            addButton();
        }
        if(requestCode == Pick_image && resultCode == RESULT_OK){
            try {
                View imageLayoutView = ltInflater.inflate(R.layout.imageview, linearLayoutInScrollView, false);
                ImageView imageView = imageLayoutView.findViewById(R.id.imageView);
                final Uri imageUri = data.getData();
                final InputStream imageStream = getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                imageView.setImageBitmap(selectedImage);
                linearLayoutInScrollView.addView(imageView);
                ChangeButtons.clickCounter++;
                addButton();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
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

        String mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            latiude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d("myLogs", "geo:" + latiude + "," + longitude);

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }


    };
    private void addButton(){
        RelativeLayout mainRelativeLayout = findViewById(R.id.mainRelativeLayout);
        FloatingActionButton addButton = new FloatingActionButton(this);
        ChangeButtons changeButtons = new ChangeButtons();
        ChangeButtons.clickCounter++;
        changeButtons.addAddButton(addButton, mainRelativeLayout);
    }
    }

