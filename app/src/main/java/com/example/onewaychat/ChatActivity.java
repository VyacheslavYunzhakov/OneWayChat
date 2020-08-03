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
import android.graphics.drawable.BitmapDrawable;
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

import android.view.ViewManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import android.widget.ScrollView;
import android.widget.Toast;


import com.google.android.material.floatingactionbutton.FloatingActionButton;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;

public class ChatActivity extends AppCompatActivity {

    public static Context context;
    private Uri photoURI;
    private Uri imageURI;

    public static LinearLayout linearLayoutInScrollView;
    private static final int CAMERA_REQUEST = 0;
    private final int Pick_image = 1;

    public static Double latiude,longitude;
    private LocationManager locationManager;
    List<Integer> itemXmlIdList = new ArrayList<>();
    List<String> itemTextOrUriList = new ArrayList<>();
    List<String> itemTypeList = new ArrayList<>();
    List<Integer> itemViewIdList = new ArrayList<>();

    public static FloatingActionButton addButton;
    RelativeLayout mainRelativeLayout;

    LayoutInflater ltInflater;
    public static ArrayList buttonNames = new ArrayList();
    public static Observable observable;
    public static Action1<String> action;

    public ScrollView scrollView;

    public static View mapView;

    AppDatabase database = App.getInstance().getDatabase();

    History history = new History();

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        scrollView = findViewById(R.id.scrollView);
        linearLayoutInScrollView =findViewById(R.id.linearLayoutInScrollView);

        context = this;
        addButton = new FloatingActionButton(this);
        mainRelativeLayout = findViewById(R.id.mainRelativeLayout);

        ChangeButtons changeButtons = new ChangeButtons();
        changeButtons.addAddButton(addButton,mainRelativeLayout);

        ltInflater = getLayoutInflater();
        mapView = ltInflater.inflate(R.layout.sharelocation, linearLayoutInScrollView, false);

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
                    try {
                        putImage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
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


        //database.textDao().clearTable();
        itemTextOrUriList = database.itemDao().getTextOrUri();
        itemViewIdList = database.itemDao().getViewId();
        itemXmlIdList = database.itemDao().getXmlId();
        itemTypeList = database.itemDao().getType();

        Log.d ("loadLogs", "" + itemXmlIdList.size());
        for (int i = 0; i < itemXmlIdList.size(); i++){
            history.loadHistory(itemTextOrUriList.get(i), itemViewIdList.get(i), itemXmlIdList.get(i), itemTypeList.get(i));
        }


    }

    public void showLocation() {
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
                1000 * 1, 1, locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 1000 * 1, 1,
                locationListener);


        try {
            linearLayoutInScrollView.removeView(mapView);
        } catch (Exception e) {
            e.printStackTrace();
        }

        linearLayoutInScrollView.addView(mapView);
        history.saveHistory("location");
        try {
            mainRelativeLayout.removeView(addButton);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scrollDialogDown();
        addButton();
    }

    private void putImage() throws IOException {// throws IOException {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, Pick_image);
        ltInflater = getLayoutInflater();
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
            View cameraView = ltInflater.inflate(R.layout.imageview, linearLayoutInScrollView, false);
            ImageView cameraImageView = cameraView.findViewById(R.id.imageView);
            cameraImageView.setImageURI(photoURI);
            history.saveHistory(photoURI, R.id.imageView, R.layout.imageview, "camera");
            Log.d("cameraURI", "" + photoURI);
            linearLayoutInScrollView.addView(cameraView);
            addButton();
        }
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_CANCELED) {
            addButton();
        }
        if(requestCode == Pick_image && resultCode == RESULT_OK){
            View imageLayoutView = ltInflater.inflate(R.layout.imageview, linearLayoutInScrollView, false);
            ImageView imageView = imageLayoutView.findViewById(R.id.imageView);
            final Uri imageUri = data.getData();
            imageView.setImageURI(imageUri);
            BitmapDrawable drawable = (BitmapDrawable) imageView.getDrawable();
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
            history.saveHistory(imageURI, R.id.imageView, R.layout.imageview, "image");
            linearLayoutInScrollView.addView(imageView);
            addButton();
        }
        if (requestCode == Pick_image && resultCode == RESULT_CANCELED) {
            addButton();
        }
        scrollDialogDown();
    }
    private File createImageFile() throws IOException {
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

    public void scrollDialogDown() {
        scrollView.post(new Runnable() {

            @Override
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_DOWN);
            }
        });

    }
    }

