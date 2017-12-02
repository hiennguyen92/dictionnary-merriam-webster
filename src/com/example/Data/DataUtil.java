package com.example.Data;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import com.example.dictionary.Word;

public class DataUtil {

	public File wfile;
	public File mfile;
	public RandomAccessFile mRandomAccessFile;
	private Context _context;
	private Boolean isSDPresent;

	public DataUtil(Context context) {

		_context = context;
		isSDPresent = android.os.Environment.getExternalStorageState().equals(
				android.os.Environment.MEDIA_MOUNTED);
		copyAssets(context);

	}

	public String ReadFile() {
		if (isSDPresent) {
			wfile = new File(_context.getExternalFilesDir(null) + "/EV.index");
		} else {
			wfile = new File(_context.getFilesDir() + "/EV.index");
		}
		String content = null;
		try {
			FileInputStream fis = new FileInputStream(wfile);
			byte[] data = new byte[(int) wfile.length()];
			fis.read(data);

			fis.close();
			fis = null;
			content = new String(data, "UTF-8");
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}

	public List<Word> getListWord() {
		List<Word> wordss = new ArrayList<Word>();
		try {
			String data = ReadFile();
			StringTokenizer token = new StringTokenizer(data, "\n");
			while (token.hasMoreTokens()) {
				String line = token.nextToken();
				String elements[] = line.split("\t");
				if (elements.length == 3) {
					Word index = new Word();
					index.setHeadword(elements[0]);
					index.setOffset(elements[1]);
					index.setLegnth(elements[2]);
					wordss.add(index);
					index = null;
				}
				elements = null;
			}
			token = null;
			return wordss;

		} catch (Exception e) {
			e.printStackTrace();
		}
		return wordss;
	}

	public String getMeaning(Word word) throws Exception {
		int offset = convert64to10(word.getOffset());
		int lengths = convert64to10(word.getLength());

		if (isSDPresent) {
			mfile = new File(_context.getExternalFilesDir(null) + "/EV.dict");
		} else {
			mfile = new File(_context.getFilesDir() + "/EV.dict");
		}

		try {
			mRandomAccessFile = new RandomAccessFile(mfile, "r");
			mRandomAccessFile.seek(offset);
			byte[] dst = new byte[lengths];
			mRandomAccessFile.read(dst, 0, lengths);
			return new String(dst);
		} catch (Exception exc) {
		}
		return null;
	}

	public String SearchIndex(int index, int length) {

		try {
			mRandomAccessFile = new RandomAccessFile(mfile, "r");
			byte[] buff = new byte[length];
			mRandomAccessFile.seek(index);
			mRandomAccessFile.read(buff, 0, length);
			String mean = new String(buff, "UTF8").replaceAll("\0+", "");
			return mean;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	public Word GetWordByString(String word) {
		return null;
	}

	public static void SaveWord(Word word) {
	}

	public static int convert64to10(String num) {
		int number = 0;
		int len = num.length();
		String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		for (int i = 0; i < len; i++) {
			number += code.indexOf(num.charAt(i)) * Math.pow(64, len - i - 1);
		}
		return number;
	}

	public static String convert10to64(int num) {
		String number = "";
		String code = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/";
		while (num != 0) {
			number = code.charAt(num % 64) + number;
			num = num / 64;
		}
		return number;
	}

	public void copyAssets(Context context) {
		AssetManager assetManager = context.getAssets();
		String[] filedata = null;
		try {
			filedata = assetManager.list("data");

		} catch (IOException e) {
			Log.e("tag", "Failed to get asset file list.", e);
		}
		for (String filename : filedata) {
			InputStream in = null;
			OutputStream out = null;
			try {
				in = assetManager.open("data/" + filename);
				File outFile;
				if (isSDPresent) {
					outFile = new File(context.getExternalFilesDir(null),
							filename);
				} else {
					outFile = new File(context.getFilesDir(), filename);
				}
				if (!outFile.exists()) {
					out = new FileOutputStream(outFile);
					copyFile(in, out);
					out.flush();
					out.close();
					out = null;
				}
				in.close();
				in = null;

			} catch (IOException e) {
				Log.e("tag", "Failed to copy asset file: " + filename, e);
			}
		}
	}

	private void copyFile(InputStream in, OutputStream out) throws IOException {
		byte[] buffer = new byte[1024];
		int read;
		while ((read = in.read(buffer)) != -1) {
			out.write(buffer, 0, read);
		}
	}

}
