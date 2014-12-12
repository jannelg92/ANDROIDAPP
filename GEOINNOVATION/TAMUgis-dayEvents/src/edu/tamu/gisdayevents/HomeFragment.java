package edu.tamu.gisdayevents;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.Log; 
import android.annotation.SuppressLint;


public class HomeFragment extends Fragment {
     
    public HomeFragment(){}
     
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
  
        View rootView = inflater.inflate(R.layout.f_main_login, container, false);
          
        return rootView;
        
	    
	    
    }
  

  /*  public static Fragment newInstance(Context context) {
		HomeFragment f = new HomeFragment();
        return f;
    }
	*/
}