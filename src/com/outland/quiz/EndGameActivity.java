package com.outland.quiz;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.outland.quiz.model.Game;
import com.outland.quiz.model.Rules;
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
}
