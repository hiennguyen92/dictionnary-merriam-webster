package com.example.merriam_webster;

import java.util.Calendar;
import java.util.List;


import com.example.dictionary.CDictionary;

public class Daily {
	
	private String daily;
	private String[] arrStr;
	
	public String getWord() {
		return arrStr[0].split(" ")[0].replace("@", "");
	}
	
	public String getPronoun() {
		String[] arr = arrStr[0].split(" ");
		if (arr.length >=2) {
			return arrStr[0].split(" ")[1].replace("'", "`");
		}else {
			return null;
		}
		
	}

	public String getMaining() {
		return (String) daily.subSequence(daily.indexOf("\n"), daily.length());
	}

	public Daily(List<String> listword,CDictionary dic) {
		
		//randomword
		Calendar cal = Calendar.getInstance();
		@SuppressWarnings("deprecation")
		int temp = (cal.getTime().getYear() + cal.getTime().getMonth() + cal.getTime().getDay());
		if (temp <= listword.size()) {
			//trong size
			daily = dic.FindWordByHeadword(listword.get(temp-1));
		}
		else {
			temp = temp - listword.size();
			daily = dic.FindWordByHeadword(listword.get(temp-1));
		}
		arrStr = daily.split("\n");
	}
	
	
	
	
}
