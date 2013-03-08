package com.outland.quiz;



import com.swarmconnect.Swarm;
import com.swarmconnect.SwarmActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

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
		Button rulesBtn = (Button) findViewById(R.id.rulesBtn);
		rulesBtn.setOnClickListener(new OnClickListener() {
		    public void onClick(View v) {
		        Swarm.showDashboard();
		    }
		});
		Button exitBtn = (Button) findViewById(R.id.exitBtn);

	}
	
	public void onClickPlay(View view)
	{
		App.setGame();
		
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
	}
}
