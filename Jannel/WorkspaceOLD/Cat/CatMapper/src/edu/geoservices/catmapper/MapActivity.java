package edu.geoservices.catmapper;

import java.io.InputStream;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.esri.android.map.LocationService;
import com.esri.android.map.MapView;
import com.esri.android.map.ags.ArcGISDynamicMapServiceLayer;
import com.esri.android.map.ags.ArcGISFeatureLayer;
import com.esri.android.map.ags.ArcGISTiledMapServiceLayer;
import com.esri.android.map.event.OnSingleTapListener;
import com.esri.android.map.event.OnStatusChangedListener;
import com.esri.core.geometry.Geometry;
import com.esri.core.geometry.Point;
import com.esri.core.map.CallbackListener;
import com.esri.core.map.FeatureEditResult;
import com.esri.core.map.FeatureTemplate;
import com.esri.core.map.Field;
import com.esri.core.map.Graphic;


public class MapActivity extends ReportActivity {
    MapView mMapView;
    String basemapMapURL = "http://services.arcgisonline.com/ArcGIS/rest/services/World_Street_Map/MapServer";
    String dynamicMapURL = "http://arcserver01.tamu.edu/arcgis/rest/services/CatMapper/catMapperTwo/MapServer";
    String featureMapURL = "http://arcserver01.tamu.edu/arcgis/rest/services/CatMapper/catMapperTwo/FeatureServer/0";
    String fencesMapURL = "http://arcserver01.tamu.edu/arcgis/rest/services/CatMapper/catMapperTwo/FeatureServer/1";
    ArcGISTiledMapServiceLayer basemapLayer;
    static ArcGISFeatureLayer featureLayer;
    ArcGISDynamicMapServiceLayer dynamicLayer;
    final int PICTURE_ACTIVITY = 1; // Only needed if catching the results of more than one activity.


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map);
        final Button mainMenu = (Button) findViewById(R.id.mainMenu_button);
        final Button Layers = (Button) findViewById(R.id.layerButton);
        //final EditText featureInfo = (EditText) findViewById(R.id.featureInfo);

        //Get popup info


// Retrieve the map from XML layout
        mMapView = (MapView) findViewById(R.id.map);

// Add basemap to MapView
        basemapLayer = new ArcGISTiledMapServiceLayer(basemapMapURL);
        mMapView.addLayer(basemapLayer);

// Add dynamic layer to MapView
        dynamicLayer = new ArcGISDynamicMapServiceLayer(dynamicMapURL);
        mMapView.addLayer(dynamicLayer);
        dynamicLayer.setVisible(false);


// Add Feature Layer to MapView
        featureLayer = new ArcGISFeatureLayer(featureMapURL, ArcGISFeatureLayer.MODE.SNAPSHOT);
        mMapView.addLayer(featureLayer);

    
        // The following code returns the layer ID in case it's needed for anything
        long l = dynamicLayer.getID();
        int layerID = (int) l;


        mainMenu.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }

        });

//        Layers.setOnClickListener(new View.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                if (dynamicLayer.isVisible()) {
//                    dynamicLayer.setVisible(false);
//                } else {
////            	  QueryTask queryTask = new QueryTask(fencesMapURL);
////            	  Query query = new Query();
////            	  query.isReturnGeometry();
////            	  query.getOutFields();
////            	  query.getWhere("USEREMAIL" == userEmail);
//                    dynamicLayer.setVisible(true);
//
//
////            	  callout.setStyle(R.xml.fencescallout);
////            	  String countyName = (String) gr.getAttributeValue("NAME");
////            	  String countyPop = gr.getAttributeValue("POP07_SQMI").toString();
//                    // Sets custom content layout to callout.
//                }
//            }
//
//        });

        mMapView.setOnSingleTapListener(new OnSingleTapListener() {
			public void onSingleTap(float x, float y) {
				if (mMapView.getLayers().length > 1){
					ArcGISFeatureLayer actLayer = (ArcGISFeatureLayer) mMapView.getLayer(2);
					Field[] fieldss = actLayer.getFields();
					String[] attr = {"TotalNumber","Fed","Long","Lat","added"};
//					String attr = "Long";
					int graphIDS[] = actLayer.getGraphicIDs(x, y, 50);
					if (graphIDS.length > 0) {
						Graphic graphic = actLayer.getGraphic(graphIDS[0]);
						int attrLen = attr.length;
						Object field = null;
//						field = graphic.getAttributeValue(attr);
						for (int i = 0; i < attrLen; i++){
							field = graphic.getAttributeValue(attr[i]);
							if (field != null){
							attr[i] = field.toString();
							}
							else{
								attr[i] = "(empty)";
							}
							}
						}

						Intent detailIntent = new Intent(MapActivity.this,DetailActivity.class);
						detailIntent.putExtra("attributes", attr);
						startActivity(detailIntent);
						
						
					}
				}
					
				

				
			
		});
        
        mMapView.setOnStatusChangedListener(
                new OnStatusChangedListener() {
                    AlertDialog errorDialog;

                    public void onStatusChanged(Object source, STATUS status) {


                        if (source == mMapView && status == STATUS.INITIALIZATION_FAILED) {
                            errorDialog = createAlertDialog("Basemap failed to load", "Continue");
                            errorDialog.show();
                        }

                        if (source == mMapView && status == STATUS.INITIALIZED) {

                            LocationService ls = mMapView.getLocationService();
                            ls.setAutoPan(false);
                            ls.setAccuracyCircleOn(true);
                            ls.start();
                            Point p = ls.getPoint();
        					mMapView.zoomToResolution(p, 4);

                        }
                    }
                });


        // below is how we can turn various listeners on and off at various times, for now we will leave the long press listener on and comment this out
        //mMapView.setOnLongPressListener(null);
    }


    /**
     * Adds a new feature using the defined Template;
     */

    public void applyAddFeature(Geometry geometry, FeatureTemplate template, ArcGISFeatureLayer featureLayer) {

        //create a graphic using the template
        Graphic graphic = featureLayer.createFeatureWithTemplate(template, geometry);

        featureLayer.applyEdits(new Graphic[]{graphic}, null, null, new CallbackListener<FeatureEditResult[][]>() {

            public void onError(Throwable error) {
                // TODO implement error code
            }

            public void onCallback(FeatureEditResult[][] editResult) {
                // Check the response for success or failure
                if (editResult[0] != null && editResult[0][0] != null && editResult[0][0].isSuccess()) {
                    // TODO implement success logic
                }
            }
        });
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
    public AlertDialog createAlertDialog2(InputStream msg, String buttonText) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        AlertDialog msgDialog = dialogBuilder.create();
        //msgDialog.setTitle(title);
        msgDialog.setMessage((CharSequence) msg);
        msgDialog.setButton(buttonText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int idx) {


                return;
            }
        });

        return msgDialog;
    }

    @Override

    protected void onDestroy() {

        super.onDestroy();

    }


    @Override

    protected void onPause() {

        super.onPause();

        mMapView.pause();

    }


    @Override

    protected void onResume() {

        super.onResume();

        mMapView.unpause();

    }


}
