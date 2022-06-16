package com.example.hikerswatch;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

import androidx.annotation.NonNull;
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

import java.security.Permission;
import java.util.ArrayList;

public class MainActivity extends Activity {

    TextView latitude;
    TextView longitude;
    TextView accuracy;
    TextView altitude;
    TextView address;
    ArrayList<String> permissionsToBeRequested = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude_ID);
        longitude = findViewById(R.id.longitude_ID);
        accuracy = findViewById(R.id.accuracy_ID);
        altitude = findViewById(R.id.altitude_ID);
        address = findViewById(R.id.address_ID);

        requestPermissions();
    }


    public boolean hasPermission_COARSE_LOCATION(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION ) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else return false;
    };

    public boolean hasPermission_FINE_LOCATION(){
        if(ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION ) == PackageManager.PERMISSION_GRANTED){
            return true;
        } else return false;
    };


    public void requestPermissions(){
        ArrayList<String> permissionsToBeRequested = new ArrayList<>();

        if (!hasPermission_COARSE_LOCATION()) {
            permissionsToBeRequested.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }
        if(!hasPermission_FINE_LOCATION()) {
            permissionsToBeRequested.add(ACCESS_FINE_LOCATION);
        }

        //checking if PermissionRationale should be shown and displaying dialogue, otherwise directly request Permission.
        if(!permissionsToBeRequested.isEmpty()) {
            for (int permissionIndex=0; permissionIndex <= permissionsToBeRequested.size()-1; permissionIndex++){
                if (shouldShowRequestPermissionRationale(permissionsToBeRequested.get(permissionIndex))){

                    String currentPermission = permissionsToBeRequested.get(permissionIndex);

                    switch (currentPermission) {
                        case Manifest.permission.ACCESS_COARSE_LOCATION:
                            showDialogue("Permission needed", "The app needs the approximate Location of you to run properly", permissionIndex);
                            break;

                        case Manifest.permission.ACCESS_FINE_LOCATION:
                            showDialogue("Permission needed", "The app needs the fine Location of you to run properly", permissionIndex);
                            break;
                    }
                } else {
                    ActivityCompat.requestPermissions(this, new String[] {permissionsToBeRequested.get(permissionIndex)}, 0);
                }

            }

        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }

    //The permission in the dialogue is the permission  we want to show the dialogue for
    public void showDialogue(String title, String message, int permissionIndex){
        new AlertDialog.Builder(this)
                .setTitle("Permission needed")
                .setMessage("The App needs you approximate Location to work properly")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[] {permissionsToBeRequested.get(permissionIndex)}, 0);
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