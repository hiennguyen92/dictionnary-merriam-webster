package com.example.Widgets;

import java.io.IOException;

import com.example.merriam_webster.MainActivity;

import android.webkit.JavascriptInterface;


public class JsHandler {

	
	MainActivity activity;
	
	
	public JsHandler(MainActivity mainActivity) {
		activity = mainActivity;
	}

	@JavascriptInterface
	public void ClickSound(String word) throws IOException {
		this.activity.JsClickSound(word);
	}
	//ClickStart
	
	@JavascriptInterface
	public void ClickStart( ) throws IOException {
		this.activity.JsClickStart();
	}
	//ReturnActionStart
	@JavascriptInterface
	public void ReturnActionStart(String name,String word) throws IOException {
		this.activity.JsReturnStart(name,word);
	}
	
	
}
