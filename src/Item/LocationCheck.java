package Item;

public class LocationCheck 
{
	public static boolean Check(String Location)
	{
		if(Location.toLowerCase().equals("(earth)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(mercury)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(mars)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(venus)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(saturn)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(jupiter)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(phobos)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(sedna)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(europa)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(uranus)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(neptune)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(pluto)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(eris)"))
		{
			return true;
		}
		if(Location.toLowerCase().equals("(ceres)"))
		{
			return true;
		}
		
		return false;
	}
}
