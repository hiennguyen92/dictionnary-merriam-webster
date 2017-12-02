package com.example.dictionary;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import com.example.Data.DataUtil;




public class CDictionary {

	List<Word> arrWords;
	public List<Word> getArrWords() {
		return arrWords;
	}



	public void setArrWords(List<Word> arrWords) {
		this.arrWords = arrWords;
	}



	public List<String> getAdapter() {
		return Adapter;
	}



	public void setAdapter(List<String> adapter) {
		Adapter = adapter;
	}



	List<String> Adapter;
	DataUtil dataU;
	
	public CDictionary(Context context) {
		dataU =  new DataUtil(context);
		arrWords = dataU.getListWord();
		Adapter = CreateAdapter();
		
	}
	
	
	
	private List<String> CreateAdapter(){
		List<String> listtemp = new ArrayList<String>();
		for (Word item : arrWords) {
			listtemp.add(item.getHeadword());
		}
		return listtemp;
	}
	
	
	public Word getWordsFromInput(String input) {

		// từ 1 input, mình lấy ra được 1 words
		for (int i = 0; i < arrWords.size(); i++) {
			if (input.equals(arrWords.get(i).getHeadword().toLowerCase())) {
				return arrWords.get(i);
			}
		}
		return null;
	}
	
	
	
	public String FindWordByHeadword(String headword){	
		try {
			return dataU.getMeaning(getWordsFromInput(headword));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	
	
	
	
	
	
	

}



