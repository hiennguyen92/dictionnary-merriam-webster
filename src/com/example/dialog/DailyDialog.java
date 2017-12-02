package com.example.dialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.example.dictionary.CDictionary;
import com.example.merriam_webster.Daily;
import com.example.merriam_webster.R;

public class DailyDialog extends Dialog{
	Context context;
	Daily daily;
	
	
	public DailyDialog(Context context,List<String> listword,CDictionary dic)
	{
		super(context);
		this.context = context;
		daily = new Daily(listword,dic);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.daily_layout);
		
		// Insert your code at here
		TextView tvDate = (TextView)findViewById(R.id.tvdate_daily);
		DateFormat dateFormat = new SimpleDateFormat("MMMMM dd, yyyy");
		tvDate.setText(dateFormat.format(Calendar.getInstance().getTime()));
		
		TextView tvword = (TextView)findViewById(R.id.tvword);
		tvword.setText(daily.getWord());
		TextView tvpronoun = (TextView)findViewById(R.id.tvpronun);
		tvpronoun.setText(daily.getPronoun());
		TextView tvmaining = (TextView)findViewById(R.id.tvmaining);
		tvmaining.setText(daily.getMaining());
	}
}