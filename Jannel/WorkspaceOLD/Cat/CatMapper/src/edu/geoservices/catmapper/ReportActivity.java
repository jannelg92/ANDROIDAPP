package edu.geoservices.catmapper;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreProtocolPNames;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONObject;

import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureEditResult;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.Looper;

/**
 * Created by paytonbaldridge on 8/7/13.
 */
public class ReportActivity extends MainActivity {

    InputStream responses;
    JSONObject returns;
    JSONObject returns2;
    JSONArray results;
    String objectID;
    String PhotoDate = null;
    String[] address;


//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.questions);
//    }

    protected void sendJson(final String x, final String y, final String Lat, final String Long, final String TotalNumber, final String Fed, final String SinceWeek, final Bitmap imagethumb) {
//    protected void sendJson(final String x, final String y, final String Lat, final String Long, final Bitmap imagethumb) {

        Thread t = new Thread() {

            String url = "http://arcserver01.tamu.edu/arcgis/rest/services/CatMapper/catMapperTwo/FeatureServer/0/addFeatures?f=pjson";

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpParams httpParams = new BasicHttpParams();
                HttpClient client = new DefaultHttpClient(httpParams);
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;
                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.US);
                Date date = new Date();
                // PhotoDate will actually be ReportDate in final application so always post the current date here
                PhotoDate = dateFormat.format(date);
                String guid = "NA";
                String added = dateFormat.format(date);
                // We are not posting the CatchDate in this section, that will be a separate json post from user login options
//                String CatchDate = null;
//                CatchDate = dateFormat.format(date);
//                String StreetAddress = "201";
//                String City = "College Station";
//                String State = "TX";
                String intComp = "1";
//                String Zip = "77840";


                try {
                    HttpPost post = new HttpPost(url);

//                    String geom = String.format("[{\"geometry\":{\"x\":%s,\"y\":%s},\"attributes\":{\"TotalNumber\":%s,\"Fed\":%s,\"Long\":%s,\"Lat\":%s,\"SinceWeek\":%s,\"PhotoDate\":\"%s\"}}]"
//                            ,x,y,TotalNumber,Fed,Long,Lat,SinceWeek,PhotoDate);
////
                    String geom = String.format("[{\"geometry\":{\"x\":%s,\"y\":%s},\"attributes\":{\"TotalNumber\":%s,\"Fed\":%s,\"Long\":%s,\"Lat\":%s,\"SinceWeek\":%s,\"PhotoDate\":\"%s\",\"guid\":%s,\"added\":\"%s\"}}]"
                            , x, y, TotalNumber, Fed, Long, Lat, SinceWeek, PhotoDate, guid, added);
//
                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("Features", geom));

                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    response = client.execute(post);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder builder = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        builder.append(line).append("\n");
                    }
                    returns = new JSONObject(builder.toString());
                    results = returns.getJSONArray("addResults");

                    returns2 = results.getJSONObject(0);
                    objectID = returns2.getString("objectId");
                    sendPushes(x, y);
                    if (imagethumb != null) {
                        sendJsonAttach(imagethumb);

                    }


                    /*Checking response */
//                    if(response!=null){
//                        responses = response.getEntity().getContent(); //Get the data in the entity
//                    }

                } catch (Exception e) {
                    e.printStackTrace();
//                    AlertDialog test;
//                    test = createAlertDialog("Error", "Cannot Establish Connection");
//                    test.show();
                }
                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();

    }

    public File saveImage(Bitmap myBitmap, Context context) {

        File myDir = new File(Environment.getExternalStorageDirectory().getPath());
//        File myDir = new File(Environment.getExternalStorageDirectory(), context.getPackageName());
//    	File myDir = new File("/mnt/sdcard/edu.geoservices.catmapper/");
        if (!myDir.exists()) {
            myDir.mkdir();
        }
        String fname = "UploadedImage.png";
        File file = new File(myDir, fname);
        if (file.exists()) file.delete();
        try {
            FileOutputStream out = new FileOutputStream(file);
            myBitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            out.flush();
            out.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }


    public void sendPushes(final String x, final String y) {
        Thread t = new Thread() {
            String url = "http://catmapper.tamu.edu/Rest/Geo/Fences/Notify/";

            public void run() {
                Looper.prepare();
                HttpParams httpParams = new BasicHttpParams();
                HttpClient client = new DefaultHttpClient(httpParams);
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;

                try {
                    HttpPost post = new HttpPost(url);

                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("x", x));
                    pairs.add(new BasicNameValuePair("y", y));

                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    response = client.execute(post);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder builder = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        builder.append(line).append("\n");
                    }
                    returns = new JSONObject(builder.toString());
                    String pushStatus = returns.getString("status");
//                         if(loginStatus.contentEquals("200") ){
//                        	 userGuid = returns.getString("guid");
//                        	 userloggedin = true;                        	 
//                        	 AlertDialog test;
//                        	 test = createAlertDialog("Your account is created and your Guid is " + userGuid, "OK");
//                        	 test.show(); 
//
//                         }
//                         else{
//                        	 userGuid = null;
//                        	 userloggedin = false;
//                        	 AlertDialog test;
//                        	 test = createAlertDialog("Invalid Login!", "OK");
//                        	 test.show();                        	 
//                         }

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


    public void sendJsonAttach(final Bitmap image) {
        Thread t = new Thread() {
            String urlAttach1 = "http://arcserver01.tamu.edu/arcgis/rest/services/CatMapper/catMapperTwo/FeatureServer/0";
            String urlAttach2 = "/addAttachment?f=pjson";
            String urlAttach = urlAttach1 + objectID + urlAttach2;

            public void run() {
                Looper.prepare();
                try {

                    File imageFile = saveImage(image, ReportActivity.this);  
                    CallbackListener<FeatureEditResult> callback = null;
                    MapActivity.featureLayer.addAttachment(33228,imageFile, callback);

                    HttpClient httpclient = new DefaultHttpClient();
                    httpclient.getParams().setParameter(CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
                    HttpPost httppost = new HttpPost(urlAttach);
                    MultipartEntity mpEntity = new MultipartEntity();
                    FileBody cbFile = new FileBody(imageFile, "image/png");
                    cbFile.getMediaType();
                    mpEntity.addPart("attachment", cbFile);
                    httppost.setEntity(mpEntity);
                    HttpResponse response = httpclient.execute(httppost);


                    /*Checking response */
                    if (response != null) {
                        InputStream in = response.getEntity().getContent(); //Get the data in the entity
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    AlertDialog test;
                    test = createAlertDialog("Error", "Cannot Establish Connection");
                    test.show();
                }

                Looper.loop(); //Loop in the message queue
            }
        };

        t.start();


    }

//    Runnable revgeo = new Runnable() {
//
//        String url = "http://geoservices-dev.tamu.edu/Services/ReverseGeocoding/WebService/v04_01/HTTP/default.aspx?";
//
//        HttpParams httpParams = new BasicHttpParams();
//        HttpClient client = new DefaultHttpClient(httpParams);
////        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
//        HttpResponse response;
//        String apiKey = "c2819225f960479c8dee089344381232";
//        String format = "json";
//        String notStore = "false";
//        String version = "4.01";
//        String state = "tx";
//        InputStream responses;
//        JSONObject Status;
//        JSONArray results;
//        String street;
//        String city;
//        String zip;
//
//        public void run() {
//
//
//            try {
//
//                String geom = String.format("lat=%s&lon=%s&state=tx&apikey=%s&format=%s&notStore=%s&version=%s"
//                        ,Lat,Long,apiKey,format,notStore,version);
//                String request = url + geom;
//                HttpPost post = new HttpPost(request);
//
//                response = client.execute(post);
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//                StringBuilder builder = new StringBuilder();
//                for (String line = null; (line = reader.readLine()) != null;) {
//                    builder.append(line).append("\n");
//                }
//                Status = new JSONObject(builder.toString());
//                results = Status.getJSONArray("StreetAddresses");
//
//                JSONObject Status2 = results.getJSONObject(0);
//                street = Status2.getString("StreetAddress");
//                city = Status2.getString("City");
//                state = Status2.getString("State");
//                zip = Status2.getString("Zip");
//                String[] temp = {street,city,state,zip};
//                address = temp;
//
//
//                    /*Checking response */
//                if(response!=null){
//                    responses = response.getEntity().getContent(); //Get the data in the entity
//                }
//
//            } catch(Exception e) {
//                e.printStackTrace();
////                    AlertDialog test;
////                    test = createAlertDialog("Error", "Cannot Establish Connection");
////                    test.show();
//            }
//
//        }
//
//    };


//    protected String[] ReverseGeocode(final String Lat, final String Long)  {
//
//        String url = "http://geoservices-dev.tamu.edu/Services/ReverseGeocoding/WebService/v04_01/HTTP/default.aspx?";
//
//        HttpParams httpParams = new BasicHttpParams();
//        HttpClient client = new DefaultHttpClient(httpParams);
//        HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
//        HttpResponse response;
//        String apiKey = "c2819225f960479c8dee089344381232";
//        String format = "json";
//        String notStore = "false";
//        String version = "4.01";
//        String state = "tx";
//        InputStream responses;
//        JSONObject Status;
//        JSONArray results;
//        String street;
//        String city;
//        String zip;
//        Runnable revgeo = new Runnable() {
//
//            public void run() {
//
//
//        try {
//
//
//            String geom = String.format("lat=%s&lon=%s&state=tx&apikey=%s&format=%s&notStore=%s&version=%s"
//                    ,Lat,Long,apiKey,format,notStore,version);
//            String request = url + geom;
//            HttpPost post = new HttpPost(request);
//
//            response = client.execute(post);
//
//            BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
//            StringBuilder builder = new StringBuilder();
//            for (String line = null; (line = reader.readLine()) != null;) {
//                builder.append(line).append("\n");
//            }
//            Status = new JSONObject(builder.toString());
//            results = Status.getJSONArray("StreetAddresses");
//
//            JSONObject Status2 = results.getJSONObject(0);
//            street = Status2.getString("StreetAddress");
//            city = Status2.getString("City");
//            state = Status2.getString("State");
//            zip = Status2.getString("Zip");
//            String[] temp = {street,city,state,zip};
//            address = temp;
//
//
//                    /*Checking response */
//            if(response!=null){
//                responses = response.getEntity().getContent(); //Get the data in the entity
//            }
//
//        } catch(Exception e) {
//            e.printStackTrace();
////                    AlertDialog test;
////                    test = createAlertDialog("Error", "Cannot Establish Connection");
////                    test.show();
//        }
//
//            }
//
//        };
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

//        return msgDialog;
//    }

}
