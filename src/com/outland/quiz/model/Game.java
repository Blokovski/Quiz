package com.outland.quiz.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import com.outland.quiz.App;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Game
{
	int score;
	int numberOfQuestion;
	int numberOfAnsweredQuestions;

	private Question actualQuestion;
	private int questionPosition = 0;

	private int trueAnswers;
	private int falseAnswers;
	
	

	private int difficulty;
	private String playerName;
	private int right;
	private int wrong; // ne koristi se u ovoj verziji

	private int helpHalf;
	private int helpMoreTime;
	private int helpSkip;

	List<Question> questions = new ArrayList<Question>();
	String json;

	public Game()
	{

		json = Util.getStringFromJsonResource();
		questions = Parser.parseQuestion(json);
		long seed = System.nanoTime();
		Collections.shuffle(questions, new Random(seed));
		setGameSettings();
		numberOfAnsweredQuestions = 0;
		trueAnswers = 0;
		
		setFalseAnswers(0);
		setActualQuestion();
		setNumberOfQuestion();
		setHelps();
		
	}
	
	private void setHelps()
	{
		switch (difficulty)
		{
		case 1:
			helpHalf = Rules.NUMBER_OF_HALF_EASY;
			helpMoreTime = Rules.NUMBER_OF_ADD_TIME_EASY;
			helpSkip = Rules.NUMBER_OF_SKIP_EASY;
			break;
			
		case 2:
			helpHalf = Rules.NUMBER_OF_HALF_MEDIUM;
			helpMoreTime = Rules.NUMBER_OF_ADD_TIME_MEDIUM;
			helpSkip = Rules.NUMBER_OF_SKIP_MEDIUM;
			break;
			
		case 3:
			helpHalf = Rules.NUMBER_OF_HALF_HARD;
			helpMoreTime = Rules.NUMBER_OF_ADD_TIME_HARD;
			helpSkip = Rules.NUMBER_OF_SKIP_HARD;
			break;

		default:
			break;
		}
	}
	
	private void setGameSettings()
	{
		SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(App.getContext());
		difficulty = sharedPref.getInt("listdiff" , 1);
	}

	public int getAnswerPosition(Question q)
	{
		int pos = -1;
		String corectAnswer = q.getCorectAnswer();
		List<String> ans = q.getAnswers();

		for (int i = 0; i < ans.size(); i++)
		{
			if (corectAnswer.equals(ans.get(i)))
			{
				return i;
			}
		}
		return pos;
	}

	public void incNumberOfAnsweredQuestion()
	{
		this.numberOfAnsweredQuestions++;
	}

	public void incTrueAnswers()
	{
		this.trueAnswers++;
	}

	public void incFalseAnswers()
	{
		this.setFalseAnswers(this.getFalseAnswers() + 1);
	}

	public void setActualQuestion()
	{
		actualQuestion = questions.get(questionPosition);
	}

	public int addScore()
	{
		int diff = getActualQuestion().getDifficulty();
		switch (diff)
		{
		case 1:
			score += Rules.EASY_POINTS;
			break;

		case 2:
			score += Rules.MEDIUM_POINTS;
			break;

		case 3:
			score += Rules.HARD_POINTS;
			break;

		default:
			break;
		}
		score += Rules.MEDIUM_POINTS;
		return score;
	}
	
	public int removeScore(int points)
	{
		score -= points;
		return score;
	}

	public int getScore()
	{
		return score;
	}

	public void setScore(int score)
	{
		this.score = score;
	}

	public int getNumberOfQuestion()
	{
		return numberOfQuestion;
	}

	private void setNumberOfQuestion()
	{
		if (questions != null)
		{
			this.numberOfQuestion = questions.size();
		}
	}

	public int getNumberOfAnsweredQuestions()
	{
		return numberOfAnsweredQuestions;
	}

	public int getDifficulty()
	{
		return difficulty;
	}

	public void setDifficulty(int difficulty)
	{
		this.difficulty = difficulty;
	}

	public String getPlayerName()
	{
		return playerName;
	}

	public void setPlayerName(String playerName)
	{
		this.playerName = playerName;
	}

	public int getRight()
	{
		return right;
	}

	public void setRight(int right)
	{
		this.right = right;
	}

	public int getWrong()
	{
		return wrong;
	}

	public void setWrong(int wrong)
	{
		this.wrong = wrong;
	}

	public List<Question> getQuestions()
	{
		return questions;
	}

	public void setQuestions(List<Question> questions)
	{
		this.questions = questions;
	}

	public Question getActualQuestion()
	{
		return questions.get(this.getQuestionPosition());
	}

	public int getQuestionPosition()
	{
		return questionPosition;
	}

	public void setQuestionPosition(boolean incQuestion)
	{
		if (incQuestion)
		{
			this.questionPosition++;
		} else
		{
			this.questionPosition--;
		}
	}

	public int getTrueAnswers()
	{
		return trueAnswers;
	}

	public void setTrueAnswers(int trueAnswers)
	{
		this.trueAnswers = trueAnswers;
	}

	public int getFalseAnswers()
	{
		return falseAnswers;
	}

	public void setFalseAnswers(int falseAnswers)
	{
		this.falseAnswers = falseAnswers;
	}

	public int getHelpHalf()
	{
		return helpHalf;
	}

	public void usingHelpHalf()
	{
		this.helpHalf--;
	}

	public int getHelpMoreTime()
	{
		return helpMoreTime;
	}

	public void usingHelpMoreTime()
	{
		this.helpMoreTime--;
	}

	public int getHelpSkip()
	{
		return helpSkip;
	}

	public void usingHelpSkip()
	{
		this.helpSkip--;
	}

	

}
