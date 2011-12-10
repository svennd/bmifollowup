package db;

import howest.dhert.svenn.users;
import java.sql.*;

public class achievement
{

    users current_user;

    public achievement(users current_user)
    {
        this.current_user = current_user;
    }
    
    /*
     *  name    : update
     *  use     : update settings with a value (key must already exist)
     *  trace   : 
     */
    public boolean update(String key, int value)
    {

        Connection connection = connector.GetConnection();
        try
        {
            //
            Statement stmt = connection.createStatement();

            // 
            int rs = stmt.executeUpdate("UPDATE settings SET value = value + " + value + " WHERE name = '" + key + "' AND user_id = " + current_user.getId() + ";");
            return true;

        }
        catch (Exception ex) {System.out.println("updateachiev" + ex.getMessage()); return false;} 
        finally {try {connection.close();} catch (Exception ex) {}}
    }

    /*
     *  name    : insert
     *  use     : insert a new key
     *  trace   : 
     */
    public boolean insert(String key, int value)
    {
        
        Connection connection = connector.GetConnection();
        try
        {
            //
            Statement stmt = connection.createStatement();

            // 
            int rs = stmt.executeUpdate("INSERT INTO settings (name, value, user_id) VALUES ('" + key + "', '" + value + "', " + current_user.getId() + ")");
            return true;

        }
        catch (Exception ex) {System.out.println("insertachi" + ex.getMessage()); return false;} 
        finally {try {connection.close();} catch (Exception ex) {}}
    }
    
    /*
     *  name    : get_value
     *  use     : get the correspending key from db & user
     *  trace   : 
     */
    public int get_value(String key)
    {
        
        Connection connection = connector.GetConnection();
        try
        {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT * FROM settings where user_id = "+ current_user.getId() +" && name = '" + key + "' LIMIT 0,1;");

            rs.next();
            return rs.getInt("value");

        }
        catch (Exception ex) {System.out.println("getvalueachu" + ex.getMessage()); return -1;} 
        finally {try {connection.close();} catch (Exception ex) {}}
    }
}
