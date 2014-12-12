package edu.geoservices.catmapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;


public class UserLogin extends MainActivity {
    public static Location lastKnownloc = null;
    public static Location currentLoc = null;
    public double latitude;
    public double longitude;
    static String userPassword = null;
    JSONObject returns;
    static String loginStatus;
    static boolean userloggedin;
    public static String userEmail;
    public static String userGuid;
    static Context current;


    @Override
    public void onCreate(Bundle savedInstanceState) {

        //if a user wants to delete his account or turn off push notifications we can simply unsubscribe them from their channel which is essentially their email
        //PushService.unsubscribe(getBaseContext(), email);
        Parse.initialize(this, "8pIfq94uxwP45tdDhcw0EmQNaXaoJiufamjdWHuF", "1vXu5FriSj3d1hzj30CSEOasXzImSbEZbUYaBYrA");
        ParseAnalytics.trackAppOpened(getIntent());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginscreen);
        final Button userLogin = (Button) findViewById(R.id.userLoginButton);
        final Button userCreate = (Button) findViewById(R.id.userCreateButton);
        final EditText emailView = (EditText) findViewById(R.id.emailTextbox);
        final EditText passwordView = (EditText) findViewById(R.id.passwordTextbox);


//    	ParseObject testObject = new ParseObject("TestObject");
//    	testObject.put("foo", "bar");
//    	testObject.saveInBackground();   
//    	ParseInstallation.getCurrentInstallation().saveInBackground();     	
//    	
//    	PushService.subscribe(getBaseContext(), userEmail, UserLogin.class);

        userLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//				loginUser("paytonbaldridge@gmail.com","bakery11");
                if (getText(R.id.emailTextbox) != null && getText(R.id.passwordTextbox) != null) {
                    userEmail = emailView.getText().toString();
//                	userPassword = "bakery11";
                    userPassword = passwordView.getText().toString();
                    loginUser(userEmail, userPassword);

                } else {
                    AlertDialog test;
                    test = createAlertDialog("Email and Password must be entered", "Ok");
                    test.show();
//                	Toast.makeText(getApplicationContext(), "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }


            }
        });

        userCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


//				loginUser("paytonbaldridge@gmail.com","bakery11");
                if (getText(R.id.emailTextbox) != null && getText(R.id.passwordTextbox) != null) {
                    userEmail = emailView.getText().toString();
//                	userPassword = "bakery11";
                    userPassword = passwordView.getText().toString();
                    createUser(userEmail, userPassword);

                } else {
                    AlertDialog test;
                    test = createAlertDialog("Email and Password must be entered", "Ok");
                    test.show();
//                	Toast.makeText(getApplicationContext(), "Please enter your email and password", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }

//			

    public void createUser(final String email, final String password) {
        Thread t = new Thread() {
            String url = "http://catmapper.tamu.edu/Rest/Signup/";

            public void run() {
                Looper.prepare();
                HttpParams httpParams = new BasicHttpParams();
                HttpClient client = new DefaultHttpClient(httpParams);
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                    HttpPost post = new HttpPost(url);

                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("email", email));
                    pairs.add(new BasicNameValuePair("password", password));

                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    response = client.execute(post);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder builder = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        builder.append(line).append("\n");
                    }
                    returns = new JSONObject(builder.toString());
                    loginStatus = returns.getString("status");
                    if (loginStatus.contentEquals("200")) {
                        userloggedin = true;
                        AlertDialog test;
                        test = createAlertDialogLoggedin("Your account has been created", "OK");
                        test.show();
                    } else {
                        userGuid = null;
                        userloggedin = false;
                        AlertDialog test;
                        test = createAlertDialog("Invalid Login!", "OK");
                        test.show();
                    }

//                         returns2 = results.getJSONObject(0);
//                         objectID = returns2.getString("objectId");                    


                         /*Checking response */
//                         if(response!=null){
//                             responses = response.getEntity().getContent(); //Get the data in the entity
//                         }
                } catch (Exception e) {
                    e.printStackTrace();
//                         AlertDialog test;
//                         test = createAlertDialog("Error", "Cannot Establish Connection");
//                         test.show();
                }
                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();

    }

    public void loginUser(final String email, final String password) {
        Thread t = new Thread() {
            //        	Enable localhost debugging
            String url = "http://catmapper.tamu.edu/Rest/Login/";
//        	String url = "http://catmapper.tamu.edu/Rest/Login/";

            public void run() {
                Looper.prepare();
                HttpParams httpParams = new BasicHttpParams();
                HttpClient client = new DefaultHttpClient(httpParams);
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                    HttpPost post = new HttpPost(url);

                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("email", email));
                    pairs.add(new BasicNameValuePair("password", password));

                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    response = client.execute(post);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder builder = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        builder.append(line).append("\n");
                    }
                    returns = new JSONObject(builder.toString());
                    loginStatus = returns.getString("status");
//                         String queryEmail = returns.getString("QueryEmail");
                    if (loginStatus.contentEquals("200")) {
                        userGuid = returns.getString("guid");
                        userloggedin = true;
                        AlertDialog test;
                        test = createAlertDialogLoggedin("Your are logged in and your Guid is " + userGuid, "OK");
                        test.show();
                    } else {
                        userGuid = null;
                        userloggedin = false;
                        AlertDialog test;
                        test = createAlertDialog("Invalid Login! Error: "+ loginStatus , "OK");
                        test.show();
                    }

//                         returns2 = results.getJSONObject(0);
//                         objectID = returns2.getString("objectId");                    


                         /*Checking response */
//                         if(response!=null){
//                             responses = response.getEntity().getContent(); //Get the data in the entity
//                         }
                } catch (Exception e) {
                    e.printStackTrace();
//                         AlertDialog test;
//                         test = createAlertDialog("Error", "Cannot Establish Connection");
//                         test.show();
                }
                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();

    }

    public static boolean authenticateUser() {

        if (userloggedin) {
            return true;
        } else {
            return false;
        }

    }

//    public static void logout(){
//        
//    	userloggedin = false;
//    	userEmail = null;
//    	userGuid = null;
//    	userPassword = null;
//    	loginStatus = null; 
//    	Toast.makeText(current, "You have been succesfully logged out", Toast.LENGTH_SHORT).show();
//    	
//    
//    }

    public static String getUseremail() {
        return userEmail;
    }

    public static String getUserguid() {
        return userGuid;
    }


    @SuppressWarnings("deprecation")
    public AlertDialog createAlertDialogLoggedin(String msg, String buttonText) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        AlertDialog msgDialog = dialogBuilder.create();
        //msgDialog.setTitle(title);
        msgDialog.setMessage(msg);
        msgDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {
                if (userloggedin) {
                    openMain();
                }
                return;
            }
        });

        return msgDialog;
    }

    public void openMain() {
        Intent main = new Intent(this, MainActivity.class);
        startActivity(main);
    }
}
        
	


   

