package com.outland.quiz;

import java.util.List;
import com.outland.quiz.model.Game;
import com.outland.quiz.model.Question;
import com.outland.quiz.model.Rules;

import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
	LinearLayout list;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		tvAnswers = (TextView) findViewById(R.id.tvAnsvers);
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

//	private void setQuestion(Question q)
//	{
//		if (q != null)
//		{
//			tvQuestion.setText(q.getQuestion());
//			List<String> ans = q.getAnswers();
//			String answers = "";
//			for (int i = 0; i < ans.size(); i++)
//			{
//				int brojac = i + 1;
//				answers += brojac + ".: " + ans.get(i) + "\r\n";
//				tvAnswers.setText(answers);
//			}
//		}
//	}

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
							String ans1 = game.getActualQuestion().getCorectAnswer();
							String ans2 = String.valueOf(tt.getText());
							
							
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

	public void onClickAnswer(View view)
	{
		Button btn = (Button) view;
		// Toast.makeText(App.getContext(), btn.getText(),
		// Toast.LENGTH_SHORT).show();
		if (game.getActualQuestion().getCorectAnswer().equals(btn.getText()))
		{
			correctAnswer();
			timer.addTimeToCountDown(5000);

		} else
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
		// Toast.makeText(App.getContext(), "Hajde ponovo",
		// Toast.LENGTH_SHORT).show();
		// game.setQuestionPosition(true);
		// game.incNumberOfAnsweredQuestion();
		// game.incFalseAnswers();
		// endCheck();
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
