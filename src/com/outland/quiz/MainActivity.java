package com.outland.quiz;

import java.util.List;
import com.outland.quiz.model.Game;
import com.outland.quiz.model.Question;

import android.os.Bundle;
import android.app.Activity;
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
		
		game = new Game();
		setQuestion(game.getActualQuestion());
		
	  
		timer = new QuizTimer(30000, 1000)
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
		Toast.makeText(App.getContext(), "GAME OVER!!!", Toast.LENGTH_SHORT).show();
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
		game.setQuestionPosition(true);
		setQuestion(game.getActualQuestion());
	}
	
	private void wrongAnswer()
	{
		Toast.makeText(App.getContext(), "Hajde ponovo", Toast.LENGTH_SHORT).show();
	}
}
