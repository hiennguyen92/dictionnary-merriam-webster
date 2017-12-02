package com.example.dialog;

import java.util.ArrayList;


import com.example.merriam_webster.R;


import android.app.Dialog;

import android.content.Context;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class ResultDialog extends Dialog{

	Context _context;
	public ListView lvResult;
	ArrayList<String> _kq;
	
	public ResultDialog(Context context,ArrayList<String> kq) {
		super(context);
		this._context = context;
		this._kq = kq;
	}
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
		this.setContentView(R.layout.audiosearch);
		lvResult = (ListView)findViewById(R.id.listaudioSearch);
		
		
		lvResult.setAdapter(new ArrayAdapter<String>(_context,R.layout.listview_layout, R.id.TextView01, _kq));
		
		
		lvResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			 
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(_context, "You Clicked at " +_kq.get(+ position), Toast.LENGTH_SHORT).show();

				if( mDialogResult != null ){
	                mDialogResult.finish(_kq.get(+ position));
	            }
				ResultDialog.this.dismiss();  
            }
        });
		
		
		
		
	}
	ResultDialogResult mDialogResult;
	
    public interface ResultDialogResult {
        void finish(String result);
     }

 	public void setDialogResult(ResultDialogResult onMyDialogResult) {
 		mDialogResult = onMyDialogResult;
 	}
	

}
