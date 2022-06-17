package com.example.hikerswatch;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.app.ComponentActivity;
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

    private final int REQUEST_CODE_COARSE_LOCATION = 0;
    private final int REQUEST_CODE_FINE_LOCATION = 1;



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
                    List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
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



        //If we click this Button, a feature of the app gets activated which needs permission for FINE_LOCATION to work. So we have to check for permission before we are able to proceed witch feature Logic.
        //If permission was previously denied, we explain why the App needs the permission and ask again.
        button.setOnClickListener(view -> {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            /*
                if(shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)){
                    showDialogue("COARSE-LOCATION is needed", "The App needs your approximate location to work", Manifest.permission.ACCESS_COARSE_LOCATION);
                } else requestPermissions(new String []{Manifest.permission.ACCESS_COARSE_LOCATION}, REQUEST_CODE_COARSE_LOCATION);
             */
                if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                    showDialogue("FINE-LOCATION is needed", "Tis is the reason, why the App needs this permission", Manifest.permission.ACCESS_FINE_LOCATION);
                } else {
                    requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_FINE_LOCATION);

                }


            } else {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            }
        });

    }

    public void showDialogue(String title, String message, String permission) {
        new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{permission}, REQUEST_CODE_FINE_LOCATION);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                })
                .create().show();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        switch (requestCode) {


            case REQUEST_CODE_FINE_LOCATION:

                //At this pont its a little odd to me that apperantly botch, the COARSE and FINE LOCATION Permissions are granted, even though i
                //just asked for FINE LOCATION. Read the doc on "Request location permissions" for more info, i assume it will be explained there.
                //Link is in bookmarks in Chrome under Bookmarks -> Android -> CheckOut.
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
                }
                break;

            //commented out the Case for COARSE Location Permission because apparently its not needed. Keeping it for reference tho. (We also don't request permission for COARSE Location, check the onClickListener for the Button.

           /*
            case REQUEST_CODE_COARSE_LOCATION:
                Log.i("Case Coarse Location ","!!!!!" );
                Log.i("requestcode ",requestCode+"" );
                Log.i("ArrayLenght permssiosn: ", permissions.length + "");
                for(int i=0; i<= permissions.length-1;i++){
                    Log.i("Elemente in PermissionsArray ", permissions[i] + "");
                }
                Log.i("ArrayLenght grant results: ", grantResults.length + "");
                for(int i=0; i<= grantResults.length-1;i++){
                    Log.i("Elemente in GrantResults Array", grantResults[i] + "");
                }
                break;


            */

        }


    }


    //The Code below was not used for the program but I´m keeping it for reference.
    public boolean hasPermission_COARSE_LOCATION() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else return false;
    }


    public boolean hasPermission_FINE_LOCATION() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else return false;
    }

    //Asks for the Permissions that the App doesn't have yet (Without use of shouldShowRequestPermissionRationale() )
    public void askForPermissions() {
        permissionsToRequest.clear();

        if (!hasPermission_COARSE_LOCATION()) {
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (!hasPermission_FINE_LOCATION()) {
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }

        if (!permissionsToRequest.isEmpty()) {
            requestPermissions(permissionsToRequest.toArray(new String[0]), 0);
        }
    }

}


/*
Note:
Lies dir  die Docs zu "Request location permissions" durch, die scheinen ziemlich aufschlussreich zu sein, wie man
die Permission anfragt. Was mich auch die ganz Zeit etwas verwirrt hat, war,
ob man jetzt COARSE UND FINE Location anfragen muss, wenn man nur die Fine Location haben will.
Ich hatte mal gelesen, dass man das im Manifest jedenfalls so angeben muss. Der Artikel solte diesbezüglich mehr klarheit geben.
Link ist in den Chrome Lesezeichen unter CheckOut.

Ich habe bei StackOverflow ein Beispiel gesehen wie man RequestCodes handeln kann, und habe es auch hier so übernommen.
Link zu dem Thread: https://stackoverflow.com/questions/35484767/activitycompat-requestpermissions-not-showing-dialog-box
Ist auch in den Lesezeichen gespeichert unter Android -> Checkout
 */