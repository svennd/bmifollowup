package db;

import java.sql.Connection;
import java.sql.Statement;
import net.sf.jachievement.Achievement;
import net.sf.jachievement.AchievementQueue;


public class target {
    
       public boolean add_target(Long date, int type, float value, int user_id) {

        if ( System.currentTimeMillis() > date)
        {
                Achievement a = new Achievement("I am a timetraveler!");
                AchievementQueue queue = new AchievementQueue();
                queue.add(a);
        }
        
        Connection connection = connector.GetConnection();

        try {
            Statement stmt = connection.createStatement();
            
            int rs = stmt.executeUpdate("INSERT INTO target(user_id, type, value, date) VALUES('" + user_id + "','" + type + "','" + value + "', '" + date + "');");
            return true;
        } catch (Exception ex) {
            System.out.println("add_target"+ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
            return false;
        }
    }
       
    public boolean delete_target(int id)
    {
        Connection connection = connector.GetConnection();

        try {
            Statement stmt = connection.createStatement();

            stmt.executeUpdate("DELETE FROM target WHERE id = " + id + ";");
            return true;
        } catch (Exception x) {
            return false;
        }
    }
}
