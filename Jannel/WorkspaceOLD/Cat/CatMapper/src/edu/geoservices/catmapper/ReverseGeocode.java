package edu.geoservices.catmapper;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.StrictMode;
import android.widget.ProgressBar;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by pbaldridge on 8/7/13.
 */
public class ReverseGeocode extends ReportActivity {

    InputStream responses;
    JSONObject Status;
    JSONArray results;
    String street;
    String city;
    String zip;

    ProgressBar myProgressBar;
    int myProgress = 0;
    static String[] address;


    protected String[] getAddress(final String Lat, final String Long) {
//        Thread t = new Thread() {
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();

        StrictMode.setThreadPolicy(policy);

        String url = "http://geoservices-dev.tamu.edu/Services/ReverseGeocoding/WebService/v04_01/HTTP/default.aspx?";

//            public void run() {
//                Looper.prepare();
        HttpParams httpParams = new BasicHttpParams();
        HttpClient client = new DefaultHttpClient(httpParams);
        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
        HttpResponse response;
//                String apiKey = "demo";
        String apiKey = "c2819225f960479c8dee089344381232";
        String format = "json";
        String notStore = "false";
        String version = "4.01";
        String state = "tx";


        try {

            String geom = String.format("lat=%s&lon=%s&state=tx&apikey=%s&format=%s&notStore=%s&version=%s"
                    , Lat, Long, apiKey, format, notStore, version);
            String request = url + geom;
            HttpPost post = new HttpPost(request);

//
//                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
//                    pairs.add(new BasicNameValuePair("Features", geom));
//
//                    post.setEntity(new UrlEncodedFormEntity(pairs ));

            response = client.execute(post);
//            Thread.sleep(500);
            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
            StringBuilder builder = new StringBuilder();
            for (String line = null; (line = reader.readLine()) != null; ) {
                builder.append(line).append("\n");
            }
            Status = new JSONObject(builder.toString());
            results = Status.getJSONArray("StreetAddresses");

            JSONObject Status2 = results.getJSONObject(0);
            street = Status2.getString("StreetAddress");
            city = Status2.getString("City");
            state = Status2.getString("State");
            zip = Status2.getString("Zip");
            String[] temp = {street, city, state, zip};
            address = temp;


                    /*Checking response */
            if (response != null) {
                responses = response.getEntity().getContent(); //Get the data in the entity
            }

        } catch (Exception e) {
            e.printStackTrace();
//                    AlertDialog test;
//                    test = createAlertDialog("Error", "Cannot Establish Connection");
//                    test.show();
        }
        return address;
    }

//Looper.loop();
//            }
//    };

//        t.start();


//    }


//    private AlertDialog createAlertDialog(String msg, String buttonText){
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//        AlertDialog msgDialog = dialogBuilder.create();
//        //msgDialog.setTitle(title);
//        msgDialog.setMessage(msg);
//        msgDialog.setButton(buttonText, new DialogInterface.OnClickListener(){
//            @Override
//            public void onClick(DialogInterface dialog, int idx){
//
//
//                return;
//            }
//        });
//
//        return msgDialog;
//    }

}
