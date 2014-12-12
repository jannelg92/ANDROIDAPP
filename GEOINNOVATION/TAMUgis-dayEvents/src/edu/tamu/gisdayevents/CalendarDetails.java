package edu.tamu.gisdayevents;

import java.util.ArrayList;

import edu.tamu.gisdayevents.adapter.CalendarDetailsAdapter;
import edu.tamu.gisdayevents.model.CalendarDetailsModel;


import android.app.Fragment;
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

public class CalendarDetails extends Fragment  {

	public static Fragment newInstance(Context context) {
		CalendarDetails f = new CalendarDetails();
        return f;
    }
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.f_event_details,
				container, false);
		return rootView;
		
		
		
	}
	
	 private ArrayList<CalendarDetailsModel> generateData(){
	        ArrayList<CalendarDetailsModel> models = new ArrayList<CalendarDetailsModel>();
	        models.add(new CalendarDetailsModel("Name", "Date", "Details"));
	        
	 
	        return models;
	    }

			
		
	 
	}