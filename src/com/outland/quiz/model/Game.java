package com.outland.quiz.model;

import java.util.ArrayList;
import java.util.List;

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
	private int wrong;
	
	private boolean helpHalf;
	private boolean helpMoreTime;
	private boolean helpSkip;

	List<Question> questions = new ArrayList<Question>();
	String json;

	public Game()
	{

		json = Util.getStringFromJsonResource();
		questions = Parser.parseQuestion(json);
		numberOfAnsweredQuestions = 0;
		trueAnswers = 0;
		helpHalf = true;
		helpMoreTime= true;
		helpSkip=true;
		setFalseAnswers(0);
		setActualQuestion();
		setNumberOfQuestion();
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
		this.numberOfAnsweredQuestions ++;
	}
	
	public void incTrueAnswers()
	{
		this.trueAnswers ++;
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
		score += Rules.MEDIUM_POINTS;
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

	private  void setNumberOfQuestion()
	{
		if (questions!= null)
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

	public boolean isHelpHalf()
	{
		return helpHalf;
	}

	public void setHelpHalf(boolean helpHalf)
	{
		this.helpHalf = helpHalf;
	}

	public boolean isHelpMoreTime()
	{
		return helpMoreTime;
	}

	public void setHelpMoreTime(boolean helpMoreTime)
	{
		this.helpMoreTime = helpMoreTime;
	}

	public boolean isHelpSkip()
	{
		return helpSkip;
	}

	public void setHelpSkip(boolean helpSkip)
	{
		this.helpSkip = helpSkip;
	}

}
