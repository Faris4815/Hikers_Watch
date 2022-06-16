package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Debug;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends Activity {

    TextView latitude;
    TextView longitude;
    TextView accuracy;
    TextView altitude;
    TextView address;
    Button button;
    List<String> permissionsToRequest = new ArrayList<>();
    Geocoder geocoder;
    LocationManager locationManager;
    LocationListener locationListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitude = findViewById(R.id.latitude_ID);
        longitude = findViewById(R.id.longitude_ID);
        accuracy = findViewById(R.id.accuracy_ID);
        altitude = findViewById(R.id.altitude_ID);
        address = findViewById(R.id.address_ID);
        button = findViewById(R.id.button_ID);

        geocoder = new Geocoder(this, Locale.GERMANY);
        locationManager = (LocationManager) getApplicationContext().getSystemService(getApplicationContext().LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(@NonNull Location location) {
                Log.i("Location", location.toString());
                try {
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(),1);
                    Log.i("Location", addresses.get(0).toString());

                    latitude.setText("Latitude: " + addresses.get(0).getLatitude());
                    longitude.setText("Longitude: " + addresses.get(0).getLongitude());
                    accuracy.setText("Accuracy: " + location.getAccuracy());
                    altitude.setText("Altitude: " + location.getAltitude());
                    address.setText("Address: \n" + addresses.get(0).getAddressLine(0));


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        };


        //If we click this Button, a feature of the app gets activated which needs permission for COARSE- and FINE_LOCATION to work. So we have to check for permission before we are able to proceed witch feature Logic.
        //If permission was previously denied, we explain why the App needs the permission and ask again.
        button.setOnClickListener(view -> {
            Log.i("MYInfo", "Hallo welt");

            //TODO: implement the Permission Requests for Coarse and Finde Location. first attempt at lin 99-104 but its not quite working.
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.


                /*


                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    showDialogue("COARSE-LOCATION is needed", "The App needs your approximate location to work", Manifest.permission.ACCESS_COARSE_LOCATION);
                } else requestPermissions(new String []{Manifest.permission.ACCESS_COARSE_LOCATION},0);
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)){
                    showDialogue("FINE-LOCATION is needed", "The App needs your approximate location to work", Manifest.permission.ACCESS_FINE_LOCATION);
                } else requestPermissions(new String []{Manifest.permission.ACCESS_COARSE_LOCATION},0);


                 */

            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
            });

/*

        //This is the way to do it if you want check for the permission BEFORE YOU WANT TO USE A FEATURE THAT NEEDS IT. Not useful for requesting all permissions
        //at the start of the app
        if (hasPermission_COARSE_LOCATION()) {
            Log.i("MyInfo","mExec1");
            // You can use the API that requires the permission.
            //TODO: performAction(...);
        } else if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Log.i("MyInfo","mExec2");
            // In an educational UI, explain to the user why your app requires this
            // permission for a specific feature to behave as expected. In this UI,
            // include a "cancel" or "no thanks" button that allows the user to
            // continue using your app without granting the permission.
            showDialogue("COARSE-LOCATION is needed", "The App needs your approximate location to work", Manifest.permission.ACCESS_COARSE_LOCATION);
        } else {
            Log.i("MyInfo","mExec3");
            // You can directly ask for the permission.
            requestPermissions(new String[] { Manifest.permission.ACCESS_COARSE_LOCATION }, 0);
        }

*/
    }



    public boolean hasPermission_COARSE_LOCATION(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else return false;
    }


    public boolean hasPermission_FINE_LOCATION(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else return false;
    }


    //Asks for the Permissions that the App doesnt have yet (Without use of shouldShowRequestPermissionRationale() )
    public void askForPermissions(){
        permissionsToRequest.clear();

        if(!hasPermission_COARSE_LOCATION()){
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if(!hasPermission_FINE_LOCATION()){
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if(!permissionsToRequest.isEmpty()) {
            requestPermissions(permissionsToRequest.toArray(new String[0]), 0);
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

    //TODO: Implement the logic for onRequestPermissionsResult.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }
}