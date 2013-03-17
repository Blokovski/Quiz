package com.outland.quiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.outland.quiz.model.Game;
import com.outland.quiz.model.Question;
import com.outland.quiz.model.Rules;

public class MainActivity extends Activity
{

	TextView tvQuestion;
	TextView tvTimer;
	TextView tvScore;
	Game game;
	QuizTimer timer;
	long remainTime;
	LinearLayout list;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		 AdView adView = (AdView)this.findViewById(R.id.adView);
		 adView.loadAd(new AdRequest());

		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		tvScore = (TextView) findViewById(R.id.tvScore);
		list = (LinearLayout) findViewById(R.id.llAnswers);

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
		try
		{
			list.removeAllViews();
			tvQuestion.setText(q.getQuestion());
			List<String> ans = q.getAnswers();
			if (ans.size() > 0)
			{
				LayoutInflater inflater = (LayoutInflater) App.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				for (int i = 0; i < ans.size(); i++)
				{

					View v = inflater.inflate(R.layout.row_answer, null);
					final TextView tt = (TextView) v.findViewById(R.id.tvAnswerRow);
					tt.setText(ans.get(i));

					v.setClickable(true);
					v.setFocusable(true);

					v.setOnClickListener(new OnClickListener()
					{
						// @Override
						public void onClick(View v)
						{
							
							if (game.getActualQuestion().getCorectAnswer().equals(tt.getText()))
							{
								correctAnswer();
								timer.addTimeToCountDown(5000);

							} else
							{
								wrongAnswer();
							}

						}
					});

					list.addView(v);
				}

			}

		} catch (Exception e)
		{
			Log.e("SetMainList", e.toString());
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

	private void correctAnswer()
	{
		Toast.makeText(App.getContext(), "Tacan odgovor", Toast.LENGTH_SHORT).show();
		game.addScore();
		updateScore();
		game.setQuestionPosition(true);
		game.incNumberOfAnsweredQuestion();
		game.incTrueAnswers();
		// setQuestion(game.getActualQuestion());
		endCheck();
	}

	private void endCheck()
	{
		if (game.getNumberOfAnsweredQuestions() < game.getNumberOfQuestion())
		{
			setQuestion(game.getActualQuestion());
		} else
		{
			gameOver();
		}
	}

	private void wrongAnswer()
	{
		gameOver();
	}

	private void updateScore()
	{
		tvScore.setText(String.valueOf(game.getScore()));
	}

	public void onClickHelpAddTime(View view)
	{
		if (game.getHelpMoreTime() > 0)
		{
			timer.addTimeToCountDown(Rules.ADDITIONAL_TIME_ADD);
			game.usingHelpMoreTime();
			game.removeScore(Rules.ADDITIONAL_TIME_POINTS_PENALTY);
		}
	}

	public void onClickHelpHalf(View view)
	{
		if (game.getHelpHalf() > 0)
		{
			int a = game.getAnswerPosition(game.getActualQuestion());
			int oneMore = -1;
			
			List<Integer> ansi = new ArrayList<Integer>();
			ansi.add(0);
			ansi.add(1);
			ansi.add(2);
			ansi.add(3);
			
			ansi.remove(a);

			boolean cool = false;
			Random ran = new Random();
			while (!cool)
			{
				
				int x = ran.nextInt(4);
				if (x != a)
				{
					oneMore = x;
					cool = true;
				}
			}
			
			//Toast.makeText(App.getContext(), "Tacan odgovor je pod:" + a, Toast.LENGTH_SHORT).show();
			ansi.remove(getPosInIntList(ansi, oneMore));
			
			
			List<View> taci =  list.getTouchables();
					
			for (Integer integer : ansi)
			{
				View v = taci.get(integer);
				list.removeView(v);
			}
			
		}
		game.usingHelpHalf();
	}

	public void onClickHelpSkip(View view)
	{
		if (game.getHelpSkip()>0)
		{
			game.setQuestionPosition(true);
			game.incNumberOfAnsweredQuestion();
			game.usingHelpSkip();
			endCheck();
		}
	}
	
	private int getPosInIntList(List<Integer> aList, int broj)
	{
		
		for (int i = 0; i < aList.size(); i++)
		{
			if (aList.get(i).equals(broj))
			{
				return i;
			}
		}
		return -1;
	}
}
