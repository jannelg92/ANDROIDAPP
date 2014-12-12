package edu.tamu.gisdayevents.model;

public class CalendarDetailsModel {

    public String label;
    public String title;
    private int Btn;
    private String count = "0";
    public String date;
    public String details;
    
    // boolean to set visiblity of the counter
    private boolean isCounterVisible = false;
  
     
    public CalendarDetailsModel(){}
 
    public CalendarDetailsModel(String title, int rsvpBtn){
        this.title = title;
        this.Btn = rsvpBtn;

    }
     
    public CalendarDetailsModel(String title, String date, String details){

    	this.title = title;
    	this.date = date;
        this.details = details;
    }
     
    public String getTitle(){
        return this.title;
    }
     
    public int getRSVP(){
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