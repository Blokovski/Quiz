package com.outland.quiz;

import android.app.Application;
import android.content.Context;
import com.outland.quiz.model.Game;



public class App extends Application
{

	private static Context mContext;
	private static Game game;

	@Override
	public void onCreate()
	{
		super.onCreate();		
		mContext = this;	
	}

	public static Context getContext()
	{
		return mContext;
	}

	public static Game getGame()
	{
		return game;
	}

	public static void setGame()
	{
		App.game = new Game();
	}

}
