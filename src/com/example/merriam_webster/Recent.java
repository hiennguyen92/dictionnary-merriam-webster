package com.example.merriam_webster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;

import com.example.Widgets.Util;

public class Recent {
	Context _context;
	public List<String> listwordsrecent = new ArrayList<String>();
	
	
	public Recent(Context context) {
		_context = context;
		this.listwordsrecent.addAll(Util.restoringPreferences(_context,"Recent"));
	}
	
	
	public static void AddWord(String word,Context context) {
		Set<String> listset = Util.restoringPreferences(context,"Recent");
		listset.add(word);
		Util.savingPreferences(listset,context,"Recent");
	}
	
	public void DeleteWord(String word)
	{
		this.listwordsrecent.remove(word);
		Util.savingPreferences(new HashSet<String>(this.listwordsrecent),_context,"Recent");
	}
	
	public void DeleteAll() {
		listwordsrecent = new ArrayList<String>();
		Util.savingPreferences(new HashSet<String>(),_context,"Recent");
	}
	

	

	

	

	
	
}
