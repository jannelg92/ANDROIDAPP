package edu.tamu.gisdayevents;

import java.util.ArrayList;


import android.app.ListActivity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

	public class MenuListItem extends ListActivity {
		 
	    public void onCreate(Bundle icicle) {
	        super.onCreate(icicle);
	 
	        // if extending Activity
	        setContentView(R.layout.main_home);
	 
	        // 1. pass context and data to the custom adapter
	        MyAdapter adapter = new MyAdapter(this, generateData());
	 
	        // if extending Activity 2. Get ListView from activity_main.xml
	        ListView listView = (ListView) findViewById(R.id.listView);
	 
	        // 3. setListAdapter
	        listView.setAdapter(adapter);   
	        setListAdapter(adapter);
	    }
	 
	    private ArrayList<Model> generateData(){
	        ArrayList<Model> models = new ArrayList<Model>();
	        models.add(new Model("TAMU GIS Events"));
	        models.add(new Model(R.drawable.ic_action_signup,"Menu Item 1","1"));
	        models.add(new Model(R.drawable.ic_action_eventcal,"Menu Item 2","2"));
	        models.add(new Model(R.drawable.ic_action_eventdetails,"Menu Item 3","12"));
	 
	        return models;
	    }
	 
	}

