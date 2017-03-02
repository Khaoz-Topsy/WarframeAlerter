package ADTs;

import java.util.ArrayList;

public class QAs 
{
	private String Question;
	private String Answer1;
	private String Answer2;
	private ArrayList<String> FinalAnswer = new ArrayList<>();
	
	public QAs(String Q, String A1, String A2)
	{
		Question = Q;
		Answer1 = A1;
		Answer2 = A2;
	}
	
	public QAs(ArrayList<String> Final)
	{
		FinalAnswer = Final;
	}

	public String getQuestion() 
	{
		return Question;
	}

	public String getAnswer1() 
	{
		return Answer1;
	}

	public String getAnswer2() 
	{
		return Answer2;
	}

	public ArrayList<String> getFinal() 
	{
		return FinalAnswer;
	}
	
	
}
