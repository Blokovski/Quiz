package com.outland.nflquiz;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.ads.AdRequest;
import com.google.ads.AdView;
import com.google.analytics.tracking.android.EasyTracker;
import com.outland.nflquiz.model.Game;
import com.outland.nflquiz.model.Question;
import com.outland.nflquiz.model.Rules;

public class MainActivity extends Activity
{

	TextView tvQuestion;
	TextView tvTimer;
	TextView tvScore;
	Game game;
	QuizTimer timer;
	long remainTime;
	LinearLayout list;
	FrameLayout fl;
	MediaPlayer mediaPlayer;

	Button btnAddTimeHelp;
	Button btnHalfHelp;
	Button btnSkipHelp;

	boolean halfSemafor = false;

	int Measuredwidth = 0;
	int Measuredheight = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		AdView adView = (AdView) this.findViewById(R.id.adView);
		adView.loadAd(new AdRequest());
		screenSizecheck();

		tvQuestion = (TextView) findViewById(R.id.tvQuestion);
		tvTimer = (TextView) findViewById(R.id.tvTimer);
		tvScore = (TextView) findViewById(R.id.tvScore);
		list = (LinearLayout) findViewById(R.id.llAnswers);
		fl = (FrameLayout) findViewById(R.id.main_draw);

		btnAddTimeHelp = (Button) findViewById(R.id.btnAddTimeHelp);
		btnHalfHelp = (Button) findViewById(R.id.btnHalfHelp);
		btnSkipHelp = (Button) findViewById(R.id.btnSkipHelp);

		game = App.getGame();
		setQuestion(game.getActualQuestion());
		updateButtonState();

		timer = new QuizTimer(Rules.TIME, 1000)
		{

			@Override
			public void onTick(long time)
			{
				tvTimer.setText("" + time / 1000);
				remainTime = time;

			}

			@Override
			public void onFinish()
			{
				// tvTimer.setText("done!");
				gameOver();

			}
		};
		timer.start();

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
		if (mediaPlayer != null)
		{
			mediaPlayer.release();
		}
		timer.stop();
		EasyTracker.getInstance().activityStop(this);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		timer.stop();

	}

	private void playOkSound()
	{
		if (game.isSound())
		{
			mediaPlayer = MediaPlayer.create(App.getContext(), R.raw.ok);
			mediaPlayer.start();
		}
	}

	private void playUsedSound()
	{
		if (game.isSound())
		{
			mediaPlayer = MediaPlayer.create(App.getContext(), R.raw.used);
			mediaPlayer.start();
		}
	}

	private void playWrongSound()
	{
		if (game.isSound())
		{
			mediaPlayer = MediaPlayer.create(App.getContext(), R.raw.wrong);
			mediaPlayer.start();
		}
	}

	private void setQuestion(Question q)
	{
		try
		{
			list.removeAllViews();
			tvQuestion.setText(q.getQuestion());
			List<String> ans = q.getAnswers();
			halfSemafor = false;
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

	private void screenSizecheck()
	{
		Measuredwidth = 0;
		Measuredheight = 0;
		Point size = new Point();
		WindowManager w = getWindowManager();

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2)
		{
			w.getDefaultDisplay().getSize(size);

			Measuredwidth = size.x;
			Measuredheight = size.y;
		} else
		{
			Display d = w.getDefaultDisplay();
			Measuredwidth = d.getWidth();
			Measuredheight = d.getHeight();
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
		// Toast.makeText(App.getContext(), "Tacan odgovor",
		// Toast.LENGTH_SHORT).show();
		playOkSound();
		createAnimScore(R.style.ScoreTextGreen, "+" + String.valueOf(game.addScore()));
		updateScore();
		game.setQuestionPosition(true);
		game.incNumberOfAnsweredQuestion();
		game.incTrueAnswers();
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

	private void helpUsed()
	{
		playUsedSound();
		game.removeScore(Rules.HELP_POINTS_PENALTY);
		createAnimScore(R.style.ScoreTextRed, "-" + String.valueOf(Rules.HELP_POINTS_PENALTY));
		updateScore();
	}

	private void wrongAnswer()
	{
		playWrongSound();
		gameOver();
	}

	private void updateScore()
	{
		tvScore.setText(String.valueOf(game.getScore()));
	}
	
	@SuppressWarnings("deprecation")
	private Drawable resize(Drawable image) 
	{
	    Bitmap d = ((BitmapDrawable)image).getBitmap();
	    Bitmap bitmapOrig = Bitmap.createScaledBitmap(d, 200, 200, false);
	    return new BitmapDrawable(bitmapOrig);
	}
	
	private void updateButtonStateOld()
	{
		switch (game.getHelpMoreTime())
		{
		case 3:
			
			btnAddTimeHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.clock_3)));
	
			break;
		case 2:
			btnAddTimeHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.clock_2)));
			break;
		case 1:
			btnAddTimeHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.clock_1)));
			break;
		case 0:
			btnAddTimeHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.clock_0)));
			break;

		default:
			break;
		}

		switch (game.getHelpHalf())
		{
		case 3:
			btnHalfHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.half_3)));
			break;
		case 2:
			btnHalfHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.half_2)));
			break;
		case 1:
			btnHalfHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.half_1)));
			break;
		case 0:
			btnHalfHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.half_0)));
			break;

		default:
			break;
		}

		switch (game.getHelpSkip())
		{
		case 3:
			btnSkipHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.netx_button_3)));
			break;
		case 2:
			btnSkipHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.netx_button_2)));
			break;
		case 1:
			btnSkipHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.netx_button_1)));
			break;
		case 0:
			btnSkipHelp.setBackgroundDrawable(resize(getResources().getDrawable(R.drawable.netx_button_0)));
			break;

		default:
			break;
		}
	}
	
	
	private void updateButtonState()
	{
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
		{
			switch (game.getHelpMoreTime())
			{
			case 3:
				btnAddTimeHelp.setBackgroundResource(R.drawable.clock_3);
				break;
			case 2:
				btnAddTimeHelp.setBackgroundResource(R.drawable.clock_2);
				break;
			case 1:
				btnAddTimeHelp.setBackgroundResource(R.drawable.clock_1);
				break;
			case 0:
				btnAddTimeHelp.setBackgroundResource(R.drawable.clock_0);
				break;

			default:
				break;
			}
			switch (game.getHelpHalf())
			{
			case 3:
				btnHalfHelp.setBackgroundResource(R.drawable.half_3);
				break;
			case 2:
				btnHalfHelp.setBackgroundResource(R.drawable.half_2);
				break;
			case 1:
				btnHalfHelp.setBackgroundResource(R.drawable.half_1);
				break;
			case 0:
				btnHalfHelp.setBackgroundResource(R.drawable.half_0);
				break;

			default:
				break;
			}
			switch (game.getHelpSkip())
			{
			case 3:
				btnSkipHelp.setBackgroundResource(R.drawable.netx_button_3);
				break;
			case 2:
				btnSkipHelp.setBackgroundResource(R.drawable.netx_button_2);
				break;
			case 1:
				btnSkipHelp.setBackgroundResource(R.drawable.netx_button_1);
				break;
			case 0:
				btnSkipHelp.setBackgroundResource(R.drawable.netx_button_0);
				break;

			default:
				break;
			}
		}else
		{
			updateButtonStateOld();
		}
	}

	public void onClickHelpAddTime(View view)
	{
		if (game.getHelpMoreTime() > 0)
		{
			helpUsed();
			timer.addTimeToCountDown(Rules.ADDITIONAL_TIME_ADD);
			game.usingHelpMoreTime();
		}
		updateButtonState();
	}

	public void onClickHelpHalf(View view)
	{
		if (!halfSemafor)
		{
			if (game.getHelpHalf() > 0)
			{
				helpUsed();
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

				// Toast.makeText(App.getContext(), "Tacan odgovor je pod:" + a,
				// Toast.LENGTH_SHORT).show();
				ansi.remove(getPosInIntList(ansi, oneMore));

				List<View> taci = list.getTouchables();

				for (Integer integer : ansi)
				{
					View v = taci.get(integer);
					list.removeView(v);
				}

			}
			halfSemafor = true;
			game.usingHelpHalf();
			updateButtonState();
		}
	}

	public void onClickHelpSkip(View view)
	{
		if (game.getHelpSkip() > 0)
		{
			helpUsed();
			game.setQuestionPosition(true);
			game.incNumberOfAnsweredQuestion();
			game.usingHelpSkip();
			endCheck();
		}
		updateButtonState();
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

	private void createAnimScore(int res, String text)
	{

		final TextView tvScore = new TextView(MainActivity.this);
		tvScore.setText(text);
		// tvScore.setTextColor(Color.GREEN);
		tvScore.setTextAppearance(this, res);

		FrameLayout.LayoutParams par = new FrameLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		tvScore.setLayoutParams(par);
		tvScore.setPadding(Measuredwidth/2, Measuredheight/2, 0, 0);

		fl.addView(tvScore);

		TranslateAnimation a1 = new TranslateAnimation(0, 0, 0, -800);
		a1.setAnimationListener(new AnimationListener()
		{

			public void onAnimationEnd(android.view.animation.Animation arg0)
			{
				tvScore.setVisibility(View.GONE);

			}

			public void onAnimationRepeat(android.view.animation.Animation animation)
			{
				// TODO Auto-generated method stub

			}

			public void onAnimationStart(android.view.animation.Animation animation)
			{

			};
		});

		Random randInt = new Random();

		int b = randInt.nextInt(2000);
		a1.setDuration(5000); // (b + speed); // 5000
		a1.setFillAfter(true);
		tvScore.startAnimation(a1);

	}
}
