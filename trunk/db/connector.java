package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connector 
{

    public static Connection GetConnection()
    {

        Connection con;

        try
        {
            Class.forName("com.mysql.jdbc.Driver");
			String connectionUrl = "jdbc:mysql://YOURURL:YOURPORT/YOURDB";
			con = DriverManager.getConnection(connectionUrl, "USER", "PASW");

            return con;
        } 
        catch (SQLException e) {System.out.println("SQL Exception: " + e.toString());} 
        catch (ClassNotFoundException cE) {System.out.println("Class Not Found Exception: " + cE.toString());}
        
        return null;
    }
}
