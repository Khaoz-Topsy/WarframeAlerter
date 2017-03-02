package Item;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
 
public class SqlServerJDBC 
{
        public static void InsertAlert(String GUID, String Title, String Author, String Descrip, Timestamp PubDate, String Faction, Timestamp Expiry) 
        {
            try 
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                System.out.println("# - Driver Loaded");
               
                String server = "192.168.0.5";
                int port = 1433;
                String user = "sa"; // Sql server username
                String password = "iamkurt101SQL";
                String database = "Warframe";

                String jdbcUrl = "jdbc:sqlserver://"+server+":"+port+";user="+user+";password="+password+";databaseName="+database+"";
               
                Connection con = DriverManager.getConnection(jdbcUrl);
//                System.out.println("# - Connection Obtained");
               
                Statement stmt = con.createStatement();
//                System.out.println("# - Statement Created");
                
//                System.out.println("JDBC - Inserting records into the table...");
                stmt = con.createStatement();
                
                String sql = "INSERT INTO TBL_Alert (Alert_GUID, Alert_Title, Alert_Author, Alert_Descrip, Alert_PubDate, Alert_Faction, Alert_Expiry) " +
                             "VALUES ('" + 
                             			GUID + 		"', '" + 
                             			Title + 	"', '" +
                             			Author + 	"', '" +
                             			Descrip + 	"', '" +
                             			PubDate + 	"', '" + 
                             			Faction + 	"', '" + 
                             			Expiry + 	"')";
                stmt.executeUpdate(sql);
          
//                System.out.println("Inserted Alert");
                
                stmt.close();
                con.close();
//                System.out.println("JDBC - Closed");
        } catch (Exception ex) {
                System.out.println("Error : "+ex);
        }
        }
        
        public static int GetAlertInt(String GUID) 
        {
        	int Result = 0;
            try 
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                System.out.println("# - Driver Loaded");
               
                String server = "192.168.0.5";
                int port = 1433;
                String user = "sa"; // Sql server username
                String password = "iamkurt101SQL";
                String database = "Warframe";

                String jdbcUrl = "jdbc:sqlserver://"+server+":"+port+";user="+user+";password="+password+";databaseName="+database+"";
               
                Connection con = DriverManager.getConnection(jdbcUrl);
                //System.out.println("# - Connection Obtained");
               
                Statement stmt = con.createStatement();
                //System.out.println("# - Statement Created");
                
//                System.out.println("JDBC - Executing Query");
                stmt = con.createStatement();
                
                String sql = "SELECT Alert_ID FROM TBL_Alert WHERE TBL_Alert.Alert_GUID='" + GUID + "'";
                ResultSet rs = stmt.executeQuery(sql);

                while(rs.next())
                {
                	Result = rs.getInt(1);
//                	System.out.println("AlertID: " + Result);
                }
                
                rs.close();
                stmt.close();
                con.close();
//                System.out.println("JDBC - Closed");
	        } catch (Exception ex) {
	                System.out.println("Error : "+ex);
	        }
            
            return Result;
        }
        
        public static void InsertAdditionalInfo(int AlertID, String Money, String Reward, String RewardType, String Location, String Descrip)
        {
            try 
            {
                Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
//                System.out.println("# - Driver Loaded");
               
                String server = "192.168.0.5";
                int port = 1433;
                String user = "sa"; // Sql server username
                String password = "iamkurt101SQL";
                String database = "Warframe";

                String jdbcUrl = "jdbc:sqlserver://"+server+":"+port+";user="+user+";password="+password+";databaseName="+database+"";
               
                Connection con = DriverManager.getConnection(jdbcUrl);
//                System.out.println("# - Connection Obtained");
               
                Statement stmt = con.createStatement();
//                System.out.println("# - Statement Created");
                
//                System.out.println("JDBC - Inserting records into the table...");
                stmt = con.createStatement();
                
                String sql = "INSERT INTO TBL_Information (Alert_ID, Info_Money, Info_Reward, Info_RewardType, Info_Location, Info_Description) " +
                             "VALUES (" + 
                             			AlertID + 	" , '" + 
                             			Money + 	"', '" +
                             			Reward + 	"', '" +
                             			RewardType+	"', '" +
                             			Location + 	"', '" +
                             			Descrip + 	"')";
                stmt.executeUpdate(sql);
          
//                System.out.println("Inserted Info records");
//				System.out.println("Database Entry, Info added");
                
                stmt.close();
                con.close();
//                System.out.println("JDBC - Closed");
        } catch (Exception ex) {
//                System.out.println("Error : "+ex);
        }
    }        

    	public static Timestamp StringToTimeStamp(String Input)
    	{
    		Timestamp Result = null;
    		
    	    DateFormat df = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z"); 
    	    try
    	    {
    	    	java.util.Date temp = df.parse(Input);
    	    	Result = new java.sql.Timestamp(temp.getTime());
    	    }
    	    catch(Exception e){}
    		return Result;
    	}
}