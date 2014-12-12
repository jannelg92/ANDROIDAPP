package edu.geoservices.catmapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * Created by pbaldridge on 8/7/13.
 */
public class QuestionsActivity extends ReverseGeocode implements LocationListener {
    Bitmap imagethumb;
    private ImageView mImage;
    private Bitmap bitmap;
    private static final int CAMERA_PIC_REQUEST = 1111;
    private static final int PICK_IMAGE = 2222;
    private Uri imageUri;

    String photoURL = null;
    private String currentLong;
    private String currentLat;
    private String Lat;
    private String Long;
    private String x;
    private String y;
    private String imageFilePath;
    private String street;
    private String city;
    private String state;
    private String zip;
    String[] reverseResult = null;

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.questions);
        //mImage = (ImageView) findViewById(R.id.catimage);
        final Button submitButton = (Button) findViewById(R.id.submit_button);
        final Button takePicButton = (Button) findViewById(R.id.takepic_button);
        final Button selectPicButton = (Button) findViewById(R.id.selectpic_button);
        final Button cancelButton = (Button) findViewById(R.id.cancel_button);
        final Spinner totNum = (Spinner) findViewById(R.id.TotalNumberSpinner);
        final Spinner beenFed = (Spinner) findViewById(R.id.fedSpinner);
        final Spinner timeWeeks = (Spinner) findViewById(R.id.timeSpinner);
        final ImageView catpic = (ImageView) findViewById(R.id.catimage);


        String lp = LocationManager.GPS_PROVIDER;
//        if (MainActivity.currentLoc.getLatitude()){
        if (latitude == 0) {

            if (MainActivity.lastKnownloc != null) {

                Long = String.valueOf(MainActivity.lastKnownloc.getLongitude());
                Lat = String.valueOf(MainActivity.lastKnownloc.getLatitude());
                x = Long;
                y = Lat;
            } else {
                buildAlertMessageNoLocation();
            }
        } else {
            Long = String.valueOf(longitude);
            Lat = String.valueOf(latitude);
            x = Long;
            y = Lat;
        }


        final Date today = new Date();
//        reverseResult = ReverseGeocode(Lat, Long);

//        synchronized (reverseResult) {
//            try {
//                reverseResult.wait();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//        while(reverseResult==null) try {
//            wait();
//        } catch (InterruptedException e) {
//        }

//        if (reverseResult!=null){
//            street = reverseResult[0];
//            city = reverseResult[1];
//            state = reverseResult[2];
//            zip = reverseResult[3];
//        }

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        takePicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                startActivityForResult(cameraIntent, 1);
//                finish();

//                Intent i = new Intent(QuestionsActivity.this,CameraActivity.class);
//                startActivity(i);
                Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                File photo = new File(Environment.getExternalStorageDirectory(), "Pic.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photo));
                imageUri = Uri.fromFile(photo);
                startActivityForResult(intent, CAMERA_PIC_REQUEST);


            }
        });

        selectPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);

                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);

            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String TotalNumber = totNum.getSelectedItem().toString();
                String Fed = beenFed.getSelectedItem().toString();
                String SinceWeek = timeWeeks.getSelectedItem().toString();
//                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String PhotoDate = new SimpleDateFormat("yyy-MM-dd HH:mm:ss").format(today);
//                Integer image = catpic.getImageAlpha();
                Integer image = 0;

//                if (TotalNumber.isEmpty()){
                if (TotalNumber.isEmpty()) {
                    TotalNumber = null;
                } else {
                    TotalNumber = totNum.getSelectedItem().toString();
                }
                if (SinceWeek.isEmpty()) {
                    SinceWeek = null;
                } else {
                    SinceWeek = timeWeeks.getSelectedItem().toString();
                }
                if (Fed.isEmpty()) {
                    Fed = null;
                } else if ("YES".equals(Fed)) {
                    Fed = "1";
                } else {
                    Fed = "0";
                }
                if (image > 0) {
                    photoURL = image.toString();
                } else {

                }

//              Reverse GEOCODING through A&M GeoServices is implemented here. If we decide to use this feature we
//              will need to handle the wait time better, possibly with a progress bar
//                reverseResult = getAddress(Lat, Long);
//                street = reverseResult[0];
//                city = reverseResult[1];
//                city = reverseResult[2];
//                city = reverseResult[3];

                sendJson(x, y, Lat, Long, TotalNumber, Fed, SinceWeek, imagethumb);
//                sendJson(x,y,Lat,Long,imagethumb);
                finish();
            }

        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        ImageView mImage = (ImageView) findViewById(R.id.catimage);

        if (resultCode == Activity.RESULT_OK && requestCode == CAMERA_PIC_REQUEST) {
//            final Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
//            mImage.setImageBitmap(thumbnail);
//            if (thumbnail == imagethumb){
//                imagethumb.recycle();
//            }
//            else{
            Uri selectedImage = imageUri;
            getContentResolver().notifyChange(selectedImage, null);
            ContentResolver cr = getContentResolver();
            Bitmap bitmap;
            try {
                bitmap = android.provider.MediaStore.Images.Media
                        .getBitmap(cr, selectedImage);
                Toast.makeText(this, "This file: " + selectedImage.toString(),
                        Toast.LENGTH_LONG).show();
                Integer initHeight = bitmap.getHeight();
                Integer initWidth = bitmap.getWidth();
                double newHeight = 300;
                double scaler = initWidth.doubleValue() / initHeight.doubleValue();
                double newWidth = scaler * newHeight;
                Bitmap scaledImage = Bitmap.createScaledBitmap(bitmap, (int) newWidth, (int) newHeight, true);
                mImage.setImageBitmap(scaledImage);
                imagethumb = scaledImage;
            } catch (Exception e) {
                Toast.makeText(this, "Failed to load", Toast.LENGTH_SHORT)
                        .show();
                Log.e("Camera", e.toString());
            }

        }

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            if (requestCode == PICK_IMAGE && data != null && data.getData() != null) {
                Uri _uri = data.getData();

                //User had pick an image.
                Cursor cursor = getContentResolver().query(_uri, new String[]{android.provider.MediaStore.Images.ImageColumns.DATA}, null, null, null);
                cursor.moveToFirst();

                //Link to the image
                imageFilePath = cursor.getString(0);
                Bitmap selectedImage = BitmapFactory.decodeFile(imageFilePath);
                Integer initHeight = selectedImage.getHeight();
                Integer initWidth = selectedImage.getWidth();
                double newHeight = 300;
                double scaler = initWidth.doubleValue() / initHeight.doubleValue();
                double newWidth = scaler * newHeight;
                Bitmap scaledImage = Bitmap.createScaledBitmap(selectedImage, (int) newWidth, (int) newHeight, true);
                mImage.setImageBitmap(scaledImage);
                imagethumb = scaledImage;
                cursor.close();
            }
            super.onActivityResult(requestCode, resultCode, data);
        }

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

    public void onLocationChanged(Location location) {

    }

    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    public void onProviderEnabled(String provider) {

    }

    public void onProviderDisabled(String provider) {

    }


}



