package com.outland.nflquiz;



import com.google.analytics.tracking.android.EasyTracker;

import android.os.Bundle;
import android.preference.PreferenceActivity;

public class SettingsActivity extends PreferenceActivity
{
	
	
	@SuppressWarnings("deprecation")
	@Override
    protected void onCreate(final Bundle savedInstanceState)
    {
		
		super.onCreate(savedInstanceState);
        
        //getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();
		addPreferencesFromResource(R.xml.prefs);
    }

//    public static class MyPreferenceFragment extends PreferenceFragment
//    {
//        @Override
//        public void onCreate(final Bundle savedInstanceState)
//        {
//            super.onCreate(savedInstanceState);
//            addPreferencesFromResource(R.xml.prefs);
//        }
//    }
	
	@Override
	public void onStart()
	{
		super.onStart();
		EasyTracker.getInstance().activityStart(this);
	}

	@Override
	public void onStop()
	{
		super.onStop();
		EasyTracker.getInstance().activityStop(this);
	}
}
