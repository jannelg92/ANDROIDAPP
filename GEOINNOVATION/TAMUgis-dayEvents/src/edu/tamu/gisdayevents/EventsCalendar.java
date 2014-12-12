package edu.tamu.gisdayevents;

import java.util.ArrayList;
import edu.tamu.gisdayevents.R;
import edu.tamu.gisdayevents.MainHome;
import edu.tamu.gisdayevents.adapter.EventsCalendarAdapter;
import edu.tamu.gisdayevents.adapter.NavDrawerListAdapter;
import edu.tamu.gisdayevents.model.EventsCalendarItem;
import edu.tamu.gisdayevents.model.NavDrawerItem;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.os.Build;
import android.app.FragmentManager;

import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.MenuInflater;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.TextView;





public class EventsCalendar extends Fragment {

	private View rootView;
	
	public RelativeLayout itemCalendarLayout;
	public ListView eventCalendarList;
	private ArrayList<EventsCalendarItem> calendarEvents; 
	private EventsCalendarAdapter eAdapter;
	public String eventTitle;
	public Drawable eventMoreBtn;  
	public View details;
	
	
	
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
			rootView = inflater.inflate(R.layout.event_calendar_item,
				container, false);
			return rootView;
	}
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
	        calendarEvents = new ArrayList<EventsCalendarItem>();
	        itemCalendarLayout = (RelativeLayout) getActivity().findViewById((R.id.eventCalendarListitem));
	        eventCalendarList = (ListView) getActivity().findViewById(R.id.elistView);
	        
	       
	        eventCalendarList.setOnItemClickListener(new eventOnClickListener());
	        
//	        eAdapter = new EventsCalendarAdapter(getApplicationContext(), 
	 //       		calendarEvents);
	        eventCalendarList.setAdapter(eAdapter);
	        
	        
	        
	        eventCalendarList.setAdapter(new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, calendarEvents));  
	           
	       
	}
	        
	   private class eventOnClickListener implements
            ListView.OnItemClickListener {
    
	        		@Override
	        		public void onItemClick(AdapterView<?> parent, View view, int position,
	        					long id) {
	        			// display view for selected nav drawer item
	        				displayView(position);
	        				
	        		}
	   		}
	
	   
	   
	   /**
	     * Diplaying fragment view for selected nav drawer list item
	     * */
	    @SuppressWarnings("unused")
		private void displayView(int position) {
	        // update the main content by replacing fragments
	        Fragment fragment = null;  
	        fragment = new CalendarDetails();

	        if (fragment != null) {
	            FragmentManager fragmentManager = getFragmentManager();
	            fragmentManager.beginTransaction()
	                    .replace(R.id.flContent, fragment).commit();
	 
	            // update selected item and title, then close the drawer
	            eventCalendarList.setItemChecked(position, true);
	            eventCalendarList.setSelection(position);
	
	            
	        } else {
	            // error in creating fragment
	            Log.e("MainActivity", "Error in creating fragment");
	        }
	    }
	        
	        	        
	public void f_event_calendar() {
	}
	
	
 
    private ArrayList<EventsCalendarItem> generateData(){
        ArrayList<EventsCalendarItem> models = new ArrayList<EventsCalendarItem>();
        models.add(new EventsCalendarItem("TAMU GIS Events", R.drawable.ic_action_about));
        models.add(new EventsCalendarItem("Menu Item 1", R.drawable.ic_action_signup));
        models.add(new EventsCalendarItem("Menu Item 2", R.drawable.ic_action_eventcal));
        models.add(new EventsCalendarItem("Menu Item 3", R.drawable.ic_action_eventdetails));
 
        return models;
    }
		
	}


