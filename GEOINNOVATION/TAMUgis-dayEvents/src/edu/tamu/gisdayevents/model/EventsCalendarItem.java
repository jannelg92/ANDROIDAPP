package edu.tamu.gisdayevents.model;

public class EventsCalendarItem {

    public String label;
    public String title;
    private int Btn;
    private String count = "0";
    public String date;
    
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;
  
     
    public EventsCalendarItem(){}
 
    public EventsCalendarItem(String title, int moreBtn){
        this.title = title;
        this.Btn = moreBtn;
    }
     
    public EventsCalendarItem(String title, int moreBtn, boolean isCounterVisible, String count){
        this.label = label;
    	this.title = title;
    	this.date = date;
        this.Btn = moreBtn;
        this.isCounterVisible = isCounterVisible;
        this.count = count;
    }

     
   
     
     
    public String getTitle(){
        return this.title;
    }
     
    public int getMore(){
        return this.Btn;
    }
     
    public String getCount(){
        return this.count;
    }
     
    public boolean getCounterVisibility(){
        return this.isCounterVisible;
    }
     
    public void setTitle(String title){
        this.title = title;
    }
     
    public void setEventDate(CharSequence eventDate){
        this.date = date;
    }
     
    public void getEventDate(CharSequence eventDate){
        this.date = date;
    }
    public void setCount(String count){
        this.count = count;
    }
     
    public void setCounterVisibility(boolean isCounterVisible){
        this.isCounterVisible = isCounterVisible;
    }
}
