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

    TextView latitude;
    TextView longitude;
    TextView accuracy;
    TextView altitude;
    TextView address;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude_ID);
        longitude = findViewById(R.id.longitude_ID);
        accuracy = findViewById(R.id.accuracy_ID);
        altitude = findViewById(R.id.altitude_ID);
        address = findViewById(R.id.address_ID);

        //Es gibt viel bessere Wege die Permissions anzufragen aber ich folge hier mal den sehr simplen Weg der im Tutorial gezeigt wurde um voran zu kommen.
        //Im Internet werden oft ActivityResultLauncher benutzt. Siehe Artikel in Chrome Lesezeichen -> Android -> CheckOut
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