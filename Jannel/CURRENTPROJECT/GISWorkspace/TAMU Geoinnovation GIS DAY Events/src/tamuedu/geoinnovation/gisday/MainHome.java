package tamuedu.geoinnovation.gisday;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.*;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;




public class MainHome extends Activity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    private CharSequence mDrawerTitle;
    CustomDrawerAdapter adapter;
    List<Drawer_Item> dataList;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_home);

        
        dataList = new ArrayList<Drawer_Item>();
        mTitle = mDrawerTitle;
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.fragment_navdrawer);
        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
      
   // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
   
        dataList.add(new Drawer_Item("Login/Signup", R.drawable.ic_action_share));
        dataList.add(new Drawer_Item("Event Calendar", R.drawable.ic_action_eventcal));
        dataList.add(new Drawer_Item("Event Details", R.drawable.ic_action_eventdetails));
        dataList.add(new Drawer_Item("Remind me", R.drawable.ic_action_myevents));
        dataList.add(new Drawer_Item("Event Check in", R.drawable.ic_action_checkin));
        dataList.add(new Drawer_Item("My Events", R.drawable.ic_action_myevents));
        dataList.add(new Drawer_Item("Share", R.drawable.ic_action_share));
        
        
        adapter = new CustomDrawerAdapter(this, R.layout.custom_drawer__item,
                dataList);
        mDrawerList.setAdapter(adapter);
    }
    
    
      final ImageButton events = (ImageButton) findViewById(R.id.calendar_button);
  //      events.setOnTouchListener(R.id.calendar_button);
        
   //     onclickListener = (new View.onClickListener(){ 	
   // 		public void onClick(View v) {
   // 			Intent calendar = new Intent(this, Login.class);
    //			startActivity(calendar);
  //  		}
   // 	});
        
   

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        // update the main content by replacing fragments
    
        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, PlaceholderFragment.newInstance(position + 1))
                .commit();
    }

    public void onSectionAttached(int number) {
        
    	Fragment fragment = null;
    	Bundle args = new Bundle();
    	switch (number) {
            case 0:
                mTitle = getString(R.string.title_section1);
                fragment = new FragOne();
                args.putString(FragOne.ITEM_NAME, dataList.get(number)
                            .getItemName());
                args.putInt(FragOne.IMAGE_RESOURCE_ID, dataList.get(number)
                            .getImgResID());
                break;
            case 1:
            	
                mTitle = getString(R.string.title_section2);
                fragment = new FragTwo();
                args.putString(FragTwo.ITEM_NAME, dataList.get(number)
                            .getItemName());
                args.putInt(FragTwo.IMAGE_RESOURCE_ID, dataList.get(number)
                            .getImgResID());
                break;
            case 3:
                mTitle = getString(R.string.title_section3);
                break;
            case 4:
                mTitle = getString(R.string.title_section4);
                break;
            case 5:
                mTitle = getString(R.string.title_section5);
                break;
            case 6:
                mTitle = getString(R.string.title_section6);
                break;
            case 7:
                mTitle = getString(R.string.title_section7);
                break;
            case 8:
                mTitle = getString(R.string.title_section8);
                break;
            case 9:
                mTitle = getString(R.string.title_section9);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_home, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
    	  
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main_home, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(Integer.toString(getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);
            ((MainHome) activity).onSectionAttached(
                    getArguments().getInt(ARG_SECTION_NUMBER));
        }
    }

}
