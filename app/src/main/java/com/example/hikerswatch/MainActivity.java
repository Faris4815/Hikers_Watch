package com.example.hikerswatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {

    TextView latitude_TW;
    TextView longitude_TW;
    TextView accuracy_TW;
    TextView altitude_TW;
    TextView address_TW;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude_TW = findViewById(R.id.latitude_ID);
        longitude_TW = findViewById(R.id.longitude_ID);
        accuracy_TW = findViewById(R.id.accuracy_ID);
        altitude_TW = findViewById(R.id.altitude_ID);
        address_TW = findViewById(R.id.address_ID);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // You can use the API that requires the permission.
            //performAction(...);
        } else {
            // You can directly ask for the permission.
            // The registered ActivityResultCallback gets the result of this request.
            //requestPermissionLauncher.launch(
            //Manifest.permission.ACCESS_COARSE_LOCATION);

        }


    }
}