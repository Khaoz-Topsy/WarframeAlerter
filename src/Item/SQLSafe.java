package Item;

public class SQLSafe {

	
	public static String Verify(String Incorrect)
	{
		String Correct = "";
		
		for(int a=0; a<Incorrect.length(); a++)
		{
			String Temp = Incorrect.substring(a, a+1);

			if(Temp.equals("@"))
			{
				Correct += "_";
			}
			else if(Temp.equals("#"))
			{
				Correct += "_";
			}
			else if(Temp.equals("$"))
			{
				Correct += "_";
			}
			else if(Temp.equals("~"))
			{
				Correct += "_";
			}
			else if(Temp.equals("'"))
			{
				Correct += "_";
			}
			else
			{
				Correct += Temp;	
			}
		}
		
		return Correct;
	}
}
