package com.outland.quiz.model;

import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;


import android.util.Log;

public class Parser
{
	public static List<Question> parseQuestion(String input)
	{
		Question currentQuestion;
		final List<Question> messages = new ArrayList<Question>();
		try
		{
			JSONArray jsonArray = new JSONArray(input);
			for (int i = 0; i < jsonArray.length(); i++)
			{
				currentQuestion = new Question();
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				
				currentQuestion.setId(jsonObject.optInt("id"));
				currentQuestion.setQuestion(jsonObject.optString("question"));
				currentQuestion.setCorectAnswer(jsonObject.optString("corectAnswer"));
				//currentQuestion.setCategory(jsonObject.optInt("category"));
				
				JSONArray arrAnswers = jsonObject.getJSONArray("answer");
				final List<String> answers = new ArrayList<String>();
				for (int j = 0; j < arrAnswers.length(); j++)
				{					
					answers.add(arrAnswers.getString(j));
				}
				currentQuestion.setAnswers(answers);
				messages.add(currentQuestion);
			}
		} catch (Exception e)
		{
			Log.e("Parser.parseQuestion()", e.toString());
			return null;
		}

		return messages;
	}

}
