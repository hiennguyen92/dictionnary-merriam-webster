package com.example.merriam_webster;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;

public class CopyrightActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_copyright);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.copyright, menu);
		return true;
	}

}
