package edu.geoservices.gisday;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import android.R.menu;
import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.SearchManager;
import android.app.Activity;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.MediaRouteButton;
import android.content.res.Configuration;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.view.GravityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NavUtils;
import android.text.Selection;
import android.view.ContextMenu;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.Toast;




public class MainActivity extends Activity 
	{




private DrawerLayout mDrawerLayout; 
	private ListView mDrawerList;
	private ActionBarDrawerToggle drawer_toggle;
	
	private CharSequence mDrawerTitle;
	private CharSequence mPageTitle;
	
	DrawerAdapter adapter; 
	List<DrawerItem> dataList;
	
	 dataList = new ArrayList<DrawerItem>();
				 mPageTitle = mDrawerTitle = getTitle();
				 mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout); 
				 mDrawerList= (ListView) findViewById(R.id.left_drawer);
				 
				 mDrawerLayout.setDrawerShadow(R.drawable.gishadow1024x800, GravityCompat.START);
				 
				 dataList.add(new DrawerItem ("Log-in", R.drawable.ic_action_signup));
				 dataList.add(new DrawerItem ("Event Calendar", R.drawable.ic_action_eventcal));
				 dataList.add(new DrawerItem ("Event Check-in", R.drawable.ic_action_checkin));
				 dataList.add(new DrawerItem ("My Events", R.drawable.ic_action_myevents));
				 dataList.add(new DrawerItem ("Share", R.drawable.ic_action_share));
				 dataList.add(new DrawerItem ("Settings", R.drawable.ic_action_settings));
									
				 // Set the adapter for the list view
				 adapter = new DrawerAdapter(this, R.layout.custom_drawer_item, dataList);
						 
				 mDrawerList.setAdapter((ListAdapter) adapter); 
				 
				 mDrawerList.setOnItemClickListener(new CustomDrawerItemClickListener());
				
				getActionBar().setDisplayHomeAsUpEnabled(true);
				getActionBar().setHomeButtonEnabled(true);
				
				drawer_toggle = new ActionBarDrawerToggle(this, mDrawerLayout,
			            R.drawable.ic_drawer, R.string.drawer_open,
			            R.string.drawer_close) {
			      public void onDrawerClosed(View view) {
			            getActionBar().setTitle(mPageTitle);
			            invalidateOptionsMenu(); // creates call to
			                                                      // onPrepareOptionsMenu()
			      }
			 
			      public void onDrawerOpened(View drawerView) {
			            getActionBar().setTitle(mDrawerTitle);
			            invalidateOptionsMenu(); // creates call to
			                                                      // onPrepareOptionsMenu()
			      }
			};
			 
			mDrawerLayout.setDrawerListener(drawer_toggle);
			 
			if (savedInstanceState == null) {
			      selectItem(0);
			}
			
			
			private void selectItem(int position)
	{
		//update the main content by replacing fragments
		
		Fragment fragment = null;
		Bundle args = new Bundle();
		 switch (position) {
         case 0:
               fragment = new FragActivity();
               args.putString(FragActivity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(FragActivity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
         case 1:
               fragment = new Frag2Activity();
               args.putString(Frag2Activity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(Frag2Activity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
               /*        case 2:
               fragment = new Frag3Activity();
               args.putString(Frag3Activity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(Frag3Activity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;   */
         case 3:
               fragment = new FragActivity();
               args.putString(FragActivity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(FragActivity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
         case 4:
               fragment = new Frag2Activity();
               args.putString(Frag2Activity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(Frag2Activity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
               /*       case 5:
               fragment = new Frag3Activity();
               args.putString(Frag3Activity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(Frag3Activity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());   
               break;    */
         case 6:
               fragment = new FragActivity();
               args.putString(FragActivity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(FragActivity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
         case 7:
               fragment = new Frag2Activity();
               args.putString(Frag2Activity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(Frag2Activity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
               /*       case 8:
               fragment = new Frag3Activity();
               args.putString(Frag3Activity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(Frag3Activity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());  
               break;   */
         case 9:
               fragment = new FragActivity();
               args.putString(FragActivity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(FragActivity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
         case 10:
               fragment = new Frag2Activity();
               args.putString(Frag2Activity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(Frag2Activity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
  /*       case 11:
               fragment = new Frag3Activity();
               args.putString(Frag3Activity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(Frag3Activity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());   
               break;    */
         case 12:
               fragment = new FragActivity();
               args.putString(FragActivity.ITEM_NAME, dataList.get(position)
                           .getItemName());
               args.putInt(FragActivity.IMAGE_RESOURCE_ID, dataList.get(position)
                           .getImgResID());
               break;
         default:
               break;
         }

         fragment.setArguments(args);
         FragmentManager frgManager = getFragmentManager();
         frgManager.beginTransaction().replace(R.id.content_frame, fragment)
                     .commit();

         mDrawerList.setItemChecked(position, true);
         setTitle(dataList.get(position).getItemName());
         mDrawerLayout.closeDrawer(mDrawerList);

   }
		

@Override
public void setTitle(CharSequence title) {
      mPageTitle = title;
      getActionBar().setTitle(mPageTitle);
}
 
@Override
protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
      // Sync the toggle state after onRestoreInstanceState has occurred.
      drawer_toggle.syncState();
}
 public static class FragActivity extends  Fragment  {
		    	
		    	 ImageView ivIcon;
		         TextView tvItemName;
		    
		         public static final String IMAGE_RESOURCE_ID = "iconResourceID";
		         public static final String ITEM_NAME = "itemName";
		    
		         public FragActivity() {
		    
		         }
		    
		         public View onCreateView(LayoutInflater inflater, ViewGroup container,
		                     Bundle savedInstanceState) {
		    
		               View view = inflater.inflate(R.layout.frag1, container,
		                           false);
		    
		               ivIcon = (ImageView) view.findViewById(R.id.frag1_icon);
		               tvItemName = (TextView) view.findViewById(R.id.frag1_text);
		    
		               tvItemName.setText(getArguments().getString(ITEM_NAME));
		               ivIcon.setImageDrawable(view.getResources().getDrawable(
		                           getArguments().getInt(IMAGE_RESOURCE_ID)));
		               return view;
		    	
		    	} 
		    }
			
			@Override
public void onConfigurationChanged(Configuration newConfig) {
      super.onConfigurationChanged(newConfig);
      // Pass any configuration change to the drawer toggles
      drawer_toggle.onConfigurationChanged(newConfig);
}

private class CustomDrawerItemClickListener implements ListView.OnItemClickListener 
{
	@Override
	 public void onItemClick(AdapterView<?> parent, View view, int position, long id)
		{
		selectItem(position);
		}
 }