package com.example.merriam_webster;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.R.anim;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.Widgets.AutoCompleteAdapter;
import com.example.Widgets.JsHandler;
import com.example.Widgets.TTS;
import com.example.Widgets.Util;
import com.example.dialog.DailyDialog;
import com.example.dialog.FavoriteDialog;
import com.example.dialog.FavoriteDialog.OnMyDialogResult;
import com.example.dialog.MoreDialog;
import com.example.dialog.RecentDialog;
import com.example.dialog.RecentDialog.RecentDialogResult;
import com.example.dialog.ResultDialog;
import com.example.dialog.ResultDialog.ResultDialogResult;
import com.example.dialog.VoiceSearchDialog;
import com.example.dictionary.CDictionary;


public class MainActivity extends Activity implements TextWatcher{
	final Context context = this;
	private ImageButton button_voice_search;
	private AutoCompleteTextView txtsearch;
	private WebView wvResultSearch;
	private Button btn_recent;
	private Button btn_daily;
	private Button btn_fav;
	private Button btn_more;
	private ProgressDialog mProgressDialog = null;
	private static final int REQUEST_CODE = 1234;
	private TTS MyTTS;
	private CDictionary myDic;

	@SuppressLint({ "SetJavaScriptEnabled" })
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);		
		setContentView(R.layout.activity_main);
		button_voice_search = (ImageButton) findViewById(R.id.imgvoicesearch);
		txtsearch = (AutoCompleteTextView) findViewById(R.id.txtsearch);
		btn_recent = (Button) findViewById(R.id.btn_recent);
		btn_daily = (Button) findViewById(R.id.btn_daily);
		btn_fav = (Button) findViewById(R.id.btn_fav);
		btn_more = (Button) findViewById(R.id.btn_more);
		wvResultSearch = (WebView)findViewById(R.id.ResultSearch);
		wvResultSearch.getSettings().setJavaScriptEnabled(true);
		wvResultSearch.getSettings().setBuiltInZoomControls(true);
		wvResultSearch.loadUrl("file:///android_asset/index.html");
		wvResultSearch.addJavascriptInterface(new JsHandler(this), "MyHandler");
		new MyAsyncTask().execute("");
		MyTTS = new TTS(this);
		
		PackageManager pm = getPackageManager();
		List<ResolveInfo> activities = pm.queryIntentActivities(new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH), 0);
		
		if (activities.size() == 0) {
			button_voice_search.setEnabled(false);
			Toast.makeText(getApplicationContext(), "Recognizer Not Found",Toast.LENGTH_SHORT).show();
		}
		button_voice_search.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startVoiceRecognitionActivity();
			}
		});
		txtsearch.addTextChangedListener(this);
		txtsearch.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				Drawable right = getResources().getDrawable(
						R.drawable.ic_reset_search);
				
				right.setBounds(0, 0, right.getIntrinsicWidth(),
						right.getIntrinsicHeight());
				Drawable left = getResources().getDrawable(
						R.drawable.ic_searchbox);
				left.setBounds(0, 0, left.getIntrinsicWidth(),
						left.getIntrinsicHeight());
				txtsearch.setCompoundDrawables(left, null, right, null);
				if (event.getX() > txtsearch.getWidth()
						- txtsearch.getPaddingRight()
						- right.getIntrinsicWidth()) {
					txtsearch.setText("");
				}				
				return false;
			}
		});
		txtsearch.setDropDownHeight(350);
		txtsearch.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long id) {
				wvResultSearch.setVisibility(View.VISIBLE);
				String input = txtsearch.getText().toString().toLowerCase();
				final String out;
				out = myDic.FindWordByHeadword(input);
				if (out != null) {
					changeTextWord(Util.FormatWebWord(out),Util.FormatWebPronunciation(out));
					changeTextMeaing(Util.FormatWebMeaning(out));
					Recent.AddWord(input, MainActivity.this);
					InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(txtsearch.getWindowToken(), 0);
					if (Favorites.getListwordsFavorites(MainActivity.this).contains(txtsearch.getText().toString())) {
						SetSource("star-on.png");
					}
					else {
						SetSource("star-off.png");
					}
				} else {
					Toast.makeText(MainActivity.this, "Not found",Toast.LENGTH_SHORT).show();
				}
			}
		});
			
		
//		button_voice_search.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View arg0) {
//				// custom dialog
//				//startVoiceRecognitionActivity();
//				final Dialog dialog = new VoiceSearchDialog(context);
//				dialog.show();	
//				
//			}
//		});

		btn_recent.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				final RecentDialog recentDialog = new RecentDialog(context);
				recentDialog.show();
				recentDialog.setRecentDialogResult(new RecentDialogResult() {
					public void finish(String result) {
						txtsearch.setText(result);
					}
				});
			}
		});

		btn_fav.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				FavoriteDialog dialog = new FavoriteDialog(context);
				dialog.show();
				dialog.setDialogResult(new OnMyDialogResult() {				
					public void finish(String result) {
						txtsearch.setText(result);
					}
				});

			}
		});
		
		btn_daily.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Dialog dialog = new DailyDialog(context,myDic.getAdapter(), myDic);
				dialog.show();

			}
		});
				
		btn_more.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Dialog dialog = new MoreDialog(context);
				dialog.show();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void afterTextChanged(Editable s) {
		txtsearch.showDropDown();	
	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,int after) {
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
	}
	
	
	
    public void changeTextWord(String word,String pronun){
        Log.v("mylog","changeText is called");
        wvResultSearch.loadUrl("javascript:document.getElementById('word').innerHTML = '<strong>"+word.replaceAll("@", "")+"</strong>'");
        wvResultSearch.loadUrl("javascript:document.getElementById('pronun').innerHTML = '"+pronun+"'");
    }
    
    public void changeTextMeaing(String someText){
        Log.v("mylog","changeText is called");
        wvResultSearch.loadUrl("javascript:document.getElementById('meaning').innerHTML = '<div>"+someText+"</div>'");
    }
    
    
    public void SetSource(String src){
        Log.v("mylog","changeText is called");
        wvResultSearch.loadUrl("javascript:document.getElementById('start').src ='"+src+"'");
    }
    
    
    
    
    public void JsClickSound(String word) throws IOException {
    	Log.v("mylog",word);
    	MyTTS.speak(word.substring(8, word.length()-9));
	}
    
    
    public void JsClickStart() throws IOException {
    	Log.v("mylog","Start is called");
    	wvResultSearch.loadUrl("javascript:changeSrc()");	
	}
    
    
	public void JsReturnStart(String name,String word) {
		if (name.equals("on")) {
			//Thêm vào yêu thích			
			Favorites.AddWord(word.substring(8, word.length()-9).trim(), MainActivity.this);
		}else {
			//Xoa khoi yêu thích
			Favorites.DeleteWord(word.substring(8, word.length()-9).trim(), MainActivity.this);
		}
	}
	
	
	

    
    
    @Override
    protected void onDestroy() {
    	MyTTS.onDestroy();
    	super.onDestroy();
    }
    
    
	private void startVoiceRecognitionActivity() {
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
		intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
		intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Voice Recognition...");
		startActivityForResult(intent, REQUEST_CODE);
	}
	
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		  if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {
			     ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
			     ResultDialog result = new ResultDialog(MainActivity.this,matches);
			     result.show();
			     result.setDialogResult(new ResultDialogResult() {	
					@Override
					public void finish(String result) {
						txtsearch.setText(result);
					}
				});
			 }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	
	private class MyAsyncTask extends AsyncTask<String, Integer, Integer> {
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(MainActivity.this);
			mProgressDialog.setMessage("Please wait...");
			mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
			mProgressDialog.setMax(100);
			mProgressDialog.show();
			mProgressDialog
					.setOnCancelListener(new DialogInterface.OnCancelListener() {
						@Override
						public void onCancel(DialogInterface dialog) {
							
						}
					});
		}
		@Override
		protected Integer doInBackground(String... params) {
			try {
				myDic = new CDictionary(context);		
			} catch (Exception e) {
			}
			return 0;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(Integer result) {
			super.onPostExecute(result);
			txtsearch.setAdapter(new AutoCompleteAdapter<String>(MainActivity.this, android.R.layout.simple_dropdown_item_1line,myDic.getAdapter()));
			mProgressDialog.dismiss();
		}
	}
	
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{

		if (keyCode == KeyEvent.KEYCODE_BACK)
		{
			new AlertDialog.Builder(this)
					.setIcon(android.R.drawable.ic_dialog_alert)
					.setTitle("Notify")
					.setMessage("Exit Dictionary-G7?")
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener()
							{
								@Override
								public void onClick(DialogInterface dialog,
										int which)
								{
									finish();
								}
							}).setNegativeButton("No", null).show();

			return true;
		}
		else
		{
			return super.onKeyDown(keyCode, event);
		}
	}
	

}
