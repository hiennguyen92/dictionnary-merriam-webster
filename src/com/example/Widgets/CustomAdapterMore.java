package com.example.Widgets;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapterMore extends ArrayAdapter<String> {

	
	
	private final Context context;
	private final String[] web;
	private final Integer[] imageId;
	public CustomAdapterMore(Context context,String[] web, Integer[] imageId) {
		super(context, com.example.merriam_webster.R.layout.list_single, web);
		this.context = context;
		this.web = web;
		this.imageId = imageId;
	}
	@Override
	public View getView(int position, View view, ViewGroup parent) {
		LayoutInflater inflater = ((Activity) context).getLayoutInflater();
		View rowView= inflater.inflate(com.example.merriam_webster.R.layout.list_single, null, true);
		TextView txtTitle = (TextView) rowView.findViewById(com.example.merriam_webster.R.id.txt);
		 
		ImageView imageView = (ImageView) rowView.findViewById(com.example.merriam_webster.R.id.img);
		txtTitle.setText(web[position]);
		 
		imageView.setImageResource(imageId[position]);
		return rowView;
	}
	
}
