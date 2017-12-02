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

import com.example.merriam_webster.Favorites;
import com.example.merriam_webster.R;
import com.example.merriam_webster.R.id;

public class FavoriteDialog extends Dialog {

	OnMyDialogResult mDialogResult; // the callback
	Context context;
	Favorites favorites;
	ListView listFavorite;

	public FavoriteDialog(Context context) {
		super(context);
		this.context = context;
		favorites = new Favorites(context);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.favorite_layout);

		ImageButton dialogButton = (ImageButton) this
				.findViewById(R.id.img_edit_fav);

		
		ArrayList<String> listFavorites = new ArrayList<String>();
		listFavorites.addAll(favorites.listwordsFavorites);
		
		// Mapping Data from database to list view
		listFavorite = (ListView)findViewById(id.listFavorite);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
				R.layout.listview_layout, R.id.TextView01, listFavorites);
		listFavorite.setAdapter(adapter);

		listFavorite.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				String itemValue = listFavorite.getItemAtPosition(position).toString();
				
				if( mDialogResult != null ){
	                mDialogResult.finish(itemValue);
	            }
				FavoriteDialog.this.dismiss();
			}

		});

		// if button is clicked, close the custom dialog
		dialogButton.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final Dialog dialog1 = new EditFavoriteDialog(context);
				dialog1.show();
				FavoriteDialog.this.dismiss();
			}
		});

	}
	


    public interface OnMyDialogResult {
       void finish(String result);
    }

	public void setDialogResult(OnMyDialogResult onMyDialogResult) {
		mDialogResult = onMyDialogResult;
	}
    
}
