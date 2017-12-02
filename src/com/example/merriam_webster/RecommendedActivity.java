package com.example.merriam_webster;

import com.example.Widgets.CustomAdapterMore;

import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.view.Menu;
import android.view.Window;
import android.widget.ArrayAdapter;

public class RecommendedActivity extends ListActivity {

	 String[] Apps = {"Mariam-Webster", "Mariam-Webster 1", "Mariam-Webster 2"
			 };
	Integer[] imageId = {
            R.drawable.premium_icon,
            R.drawable.premium_icon,
            R.drawable.premium_icon,
    };	 
			    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        CustomAdapterMore adapter = new CustomAdapterMore(this, Apps, imageId);
        setListAdapter(adapter);
        
        
    }
    
    
    
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.recommended, menu);
		return true;
	}

}
