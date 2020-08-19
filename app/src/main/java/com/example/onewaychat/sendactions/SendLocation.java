package com.example.onewaychat.sendactions;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.example.onewaychat.hystoryRoom.History;

import static com.example.onewaychat.chat.ChatActivity.linearLayoutInScrollView;


public class SendLocation extends AppCompatActivity {

    Context chatActivityContext;
    private LocationManager locationManager;
    public static Double latitude,longitude;
    History history = new History();
    LayoutInflater ltInflater;




    public void showLocation(View mapView) {

        locationManager = (LocationManager) chatActivityContext.getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(chatActivityContext, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(chatActivityContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast toast = Toast.makeText(chatActivityContext, "Permission failed", Toast.LENGTH_SHORT);
            toast.show();
            chatActivityContext.startActivity(new Intent(
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
    }
    private LocationListener locationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            latitude = location.getLatitude();
            longitude = location.getLongitude();
            Log.d("myLogs", "geo:" + latitude + "," + longitude);

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

    public void setContext(Context context){
        chatActivityContext = context;
    }
}
