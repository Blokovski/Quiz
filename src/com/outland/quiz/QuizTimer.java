package com.outland.quiz;

import android.os.CountDownTimer;

abstract class QuizTimer
{

	private long mDeadline;
	private long mInterval;
	private long remaningTime;

	public QuizTimer(long deadline, long interval)
	{
		mDeadline = deadline;
		mInterval = interval;

		mTimer = new MyCountDownTimer(mDeadline, mInterval);
	}

	public synchronized void start()
	{
		mTimer.start();
	}

	public abstract void onTick(long time);

	public abstract void onFinish();

	public synchronized void addTimeToCountDown(long addTime)
	{
		mTimer.cancel();
		mTimer = new MyCountDownTimer(remaningTime+= addTime, mInterval );
		mTimer.start();
	}

	public synchronized void removeTimeToCountDown(long addTime)
	{
		mTimer.cancel();
		mTimer = new MyCountDownTimer(remaningTime-= addTime, mInterval);
		mTimer.start();
	}

	private class MyCountDownTimer extends CountDownTimer
	{

		public MyCountDownTimer(long mDeadline, long mInterval)
		{
			super(mDeadline, mInterval);
		}

		public void onFinish()
		{
			QuizTimer.this.onFinish();
		}

		public void onTick(long time)
		{
			QuizTimer.this.onTick(time);
			QuizTimer.this.remaningTime = time;
		}
	}

	private MyCountDownTimer mTimer;
}
