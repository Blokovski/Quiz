package com.outland.quiz.model;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;

import android.util.Log;

import com.outland.quiz.App;
import com.outland.quiz.R;

public class Util
{
	public static String getStringFromJsonResource()
	{
		InputStream is = App.getContext().getResources().openRawResource(R.raw.nfl);
		Writer writer = new StringWriter();
		char[] buffer = new char[1024];
		try {
		    Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
		    int n;
		    while ((n = reader.read(buffer)) != -1) {
		        writer.write(buffer, 0, n);
		    }
		    
		    is.close();
		}catch(Exception e)
		{
			Log.e("getStringFromJsonResource()", e.toString());
		}
		
		String jsonString = writer.toString();
		return jsonString;
	}
}
