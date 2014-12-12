package edu.geoservices.catmapper;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;


public class MainActivity extends Activity {
    public static Location lastKnownloc = null;
    public static Location currentLoc = null;
    public double latitude;
    public double longitude;
    public boolean userAuthenticated;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.barmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.quit_app:
                System.exit(0);
                return true;
            case R.id.file_report:
                Intent one = new Intent(this, QuestionsActivity.class);
                startActivity(one);
                return true;
            case R.id.view_map:
                Intent two = new Intent(this, MapActivity.class);
                startActivity(two);
                return true;
            case R.id.home_menu:
                Intent three = new Intent(this, MainActivity.class);
                startActivity(three);
                return true;
            case R.id.user_options:
                if (UserLogin.authenticateUser()) {
                    AlertDialog test;
                    test = createAlertDialog("You can do this now", "OK");
                    test.show();
                } else {
                    AlertDialog test;
                    test = createAlertDialogLogin("Tsk Tsk.. login first!", "OK");
                    test.show();
                }
                return true;
            case R.id.create_fence:
                if (UserLogin.authenticateUser()) {
                    Intent four = new Intent(this, DrawGraphicsElements.class);
                    startActivity(four);
                } else {
                    AlertDialog test;
                    test = createAlertDialogLogin("Tsk Tsk.. login first!", "OK");
                    test.show();
                }
                return true;
            case R.id.user_login:
                Intent five = new Intent(this, UserLogin.class);
                startActivity(five);
                return true;
            case R.id.user_logout:
                logout();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void logout() {

        UserLogin.userloggedin = false;
        UserLogin.userEmail = null;
        UserLogin.userGuid = null;
        UserLogin.userPassword = null;
        UserLogin.loginStatus = null;
        Toast.makeText(getBaseContext(), "You have been succesfully logged out", Toast.LENGTH_SHORT).show();
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);


    }

    public void onProviderDisabled(String provider)

    {
        Toast.makeText(getApplicationContext(), "GPS Disabled", Toast.LENGTH_LONG).show();
    }

    public void onProviderEnabled(String provider)

    {
        Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_LONG).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        // Push notices have now been implemented in the UserLogin class
//    	Parse.initialize(this, "8pIfq94uxwP45tdDhcw0EmQNaXaoJiufamjdWHuF", "1vXu5FriSj3d1hzj30CSEOasXzImSbEZbUYaBYrA"); 
//    	ParseAnalytics.trackAppOpened(getIntent());
//    	ParseObject testObject = new ParseObject("TestObject");
//    	testObject.put("foo", "bar");
//    	testObject.saveInBackground();   
//    	ParseInstallation.getCurrentInstallation().saveInBackground();
//    	// the username here will eventually be assigned as the email when a user creates an account, for now we hardcode it for testing    	
//    	username = "Payton";
//    	PushService.subscribe(getBaseContext(), username, MainActivity.class);
//    	//if a user wants to delete his account or turn off push notifications we can simply unsubscribe them from their channel which is essentially their username
//    	//PushService.unsubscribe(getBaseContext(), username);
//    	
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        final Button mapButton = (Button) findViewById(R.id.map_button); // Get a handle to the button so we can add a handler for the click event
        final Button reportButton = (Button) findViewById(R.id.report_button);


        LocationListener locationListener = new LocationListener() { // Define a listener that responds to location updates

            public void onLocationChanged(Location loc) {
                // Called when a new location is found by the network location provider.
//                loc.getLatitude();
//                loc.getLongitude();
                latitude = loc.getLatitude();
                longitude = loc.getLongitude();
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        // Register the listener with the Location Manager to receive location updates

        // Start listening for GPS coordinates
        LocationManager lm = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);// Acquire a reference to the system Location Manager
        if (!lm.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//        	buildAlertMessageNoGps();
            AlertDialog noGPS;
            noGPS = gpsNotEnabled("GPS Not enabled", "Enable now");
            noGPS.show();
//        	setContentView(R.layout.main);
        } else {
            Toast.makeText(getApplicationContext(), "GPS Enabled", Toast.LENGTH_LONG).show();
            String lpgps = LocationManager.GPS_PROVIDER;
            String lpnet = LocationManager.NETWORK_PROVIDER;
            lm.requestLocationUpdates(lpgps, 0, 0, locationListener);
            lm.requestLocationUpdates(lpnet, 0, 0, locationListener);
            lastKnownloc = lm.getLastKnownLocation(lpgps);
            if (!(lastKnownloc == null)) {
                longitude = MainActivity.lastKnownloc.getLongitude();
                latitude = MainActivity.lastKnownloc.getLatitude();
            }

        }

        mapButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, MapActivity.class);
                startActivity(i);

            }

        });

        reportButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, QuestionsActivity.class);
                startActivity(i);


            }


        });

    }

    ;

    public void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    public void buildAlertMessageNoLocation() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Unable to obtain a GPS location, reporting and mapping functions will be unavailable.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        return;
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @SuppressWarnings("deprecation")
    public AlertDialog gpsNotEnabled(String msg, String buttonText) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        AlertDialog msgDialog = dialogBuilder.create();
        //msgDialog.setTitle(title);
        msgDialog.setMessage(msg);
        msgDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                startActivityForResult(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS), 1);
                return;
            }
        });

        return msgDialog;
    }

    @SuppressWarnings("deprecation")
    public AlertDialog createAlertDialog(String msg, String buttonText) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        AlertDialog msgDialog = dialogBuilder.create();
        //msgDialog.setTitle(title);
        msgDialog.setMessage(msg);
        msgDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                return;
            }
        });

        return msgDialog;
    }

    @SuppressWarnings("deprecation")
    public AlertDialog createAlertDialogLogin(String msg, String buttonText) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        AlertDialog msgDialog = dialogBuilder.create();
        //msgDialog.setTitle(title);
        msgDialog.setMessage(msg);
        msgDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                openLogin();
                return;
            }
        });

        return msgDialog;
    }

    public void openLogin() {
        Intent five = new Intent(this, UserLogin.class);
        startActivityForResult(five, 0);
    }


}

