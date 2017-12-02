package com.example.Widgets;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import android.content.Context;
import android.content.SharedPreferences;


public class Util {
	public static String FormatWebMeaning(String src) {
		String[] arrOut = src.split("\n");
		String out = "";
		for (int i = 1; i < arrOut.length; i++) {
			if (arrOut[i].startsWith("@")) {
				arrOut[i] = "<b>"+arrOut[i]+"</b>";
			}
			if( arrOut[i].startsWith("*")){
				arrOut[i] = "<b>"+arrOut[i]+"</b>";
			}
			out = out + arrOut[i]+"<br>";
		}
		return out.replaceAll("'", "`");
	}
	public static String FormatWebWord(String src) {
		if(src.split("\n")[0].contains("/"))
			return src.split("\n")[0].split("/")[0].replaceAll("'", "`");
		else return src.split("\n")[0].replaceAll("'", "`");
	}
	
	//pronunciation
	public static String FormatWebPronunciation(String src) {
		if(src.split("\n")[0].split("/").length >= 2)
			return  src.split("\n")[0].split("/")[1].replace("'", "`");
		else return null;
	}
	
	
	public static List<ModelListView> ListStringtoListModel(List<String> list) {
		List<ModelListView> temp = new ArrayList<ModelListView>();
		for (String item : list) {
			temp.add(new ModelListView(item));
		}
		return temp;
	}
	
	
	
	
	
	public static void savingPreferences(Set<String> recent, Context context, String name) {
		SharedPreferences pre = context.getSharedPreferences(name,
				Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = pre.edit();
		editor.clear();
		editor.putStringSet(name, recent);
		editor.commit();
	}
	
	
	
	public static Set<String> restoringPreferences(Context context, String name) {
		SharedPreferences pre = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		return pre.getStringSet(name,new HashSet<String>());
	}
	
	
	
	public static boolean CheckExist(List<String> str,String word) {
		
		for (String s : str) {
			if (s.equals(word)) {
				return true;
			}
		}
		return false;	
	}
	

	
    public static void init(InputStream in, File tempFile) throws IOException {  
    	   
        tempFile = File.createTempFile("tempFile", ".tmp");  
        tempFile.deleteOnExit();  
   
        FileOutputStream fout = null;  
   
        try {  
   
            fout = new FileOutputStream(tempFile);  
            int c;  
   
            while ((c = in.read()) != -1) {  
                fout.write(c);  
            }  
   
        }finally {  
            if (in != null) {  
                in.close();  
            }  
            if (fout != null) {  
                fout.close();  
            }  
        }  
    } 
	
	
}
