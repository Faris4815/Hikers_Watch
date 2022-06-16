package com.example.hikerswatch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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


        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Log.i("MyInfo","mExec1");
            // You can use the API that requires the permission.
            //TODO: performAction(...);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Log.i("MyInfo","mExec2");
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            //TODO: showInContextUI(...);
            showDialogue("COARSE-LOCATION is needed", "The App needs your approximate location to work", Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            Log.i("MyInfo","mExec3");
            // You can directly ask for the permission.
            requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, 0);
        }


    }



    public void showDialogue(String title, String message, String permission){
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[] {permission}, 0);
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }
}