package com.outland.nflquiz.model;

import java.util.ArrayList;
import java.util.List;

public class Question
{
	private int id;
	private String question;
	private List<String> answers = new ArrayList<String>();
	private String corectAnswer;
	private int category;
	private int difficulty;
	
	public int getId()
	{
		return id;
	}
	public void setId(int id)
	{
		this.id = id;
	}
	public String getQuestion()
	{
		return question;
	}
	public void setQuestion(String question)
	{
		this.question = question;
	}
	public String getCorectAnswer()
	{
		return corectAnswer;
	}
	public void setCorectAnswer(String corectAnswer)
	{
		this.corectAnswer = corectAnswer;
	}
	public int getCategory()
	{
		return category;
	}
	public void setCategory(int category)
	{
		this.category = category;
	}
	public List<String> getAnswers()
	{
		return answers;
	}
	public void setAnswers(List<String> answers)
	{
		this.answers = answers;
	}
	public int getDifficulty()
	{
		return difficulty;
	}
	public void setDifficulty(int difficulty)
	{
		this.difficulty = difficulty;
	}
}
