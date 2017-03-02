package Files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 *This Class Writes in data to a given TextFile.
 *
 * @author Kurt Lourens 201336677
 */
public class UserFileWriter 
{

	/**
	 * This method Writes data to the textFile
	 * @param filename, This is a string representation of the location of the textfile
	 * @param MyData, this is the highscores to be stored
	 */
	public static void writeEmailFile(String FileName, DataFile MyData)
	{
		File MyFile = new File(FileName);
//		if(MyFile.exists())
//		{
			PrintWriter pw = null;
			try 
			{
				pw = new PrintWriter(MyFile);
				
				pw.println(MyData.getEmail());
				pw.println(MyData.getMod());
				pw.println(MyData.getKey());
				pw.println(MyData.getAura());
				pw.println(MyData.getVauban());
				pw.println(MyData.getResource());
				pw.println(MyData.getBlueprint());
				
				System.out.println("File written to: " + System.getProperty("user.home") + "\\Warframe Alert.txt");
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			if(pw != null)
			{
				pw.close();
			}
//		}
	}

	public void writeBlackList(String FileName, ArrayList<String> BlackList)
	{
		File MyFile = new File(FileName);
		if(MyFile.exists())
		{
			PrintWriter pw = null;
			try 
			{
				pw = new PrintWriter(MyFile);
				pw.println(BlackList.size());
				for(int a=0; a<BlackList.size(); a++)
				{
					pw.println(BlackList.get(a));
				}
			} 
			catch (FileNotFoundException e) 
			{
				e.printStackTrace();
			}
			if(pw != null)
			{
				pw.close();
			}
		}
	}
}
