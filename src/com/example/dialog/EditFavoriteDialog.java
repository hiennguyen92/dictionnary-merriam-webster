package com.example.dialog;

import com.example.Widgets.CustomAdapter;
import com.example.Widgets.ModelListView;
import com.example.Widgets.Util;
import com.example.merriam_webster.Favorites;
import com.example.merriam_webster.R;
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

public class EditFavoriteDialog extends Dialog {

	Context _context;
	Favorites favorites;

	public EditFavoriteDialog(Context context) {
		super(context);
		this._context = context;
		favorites = new Favorites(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.favorite_layout_press);

		ImageButton dialogButtonDelete = (ImageButton) this
				.findViewById(R.id.img_fav_delete);
		ImageButton dialogButtonDeleteAll = (ImageButton) this
				.findViewById(R.id.img_fav_deleteall);
		ImageView dialogButtonCancel = (ImageView) this
				.findViewById(R.id.img_fav_cancel);

		// Mapping Data from database to list view
		final ListView listFavorite = (ListView) this
				.findViewById(id.list_fav_press);
		
		listFavorite.setAdapter(new CustomAdapter(_context, Util.ListStringtoListModel(favorites.listwordsFavorites)));

		listFavorite.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

			}

		});

		// Handing event click Buttons
		dialogButtonDelete.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				for (int i = 0; i < listFavorite.getChildCount(); i++) {
					if( ((ModelListView)listFavorite.getAdapter().getItem(i)).isSelected() ){
						favorites.DeleteWord(((ModelListView)listFavorite.getAdapter().getItem(i)).getName());
					}
				}
				listFavorite.setAdapter(new CustomAdapter(_context, Util.ListStringtoListModel(favorites.listwordsFavorites)));
			}
		});

		dialogButtonDeleteAll.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {			
				favorites.DeleteAll();
				listFavorite.setAdapter(new CustomAdapter(_context, Util.ListStringtoListModel(favorites.listwordsFavorites)));
				
			}
		});

		dialogButtonCancel.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				EditFavoriteDialog.this.dismiss();
			}
		});

	}
}
