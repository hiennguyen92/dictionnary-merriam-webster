package com.example.dialog;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.merriam_webster.R;
import com.example.merriam_webster.Recent;

public class RecentDialog extends Dialog{
	
	RecentDialogResult mDialogResult;
	Context context;
	static Recent recent;
	ListView lvRecent;
	
	public RecentDialog(final Context context)
	{
		super(context);
		this.context = context;
		recent = new Recent(context);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.recent_layout);

		
		ArrayList<String> listRecent = new ArrayList<String>();
		listRecent.addAll(recent.listwordsrecent);
		
		lvRecent = (ListView)findViewById(R.id.list_recent);
		lvRecent.setAdapter(new ArrayAdapter<String>(context, R.layout.listview_layout, R.id.TextView01, listRecent));
		
		lvRecent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String itemValue = lvRecent.getItemAtPosition(position).toString();
				
				
				if( mDialogResult != null ){
	                mDialogResult.finish(itemValue);
	            }
				RecentDialog.this.dismiss();				
			}
		});
		
		
		
		ImageButton dialogButton = (ImageButton) this.findViewById(R.id.img_edit_recent);
		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				final Dialog dialog1 = new EditRecentDialog(context);
				dialog1.show();
				RecentDialog.this.dismiss();
			}
		});
	}
	
	
	
    public interface RecentDialogResult {
        void finish(String result);
     }

 	public void setRecentDialogResult(RecentDialogResult onMyDialogResult) {
 		mDialogResult = onMyDialogResult;
 	}
	
}
