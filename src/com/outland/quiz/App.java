package com.outland.quiz;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;



public class App extends Application
{
	
	public static final String GA_CODE = "UA-31703028-1"; //TODO: ubaciti za quiz aplikaciju

	private static Context mContext;
	//private static GetData gd;
	//private static BitmapManager bm;
	// public static GoogleAnalyticsTracker tracker;

	private static final String APP_SHARED_PREFS = "com.diwanee.if_preferences";
	public static SharedPreferences appSharedPrefs;
	public static Editor prefsEditor;

	@Override
	public void onCreate()
	{

		super.onCreate();
		String version = "100";
		try
		{
			version = getPackageManager().getPackageInfo(getPackageName(), 0).versionName;
		} catch (NameNotFoundException e)
		{

		}
		appSharedPrefs = getSharedPreferences(APP_SHARED_PREFS, Activity.MODE_PRIVATE);
		prefsEditor = appSharedPrefs.edit();
		mContext = this;
		//gd = new GetData();
		//bm = new BitmapManager();
		// tracker = GoogleAnalyticsTracker.getInstance();
		// tracker.startNewSession(App.GA_CODE, 20, this);

		// App.tracker.trackEvent(App.APP_NAME + "App", // Category
		// "start", // Action
		// "Android " + version, // Label
		// 0); // Value
	}

	public static Context getContext()
	{
		return mContext;
	}

//	public static GetData getGetData()
//	{
//		return gd;
//	}
//
//	public static BitmapManager getBitmapManager()
//	{
//		return bm;
//	}


}
