package com.example.dialog;

import com.example.merriam_webster.R;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;

public class VoiceSearchDialog extends Dialog{
	
	Context context;
	
	public VoiceSearchDialog(Context context)
	{
		super(context);
		this.context = context;
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.audiosearch);
		
		// insert your code here
	}
}
