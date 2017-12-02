package com.example.Widgets;

import java.util.Locale;

import android.content.Context;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import android.util.Log;

public class TTS implements OnInitListener {

	private TextToSpeech speech;

	public TTS(Context mContext) {
		speech = new TextToSpeech(mContext, this);
	}

	@Override
	public void onInit(int status) {
		if (status == TextToSpeech.SUCCESS)

		{

			int result = speech.setLanguage(Locale.US);

			if (result == TextToSpeech.LANG_MISSING_DATA
					|| result == TextToSpeech.LANG_NOT_SUPPORTED) {
				Log.e("TTS", "This Language is not supported");
			}

			else

			{
				//btnspeech.setEnabled(true);
				//speak();
			}

		}

		else

		{
			Log.e("TTS", "Initilization Failed!");
		}

	}

	public void speak(String text) {

		// String text = txtinput.getText().toString();

		speech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
	}

	public void onDestroy() {
		if (speech != null) {
			speech.stop();
			speech.shutdown();
		}
	}
}
