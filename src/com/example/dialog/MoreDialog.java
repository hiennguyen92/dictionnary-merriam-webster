package com.example.dialog;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.Widgets.CustomAdapterMore;
import com.example.merriam_webster.AboutActivity;
import com.example.merriam_webster.CopyrightActivity;
import com.example.merriam_webster.MainActivity;
import com.example.merriam_webster.R;
import com.example.merriam_webster.RecommendedActivity;

public class MoreDialog extends Dialog{
	Context context;
	ListView lvMore;
	
	String[] items = {"Feedback","Rate this App","Share this App","About Apps","Recommended Apps","Copyrights"};
	Integer[] imageId = {
            R.drawable.feedback,
            R.drawable.rate_this_app,
            R.drawable.share_this_app,
            R.drawable.info,
            R.drawable.recommend,
            R.drawable.copyright

    };
	
	public MoreDialog(Context context)
	{
		super(context);
		this.context = context;
	}	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.more_layout);
		lvMore = (ListView)findViewById(R.id.list_more);
		
		
		CustomAdapterMore adapter = new CustomAdapterMore(context, items, imageId);
		
		
		lvMore.setAdapter(adapter);
		
		
		lvMore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                //Toast.makeText(context, "You Clicked at " +items[+ position] + position, Toast.LENGTH_SHORT).show();

                switch (position) {
				case 0:	//Feedback					
					Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
					emailIntent.setType("plain/text");
					emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"abc@gmail.com"});
					emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject");
					emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Text");					
					context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));				
					break;
				case 1:	//Rate
					
					Uri uri = Uri.parse("market://search?q=Merriam-Webster");
					Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
					try {
					  context.startActivity(goToMarket);
					} catch (ActivityNotFoundException e) {
					  Toast.makeText(context, "Couldn't launch the market", Toast.LENGTH_LONG).show();
					}
					
					
					break;
				case 2:	//Share
					
					
					Intent share = new Intent(Intent.ACTION_SEND);
					share.setType("text/plain");
					share.putExtra(Intent.EXTRA_TEXT, "Merriam-Webster");
					context.startActivity(Intent.createChooser(share, "Share Text"));
					break;
				case 3:	//About
					Intent About = new Intent(context,AboutActivity.class);
					context.startActivity(About);
					break;
				case 4:	//recomment apps
					Intent Recommend = new Intent(context,RecommendedActivity.class);
					context.startActivity(Recommend);
					break;
				case 5:	//Copyright
					Intent Copyright = new Intent(context,CopyrightActivity.class);
					context.startActivity(Copyright);
					break;

				}
                
            }
        });
		
		
		
		
	}
	
	
	
	
	
}
