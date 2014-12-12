package edu.geoservices.catmapper;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class DetailActivity extends Activity{

	String lat, lon;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.detail_);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		Bundle recieveBundle = this.getIntent().getExtras();
		final String[] attributes = recieveBundle.getStringArray("attributes");
		TextView sightingInfo = (TextView) findViewById(R.id.information);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
        try {
			Date test = dateFormat.parse(attributes[4]);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
//        String addedDate = dateFormat.format(attributes[4]);
		sightingInfo.setText("Total Number of Cats: " + attributes[0] + "\n" + "Looks fed? " + attributes[1] + "\n" + "Longitude: " + attributes[2] + "\n" + "Latitude: " + attributes[3] + "\n" + "Date Added: " + attributes[4] + "\n");
		lat = attributes[0];
		lon = attributes[1];
	}
	


	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.barmenu, menu);
		return super.onCreateOptionsMenu(menu);
		
		
		
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

//	public boolean onOptionsItemSelected (MenuItem item) {
//		//Handle presses on the action bar items
//		switch (item.getItemId())
//		{
//
//
//			case R.id.overflow_trackProgress:
//	//			trackProgress();
//				return true;
//				
//			case R.id.routing:
//				routing();
//				return true;
//								
//			default:
//				return super.onOptionsItemSelected(item);
//		
//		}
//		
//		
//	}
	
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
    
	private void routing() {
		Intent intent = new Intent(android.content.Intent.ACTION_VIEW, 
		Uri.parse("http://maps.google.com/maps?daddr=" + lat + "," + lon));
		startActivity(intent);
		
	}
}
