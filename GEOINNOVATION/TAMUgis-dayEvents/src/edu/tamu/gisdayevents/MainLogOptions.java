package edu.tamu.gisdayevents;

import android.annotation.SuppressLint;
import android.app.Activity;
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
 

public class MainLogOptions extends Fragment {
	
	private View rootView;
	
	
    public void f_main_log_options(){	
    }
    
  
   
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	View rootView = inflater.inflate(R.layout.f_main_log_options, container, false);
        return rootView;
        
    }
    public void onActivityCreated(Bundle savedInstanceState) {  
    	super.onCreate(savedInstanceState); 
    	

        Button signupBtn = (Button) rootView.findViewById(R.id.create_login_b);
        Button loginBtn = (Button) rootView.findViewById(R.id.login_b);
    }
   
}


