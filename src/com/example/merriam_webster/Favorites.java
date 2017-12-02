package com.example.merriam_webster;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;

import com.example.Widgets.Util;

public class Favorites {
	Context _context;
	public List<String> listwordsFavorites = new ArrayList<String>();
	
	
	
	
	public static List<String> getListwordsFavorites(Context context) {
		return new ArrayList<String>(Util.restoringPreferences(context,"Favorites"));
	}


	public Favorites(Context context) {
		_context = context;
		this.listwordsFavorites.addAll(Util.restoringPreferences(_context,"Favorites"));
	}
	
	
	public static void AddWord(String word,Context context) {
		Set<String> listset = Util.restoringPreferences(context,"Favorites");
		listset.add(word);
		Util.savingPreferences(listset,context,"Favorites");
	}
	
	
	
	public static void DeleteWord(String word,Context context)
	{
		Set<String> listset = Util.restoringPreferences(context,"Favorites");
		listset.remove(word);
		Util.savingPreferences(listset,context,"Favorites");
		
		
	}
	
	public void DeleteWord(String word) {
		this.listwordsFavorites.remove(word);
		Util.savingPreferences(new HashSet<String>(this.listwordsFavorites),_context,"Favorites");
	}
	
	public void DeleteAll() {
		listwordsFavorites = new ArrayList<String>();
		Util.savingPreferences(new HashSet<String>(),_context,"Favorites");
	}

//	public List<String> getListFav() {
//		return listFavorite;
//	}
//
//	public void setListFav(List<String> listFav) {
//		this.listFavorite = listFav;
//	}
//
//	public Favorites() {
//		ReadFile();
//	}
//
//	public void AddWord(Word word) {
//		listFavorite.add(word.getHeadword());
//	}
//
//	public void DeleteWord(Word word) {
//		for (int i = 0; i < listFavorite.size(); i++) {
//			if (listFavorite.get(i) == word.getHeadword())
//				listFavorite.remove(i);
//		}
//	}
//
//	public void DeleteAll() {
//		listFavorite.clear();
//	}
//	
//	public void ReadFile()
//	{
//		File sdcard = Environment.getExternalStorageDirectory();
//
//		try {
//
//			File file = new File(sdcard, "favorites.txt");
//			FileInputStream fis = new FileInputStream(file);
//			BufferedReader reader = new BufferedReader(new InputStreamReader(
//					fis));
//
//			String str = "";
//			str = reader.readLine();
//			while (str != null) {
//				listFavorite.add(str);
//				str = reader.readLine();
//			}
//
//			reader.close();
//			fis.close();
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
//	
//	public void WriteFile()
//	{
//		File sdcard = Environment.getExternalStorageDirectory();
//		
//		try {
//			File file = new File(sdcard, "favorites.txt");
//			FileOutputStream fos = new FileOutputStream(file);
//			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
//					fos));
//
//			for(int i = 0; i < listFavorite.size(); i++)
//			{
//				writer.write(listFavorite.get(i));
//				writer.write("/n");
//			}
//			
//			// commit
//			writer.flush();
//
//			writer.close();
//			fos.close();
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//	}

}
