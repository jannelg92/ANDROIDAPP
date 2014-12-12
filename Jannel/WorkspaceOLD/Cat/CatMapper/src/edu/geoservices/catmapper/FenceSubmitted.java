package edu.geoservices.catmapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class FenceSubmitted extends Activity {


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.barmenu, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.file_report:
                Intent one = new Intent(this, QuestionsActivity.class);
                startActivity(one);
                return true;
            case R.id.view_map:
                Intent two = new Intent(this, MapActivity.class);
                startActivity(two);
                return true;
            case R.id.home_menu:
                Intent three = new Intent(this, FenceSubmitted.class);
                startActivity(three);
                return true;
            case R.id.user_login:
                return true;
            case R.id.user_options:
                return true;
            case R.id.create_fence:
                Intent four = new Intent(this, DrawGraphicsElements.class);
                startActivity(four);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fencesuccess);
        final Button mainMenuButton = (Button) findViewById(R.id.mainMenu_button); // Get a handle to the button so we can add a handler for the click event


        // Start listening for GPS coordinates


        mainMenuButton.setOnClickListener(new OnClickListener() {


            @Override
            public void onClick(View v) {
                Intent i = new Intent(FenceSubmitted.this, MainActivity.class);
                startActivity(i);

            }

        });


    }

    ;


    private AlertDialog createAlertDialog(String msg, String buttonText) {
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


}
