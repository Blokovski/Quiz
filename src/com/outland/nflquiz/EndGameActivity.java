package com.outland.nflquiz;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.analytics.tracking.android.EasyTracker;
import com.outland.nflquiz.model.Game;
import com.outland.nflquiz.model.Rules;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;

public class EndGameActivity extends SwarmActivity
{
	TextView tvTrue;
	TextView tvFalse;
	TextView tvScore;
	Game game;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_endgame);
				
		tvScore = (TextView) findViewById(R.id.tvEndScore);
		tvTrue = (TextView) findViewById(R.id.tvEndTrueAnswers);
		game = App.getGame();
		setResults();
	}
	
	private void setResults()
	{
		tvScore.setText(String.valueOf(game.getScore()));
		tvTrue.setText(String.valueOf(game.getTrueAnswers()));	
	}
	
	public void onClickSubmit(View view)
	{
		SwarmLeaderboard.submitScoreAndShowLeaderboard(Rules.SWARM_LEADERBORD, game.getScore());
	}
	
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
	
	public void onClickPlayAgain(View view)
	{
		App.setGame();
		Intent i = new Intent(this, MainActivity.class);
		startActivity(i);
		finish();
	}
}
