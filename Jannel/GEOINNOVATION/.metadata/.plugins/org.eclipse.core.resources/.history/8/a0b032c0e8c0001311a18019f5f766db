package edu.tamu.gisdayevents;


import java.util.ArrayList;
import android.app.Activity;
import android.app.ActionBar;
import android.app.ActivityManager;
import android.app.Fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;



public class NavigationDrawerFragment extends ListView
{
	private ActionBarDrawerToggle drawerToggle;
	private ListView lvDrawer;
	private ArrayAdapter<String> drawerAdapter;
	private ArrayList<FragmentNavItem> drawerNavItems;
	private int drawerContainerRes;
		 
		public NavigationDrawerFragment(Context context, AttributeSet attrs, int defStyle) 
		{
			super(context, attrs, defStyle);
		}
		 
		public NavigationDrawerFragment(Context context, AttributeSet attrs) 
		{
			super(context, attrs);
		}
		 
		public NavigationDrawerFragment(Context context) 
		{
			super(context);
		}
		// setupDrawerConfiguration((ListView) findViewById(R.id.lvDrawer), R.layout.menu_list_item, R.id.flContent);
		public void setupDrawerConfiguration(ListView drawerListView, int drawerItemRes, int drawerContainerRes) 
		{
		// Setup navigation items array
			drawerNavItems = new ArrayList<NavigationDrawerFragment.FragmentNavItem>();
		// Set the adapter for the list view
			drawerAdapter = new ArrayAdapter<String>(getActivity(), drawerItemRes, new ArrayList<String>());
			this.drawerContainerRes = drawerContainerRes;
		// Setup drawer list view and related adapter
			lvDrawer = drawerListView;
			lvDrawer.setAdapter(drawerAdapter);
		// Setup item listener
			lvDrawer.setOnItemClickListener(new FragmentDrawerItemListener());
		// ActionBarDrawerToggle ties together the the proper interactions
		// between the sliding drawer and the action bar app icon
			drawerToggle = setupDrawerToggle();
			setDrawerListener(drawerToggle);
		// set a custom shadow that overlays the main content when the drawer
			setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
		// Setup action buttons
			getActionBar().setDisplayHomeAsUpEnabled(true);
			getActionBar().setHomeButtonEnabled(true);
			
			
		}
		 
		// addNavItem("First", "First Fragment", FirstFragment.class)
		public void addNavItem(String navTitle, String windowTitle, Class<? extends Fragment> fragmentClass) 
		{
			drawerAdapter.add(navTitle);
			drawerNavItems.add(new FragmentNavItem(windowTitle, fragmentClass));
		}
		/** Swaps fragments in the main content view */
		public void selectDrawerItem(int position) 
		{
		// Create a new fragment and specify the planet to show based on
		// position
			FragmentNavItem navItem = drawerNavItems.get(position);
			Fragment frag=null;
			try 
			{
				frag = navItem.getFragmentClass().newInstance();
				Bundle args = navItem.getFragmentArgs();
				if (args != null)
				{
					frag.setArguments(args);
				}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		 
		// Insert the fragment by replacing any existing fragment

			FragmentTransaction transaction = getFragmentManager().beginTransaction();
	        transaction.replace(R.id.flContent, frag);
	        transaction.addToBackStack(null);
	        transaction.commit();
		
		 
		// Highlight the selected item, update the title, and close the drawer
			lvDrawer.setItemChecked(position, true);
			setTitle(navItem.getTitle());
			closeDrawer(lvDrawer);
		}
		 
		public ActionBarDrawerToggle getDrawerToggle() 
		{
			return drawerToggle;
		}
		 
		private FragmentActivity getActivity() 
		{
			return (FragmentActivity) getContext();
		}
		 
		private ActionBar getActionBar() 
		{
			return getActivity().getActionBar();
		}
		 
		private void setTitle(CharSequence title) 
		{
			getActionBar().setTitle(title);
		}
		
		
		private class FragmentDrawerItemListener implements ListView.OnItemClickListener 
		{	
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
			{
				selectDrawerItem(position);
			}	
		}
		private class FragmentNavItem 
		{
			private Class<? extends Fragment> fragmentClass;
			private String title;
			private Bundle fragmentArgs;
			public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass) 
			{
				this(title, fragmentClass, null);
			}
			
			public FragmentNavItem(String title, Class<? extends Fragment> fragmentClass, Bundle args) 
			{
			this.fragmentClass = fragmentClass;
			this.fragmentArgs = args;
			this.title = title;
			}
		
			public Class<? extends Fragment> getFragmentClass() 
			{
				return fragmentClass;
			}
			public String getTitle() 
			{
				return title;
			}
			public Bundle getFragmentArgs() 
			{
				return fragmentArgs;
			}
		}
		public boolean isDrawerOpen() 
		{
		return isDrawerOpen(lvDrawer);
		}
		
		
}
