package edu.geoservices.catmapper;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Looper;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.esri.android.map.GraphicsLayer;
import com.esri.android.map.MapOnTouchListener;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.GeometryEngine;
import com.esri.core.geometry.MultiPath;
import com.esri.core.geometry.Point;
import com.esri.core.geometry.Polygon;
import com.esri.core.geometry.Polyline;
import com.esri.core.geometry.SpatialReference;
import com.esri.core.map.Graphic;
import com.esri.core.symbol.SimpleFillSymbol;
import com.esri.core.symbol.SimpleLineSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol;
import com.esri.core.symbol.SimpleMarkerSymbol.STYLE;
import com.parse.ParseInstallation;
import com.parse.ParseObject;
import com.parse.PushService;

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
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DrawGraphicsElements extends UserLogin {

    /*
     * ArcGIS Android elements
     */
    MapView mapViewfence = null;
    ArcGISTiledMapServiceLayer tiledMapServiceLayerfence = null;
    GraphicsLayer graphicsLayerfence = null;
    MyTouchListener myListenerfence = null;
    InputStream responses;
    JSONObject returns;
    JSONObject returns2;
    JSONObject results;
    JSONArray error;
    String objectID;
    public boolean polySuccess = false;
    //static MultiPath poly;
    //static Polygon userPoly = new Polygon();

    /*
     * Android UI elements
     */
    Button geometryButton = null;
    Button submitButton = null;
    Button clearButton = null;
    TextView label = null;

    /*
     * Other elements that hold app state
     */
    String mapURL = "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";

    final String[] geometryTypes = new String[]{"Point", "Polyline",
            "Polygon"};

    int selectedGeometryIndex = -1;

    @SuppressWarnings("serial")
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		setContentView(mapViewfence);
        setContentView(R.layout.createfence);
        /*
		 * Initialize ArcGIS Android MapView, tiledMapServiceLayerfence, and Graphics
		 * Layer
		 */
//		mapViewfence = new MapView(this);
        mapViewfence = (MapView) findViewById(R.id.map2);
        myListenerfence = new MyTouchListener(DrawGraphicsElements.this, mapViewfence);
        mapViewfence.setOnTouchListener(myListenerfence);

        submitButton = (Button) findViewById(R.id.submitbutton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                SpatialReference mapViewSr = mapViewfence.getSpatialReference();
                SpatialReference latLon = SpatialReference.create(4326);

                MultiPath multiPath = myListenerfence.poly;
                MultiPath mp2 = (MultiPath) GeometryEngine.project(multiPath, mapViewSr, latLon);
                Polygon polygon = new Polygon();
                polygon.add(mp2, false);
                Random randomGenerator = new Random();
                int newRandom = randomGenerator.nextInt(99999999);
                int newRandom2 = randomGenerator.nextInt(99999999);
                String userid = "A" + newRandom + newRandom2;
                sendJson(getUseremail(), userid, polygon);
                if (polySuccess) {
                    ParseObject testObject = new ParseObject("TestObject");
                    testObject.put("foo", "bar");
                    testObject.saveInBackground();
                    ParseInstallation.getCurrentInstallation().saveInBackground();
                    // need to create a user id with "A" and random numbers after to send as a channel to subscribe to
                    // this will then need to be included in website to send push notice to user id of any matching fences after new point added
                    PushService.subscribe(getBaseContext(), userid, UserLogin.class);
                }
            }
        });

		/*
		 * Initialize Android Geometry Button
		 */
        geometryButton = (Button) findViewById(R.id.geometrybutton);
        geometryButton.setEnabled(false);
        geometryButton.setOnClickListener(new View.OnClickListener() {
            /*
             * This displays an AlertDilaog as defined in onCreateDialog()
             * method. Invocation of show() causes onCreateDialog() to be called
             * internally.
             */
            public void onClick(View v) {
                showDialog(0);
            }
        });

        label = (TextView) findViewById(R.id.label);

        clearButton = (Button) findViewById(R.id.clearbutton);
        clearButton.setEnabled(false);
        clearButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                graphicsLayerfence.removeAll();


                clearButton.setEnabled(false);
            }
        });

		/*
		 * Initialize MapView, tiledMapServiceLayerfence and GraphicsLayer. This
		 * block will be executed when app is started the first time.
		 */
//		mapViewfence.centerAt(new Point(30.615902,-96.330555), true);
        Point centerPoint = new Point(MainActivity.lastKnownloc.getLongitude(), MainActivity.lastKnownloc.getLatitude());
        mapViewfence.zoomTo(centerPoint, 2);

        tiledMapServiceLayerfence = new ArcGISTiledMapServiceLayer(mapURL);
        graphicsLayerfence = new GraphicsLayer();

		/*
		 * Use TiledMapServiceLayer's OnStatusChangedListener to listen to
		 * events such as change of status. This event allows developers to
		 * check if layer is indeed initialized and ready for use, and take
		 * appropriate action. In this case, we are modifying state of other UI
		 * elements if and when the layer is loaded.
		 */
        tiledMapServiceLayerfence
                .setOnStatusChangedListener(new OnStatusChangedListener() {
                    /*
                     * This callback method will be invokes when status of layer
                     * changes
                     */
                    public void onStatusChanged(Object arg0, STATUS status) {
						/*
						 * Check if layer's new status = INITIALIZED. If it is,
						 * initialize UI elements
						 */
                        if (status
                                .equals(OnStatusChangedListener.STATUS.INITIALIZED)) {
                            geometryButton.setEnabled(true);
                        }
                    }
                });

        /**
         * Add TiledMapServiceLayer and GraphicsLayer to map
         */
        mapViewfence.addLayer(tiledMapServiceLayerfence);
        mapViewfence.addLayer(graphicsLayerfence);
    }

    /*
     * MapView's touch listener
     */
    class MyTouchListener extends MapOnTouchListener {
        ArrayList<Point> polylinePoints = new ArrayList<Point>();

        public MultiPath poly;
        String type = "";
        Point startPoint = null;

        public MyTouchListener(Context context, MapView view) {
            super(context, view);
        }

        public void setType(String geometryType) {
            this.type = geometryType;
        }

        public String getType() {
            return this.type;
        }

        /*
         * Invoked when user single taps on the map view. This event handler
         * draws a point at user-tapped location, only after "Draw Point" is
         * selected from Spinner.
         *
         * @see
         * com.esri.android.map.MapOnTouchListener#onSingleTap(android.view.
         * MotionEvent)
         */
        public boolean onSingleTap(MotionEvent e) {
            if (type.length() > 1 && type.equalsIgnoreCase("POINT")) {
                graphicsLayerfence.removeAll();
                Graphic graphic = new Graphic(mapViewfence.toMapPoint(new Point(e.getX(), e
                        .getY())), new SimpleMarkerSymbol(Color.RED, 25, STYLE.CIRCLE));
                //graphic.setGeometry();
                graphicsLayerfence.addGraphic(graphic);

                clearButton.setEnabled(true);
                return true;
            }
            return false;

        }

        /*
         * Invoked when user drags finger across screen. Polygon or Polyline is
         * drawn only when right selected is made from Spinner
         *
         * @see
         * com.esri.android.map.MapOnTouchListener#onDragPointerMove(android
         * .view.MotionEvent, android.view.MotionEvent)
         */
        public boolean onDragPointerMove(MotionEvent from, MotionEvent to) {
            if (type.length() > 1
                    && (type.equalsIgnoreCase("POLYLINE") || type
                    .equalsIgnoreCase("POLYGON"))) {


                Point mapPt = mapViewfence.toMapPoint(to.getX(), to.getY());

				/*
				 * if StartPoint is null, create a polyline and start a path.
				 */
                if (startPoint == null) {
                    graphicsLayerfence.removeAll();
                    poly = type.equalsIgnoreCase("POLYLINE") ? new Polyline()
                            : new Polygon();
                    startPoint = mapViewfence.toMapPoint(from.getX(), from.getY());
                    poly.startPath((float) startPoint.getX(),
                            (float) startPoint.getY());

					/*
					 * Create a Graphic and add polyline geometry
					 */
                    Graphic graphic = new Graphic(startPoint, new SimpleLineSymbol(Color.RED, 5));

					/*
					 * add the updated graphic to graphics layer
					 */
                    graphicsLayerfence.addGraphic(graphic);
                }

                poly.lineTo((float) mapPt.getX(), (float) mapPt.getY());

                return true;
            }

            return super.onDragPointerMove(from, to);

        }

        @Override
        public boolean onDragPointerUp(MotionEvent from, MotionEvent to) {
            if (type.length() > 1
                    && (type.equalsIgnoreCase("POLYLINE") || type
                    .equalsIgnoreCase("POLYGON"))) {

				/*
				 * When user releases finger, add the last point to polyline.
				 */
                if (type.equalsIgnoreCase("POLYGON")) {
                    poly.lineTo((float) startPoint.getX(),
                            (float) startPoint.getY());
                    graphicsLayerfence.removeAll();
                    graphicsLayerfence.addGraphic(new Graphic(poly, new SimpleFillSymbol(Color.RED)));

                }
                graphicsLayerfence.addGraphic(new Graphic(poly, new SimpleLineSymbol(Color.BLUE, 5)));
                startPoint = null;
                clearButton.setEnabled(true);
                return true;
            }
            return super.onDragPointerUp(from, to);
        }
    }

    /*
     * Returns an AlertDialog that includes names of all layers in the map
     * service
     */
    protected Dialog onCreateDialog(int id) {
        return new AlertDialog.Builder(DrawGraphicsElements.this)
                .setTitle("Select Geometry")
                .setItems(geometryTypes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        graphicsLayerfence.removeAll();

                        // ignore first element
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.BOTTOM, 0, 0);

                        // Get item selected by user.
                        String geomType = geometryTypes[which];
//                        label.setText(geomType + " selected.");
                        selectedGeometryIndex = which;

                        // process user selection
                        if (geomType.equalsIgnoreCase("Polygon")) {
                            myListenerfence.setType("POLYGON");
                            toast.setText("Drag finger across screen to draw a Polygon. \nRelease finger to stop drawing.");
                        } else if (geomType.equalsIgnoreCase("Polyline")) {
                            myListenerfence.setType("POLYLINE");
                            toast.setText("Drag finger across screen to draw a Polyline. \nRelease finger to stop drawing.");
                        } else if (geomType.equalsIgnoreCase("Point")) {
                            myListenerfence.setType("POINT");
                            toast.setText("Tap on screen once to draw a Point.");
                        }

                        toast.show();
                    }
                }).create();
    }

    protected void sendJson(final String userEmail, final String userId, final Polygon userFence) {


        Thread t = new Thread() {

            String url = "http://arcserver01.tamu.edu/arcgis/rest/services/CatMapper/catMapperTwo/FeatureServer/1/addFeatures?f=pjson";

            public void run() {
                Looper.prepare(); //For Preparing Message Pool for the child Thread
                HttpParams httpParams = new BasicHttpParams();
                HttpClient client = new DefaultHttpClient(httpParams);
                HttpConnectionParams.setConnectionTimeout(client.getParams(), 10000); //Timeout Limit
                HttpResponse response;


                try {
                    HttpPost post = new HttpPost(url);

                    SpatialReference sr = SpatialReference.create(4326);
                    String geomJson = GeometryEngine.geometryToJson(sr, userFence);

                    String geom = String.format("[{\"geometry\":%s,\"attributes\":{\"USERID\":\"%s\",\"USEREMAIL\":\"%s\"}}]"
                            , geomJson, userId, userEmail);

                    List<NameValuePair> pairs = new ArrayList<NameValuePair>();
                    pairs.add(new BasicNameValuePair("Features", geom));
                    pairs.add(new BasicNameValuePair("gdbVersion", "10.1"));
                    pairs.add(new BasicNameValuePair("rollbackOnFailure", "true"));

                    post.setEntity(new UrlEncodedFormEntity(pairs));
                    response = client.execute(post);

                    BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent(), "UTF-8"));
                    StringBuilder builder = new StringBuilder();
                    for (String line = null; (line = reader.readLine()) != null; ) {
                        builder.append(line).append("\n");
                    }
                    returns = new JSONObject(builder.toString());

                    JSONArray test = returns.names();

                    if ("error".equals(test.getString(0))) {
                        polySuccess = false;
                        results = returns.getJSONObject("error");
                        error = results.getJSONArray("details");
                        AlertDialog err;
                        err = createAlertDialog2("Your fence lines intersect, please redraw fence", "OK");
                        err.show();
                        graphicsLayerfence.removeAll();
                        clearButton.setEnabled(false);

                    } else {
                        polySuccess = true;
                        AlertDialog success;
                        success = createAlertDialog2("Success - Your fence has been established", "OK");
                        success.show();
                        graphicsLayerfence.removeAll();
                        clearButton.setEnabled(false);

                        // This will be moved to another menu item section where used subscribes to email or push notifications


                    }


//                    objectID = returns2.getString("objectId");

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

    @SuppressWarnings("deprecation")
    public AlertDialog createAlertDialog2(String msg, String buttonText) throws InterruptedException {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        AlertDialog msgDialog = dialogBuilder.create();
        //msgDialog.setTitle(title);
        msgDialog.setMessage(msg);
        msgDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(final DialogInterface dialog, final int idx) {


                return;
            }
        });
//        
//        if (polySuccess == true){
//
//            Intent i = new Intent(DrawGraphicsElements.this,FenceSubmitted.class);
//            startActivity(i);

//        }
        return msgDialog;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapViewfence.pause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapViewfence.unpause();
    }

}