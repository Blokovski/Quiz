package com.outland.quiz;



import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.outland.quiz.model.Rules;
import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;

public class StartActivity extends SwarmActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		Swarm.init(this, 4681, "e58d7d9e924c5f2e246022a403ebb482");
		
		
		Button playBtn = (Button) findViewById(R.id.playBtn);
		Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
		Button scoreBtn = (Button) findViewById(R.id.scoreBtn);
		Button exitBtn = (Button) findViewById(R.id.exitBtn);
		
	}
	
	
	
	public void onClickPlay(View view)
	{
		App.setGame();
		
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
	
	public void onClickScore(View view)
	{
		SwarmLeaderboard.showLeaderboard(Rules.SWARM_LEADERBORD);
	}
	
	public void onClickExit(View view)
	{
		System.exit(0);
	}
		
	public void onClickSettings(View view)
	{
			
		Intent i = new Intent(this, SettingsActivity.class);
		startActivity(i);
	}
}
