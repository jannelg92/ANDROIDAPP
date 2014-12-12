package edu.tamu.gisdayevents.adapter;

import edu.tamu.gisdayevents.model.EventsCalendarItem;
import edu.tamu.gisdayevents.model.NavDrawerItem;
import edu.tamu.gisdayevents.EventsCalendar;
import edu.tamu.gisdayevents.R;
import java.util.ArrayList;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class EventsCalendarAdapter extends BaseAdapter {


     
    private static final String title = null;
	private Context context;
    private ArrayList<EventsCalendarItem> EventsCalendarItems;
	private String txtTitle;
     
    public EventsCalendarAdapter(Context context, ArrayList<EventsCalendarItem> arrayList){
        this.context = context;
        this.EventsCalendarItems = arrayList;
    }
 
    @Override
    public Object getItem(int position) {       
        return position;
    }
    
    
    public String getTitle(){
        this.txtTitle = title;
    	return this.title;
    }
   
	@Override
    public int getCount() {
        return EventsCalendarItems.size();
    }
 
 
    @Override
    public long getItemId(int position) {
        return position;
    }
 
   
    
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater mInflater = (LayoutInflater)
                    context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.event_calendar_item, null);
        }
          
        ImageView imgBtn = (ImageView) convertView.findViewById(R.id.event_more);
        TextView txtLabel = (TextView) convertView.findViewById(R.id.event_label);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.event_name);
        TextView txtDate = (TextView) convertView.findViewById(R.id.event_date);  
        imgBtn.setImageResource(EventsCalendarItems.get(position).getMore());        
        txtTitle.setText(EventsCalendarItems.get(position).getTitle());
         
       
        return convertView;
     }

 
}