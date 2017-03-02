package Files;

import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *This Class Reads in data from a given TextFile.
 *
 * @author Kurt Lourens 201336677
 */
public class UserFileReader
{
	/**
	 * This method Reads data from the textFile
	 * @param filename, This is a string representation of the location of the textfile
	 * @return scoreData, this is the stored highscores
	 */
	public static DataFile readEmailFile(String filename)
	{
		Scanner LineReader = null;
		DataFile MyData = new DataFile();
		
		try
		{
			File MyFile = new File(filename);
			LineReader = new Scanner(MyFile);

			MyData.setEmail(LineReader.nextLine());
			MyData.setMod(toBoolean(LineReader.nextLine()));
			MyData.setKey(toBoolean(LineReader.nextLine()));
			MyData.setAura(toBoolean(LineReader.nextLine()));
			MyData.setVauban(toBoolean(LineReader.nextLine()));;
			MyData.setResource(toBoolean(LineReader.nextLine()));
			MyData.setBlueprint(toBoolean(LineReader.nextLine()));
		}
		catch(FileNotFoundException ex) {}
		finally
		{
			if(LineReader!=null)
			{
				LineReader.close();
			}
		}
		
		return MyData; //Return the
	}
	

	public static boolean toBoolean(String sBool)
	{
		if(sBool.contains("true"))
		{
			return true;
		}
		
//		if(sBool.contains("false"))
//		{
			return false;
//		}
	}
}

