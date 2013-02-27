package com.outland.quiz;

import java.util.List;
import com.outland.quiz.model.Game;
import com.outland.quiz.model.Question;
import com.outland.quiz.model.Rules;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity
{
	
	TextView tvQuestion;
	TextView tvAnswers;
	TextView tvTimer;
	TextView tvScore;
	Game game;
	QuizTimer timer;
	long remainTime;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		tvAnswers = (TextView) findViewById(R.id.tvAnsvers);
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		tvScore = (TextView) findViewById(R.id.tvScore);
		
		game = App.getGame();
		setQuestion(game.getActualQuestion());
		
	  
		timer = new QuizTimer(Rules.TIME, 1000)
		{
			
			@Override
			public void onTick(long time)
			{
				 tvTimer.setText("seconds remaining: " + time / 1000);
				 remainTime = time;
				
			}
			
			@Override
			public void onFinish()
			{
				tvTimer.setText("done!");
				gameOver();
				
			}
		};
		timer.start();
		
		  
	}
	
	private void setQuestion(Question q)
	{
		if (q != null)
		{
			tvQuestion.setText(q.getQuestion());
			List<String> ans = q.getAnswers();
			String answers = "";
			for (int i = 0; i < ans.size(); i++)
			{
				int brojac = i + 1;
				answers += brojac + ".: " + ans.get(i) + "\r\n";
				tvAnswers.setText(answers);
			}
		}
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}


	
	public void gameOver()
	{
		this.finish();
		Intent i = new Intent(this, EndGameActivity.class);
		startActivity(i);
	}
	
	public void onClickAnswer(View view)
	{
		Button btn = (Button)view;
		//Toast.makeText(App.getContext(), btn.getText(), Toast.LENGTH_SHORT).show();
		if (game.getActualQuestion().getCorectAnswer().equals(btn.getText()))
		{
			correctAnswer();
			timer.addTimeToCountDown(5000);
			
		}
		else
		{
			wrongAnswer();
		}
	}
	
	private void correctAnswer()
	{
		Toast.makeText(App.getContext(), "Tacan odgovor", Toast.LENGTH_SHORT).show();
		game.addScore();
		updateScore();
		game.setQuestionPosition(true);
		game.incNumberOfAnsweredQuestion();
		game.incTrueAnswers();
		//setQuestion(game.getActualQuestion());
		endCheck();
	}
	
	private void endCheck()
	{
		if (game.getNumberOfAnsweredQuestions() < game.getNumberOfQuestion())
		{
			setQuestion(game.getActualQuestion());
		}
		else
		{
			gameOver();
		}
	}
	
	private void wrongAnswer()
	{
		//Toast.makeText(App.getContext(), "Hajde ponovo", Toast.LENGTH_SHORT).show();
//		game.setQuestionPosition(true);
//		game.incNumberOfAnsweredQuestion();
//		game.incFalseAnswers();
//		endCheck();
		gameOver();
	}
	
	private void updateScore()
	{
		tvScore.setText(String.valueOf(game.getScore()));
	}
	
	public void onClickHelpAddTime(View view)
	{
		if (game.isHelpMoreTime())
		{
			timer.addTimeToCountDown(Rules.ADDITIONAL_TIME_ADD);
			game.setHelpMoreTime(false);
		}
	}
	
	public void onClickHelpHalf(View view)
	{
		if (game.isHelpHalf())
		{
			
		}
	}
	
	public void onClickHelpSkip(View view)
	{
		if (game.isHelpSkip())
		{
			game.setQuestionPosition(true);
			game.incNumberOfAnsweredQuestion();
			game.setHelpSkip(false);
			endCheck();
		}
	}
}
