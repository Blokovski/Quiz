package com.outland.quiz;



import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;

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
}
