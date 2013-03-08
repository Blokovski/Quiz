package com.outland.quiz;

import com.outland.quiz.model.Game;
import com.swarmconnect.SwarmActivity;
import com.swarmconnect.SwarmLeaderboard;


import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

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
		tvFalse = (TextView) findViewById(R.id.tvEndFalseAnswers);
		tvTrue = (TextView) findViewById(R.id.tvEndTrueAnswers);
		game = App.getGame();
		setResults();
	}
	
	private void setResults()
	{
		tvScore.setText(String.valueOf(game.getScore()));
		tvTrue.setText(String.valueOf(game.getTrueAnswers()));
		tvFalse.setText(String.valueOf(game.getFalseAnswers()));
		SwarmLeaderboard.submitScoreAndShowLeaderboard(6897, game.getScore());
	}
}
