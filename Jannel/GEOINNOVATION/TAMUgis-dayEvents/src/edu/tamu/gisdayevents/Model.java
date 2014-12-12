package edu.tamu.gisdayevents;

public class Model 
{
	    private int icon;
	    private String title;
	    private String counter;
	 
	    private boolean isGroupHeader = false;
	 
	    public Model(String titleH) 
	    {
	        this(-1,titleH,null);
	        isGroupHeader = true;
	    }
	    public Model(int icon, String titleM, String counter) 
	    {
	        super();
	        this.icon = icon;
	        this.title = titleM;
	        this.counter = counter;
	    }
		public boolean isGroupHeader() {
			// TODO Auto-generated method stub
			return false;
		}
	 
	//gettters & setters...
		
		int[] viewIDs = new int[] {
				R.id.item_icon, R.id.item_title, R.id.item_counter
		};
		String[] elements = new String[] {
				"icon", "title", "counter"
		};
	    public String getTitle() {
	    	return  title;
	    }
	    public int getIcon() {
	    	return icon;
	    }
	    public String getCounter() {
	    	return counter;
	    }
	    
	    
}
