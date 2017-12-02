package com.example.dialog;


import com.example.Widgets.CustomAdapter;
import com.example.Widgets.ModelListView;
import com.example.Widgets.Util;
import com.example.merriam_webster.R;
import com.example.merriam_webster.Recent;
import com.example.merriam_webster.R.id;


import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class EditRecentDialog extends Dialog {

	Context _context;
	Recent recent;
	
	public EditRecentDialog(Context context) {
		super(context);
		_context = context;
		recent = new Recent(context);
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.recent_layout_press);

		ImageButton dialogButtonDelete = (ImageButton) this
				.findViewById(R.id.img_recent_delete);
		ImageButton dialogButtonDeleteAll = (ImageButton) this
				.findViewById(R.id.img_recent_deleteall);
		ImageView dialogButtonCancel = (ImageView) this
				.findViewById(R.id.img_recent_cancel);

		// Mapping Data from database to list view
		final ListView listRecent = (ListView) this
				.findViewById(id.list_recent_press);

		listRecent.setAdapter(new CustomAdapter(_context, Util.ListStringtoListModel(recent.listwordsrecent)));

		listRecent.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}

		});

		// Handing event click Buttons
		dialogButtonDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < listRecent.getChildCount(); i++) {
					if( ((ModelListView)listRecent.getAdapter().getItem(i)).isSelected() ){
						recent.DeleteWord(((ModelListView)listRecent.getAdapter().getItem(i)).getName());
					}
				}
				listRecent.setAdapter(new CustomAdapter(_context, Util.ListStringtoListModel(recent.listwordsrecent)));
			}
		});

		dialogButtonDeleteAll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {			
				recent.DeleteAll();
				listRecent.setAdapter(new CustomAdapter(_context, Util.ListStringtoListModel(recent.listwordsrecent)));
				
			}
		});

		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditRecentDialog.this.dismiss();
			}
		});

		
		
	}

}
