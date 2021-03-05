package Model;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ConnexionDB {
	Connection conn;
	PreparedStatement prepare;
	String databaseName ;
	String root ;
	String password;
	
public ConnexionDB(String dbName, String user, String password) {
		try {
			this.databaseName = dbName;
			this.root= user;
			this.password = password;
			connect(databaseName,root,password);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	public void connect(String databaseName, String user, String password)  {
		try
         {
			 Class.forName("com.mysql.cj.jdbc.Driver");      
			 System.out.println("Driver O.K.");      
			 String url = "jdbc:mysql://localhost:3303/"+databaseName+"?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC";      
			 conn = DriverManager.getConnection(url, user, password);      
			 System.out.println("Connexion effective !");  
	     }
        catch(Exception ex){
	    ex.printStackTrace();
        }
		
    }      
	
}
