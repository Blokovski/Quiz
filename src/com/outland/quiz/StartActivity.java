package com.outland.quiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends Activity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		Button playBtn = (Button) findViewById(R.id.playBtn);
		Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
		Button rulesBtn = (Button) findViewById(R.id.rulesBtn);
		Button exitBtn = (Button) findViewById(R.id.exitBtn);

	}
	
	public void onClickPlay(View view)
	{
		App.setGame();
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}
