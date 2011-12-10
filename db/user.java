package db;

import howest.dhert.svenn.users;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import net.sf.jachievement.Achievement;
import net.sf.jachievement.AchievementQueue;

public class user {

    /* 
     *  probeert de gebruiker in te loggen
     *  return          : users(id, name, logged_in)
     *  return on fail  : users() & system.out.println
     */
    public users user_attempt_login(String user, String pasword) {

        Connection connection = connector.GetConnection();
        
        try {
            //
            Statement stmt = connection.createStatement();
            
            // 
            ResultSet rs = stmt.executeQuery("SELECT user_id, user_name FROM users WHERE user_name ='" + user + "' AND user_pasw ='" + pasword + "' LIMIT 0,1;");

            // we gaan er vanuit dat er iets komt.
            try{
                rs.next();

                int user_id             = rs.getInt("user_id");
                String user_name        = rs.getString("user_name");

                return new users(user_id, user_name, true);
            } catch (Exception ex) {
                return new users();
            }

        } catch (Exception ex) {
            System.out.println("attemptlogin"+ex.getMessage());
                return new users();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
    
    /*
     *  probeert een nieuwe gebruiker aan te maken & in te loggen
     *  return          : user(id, name, logged_in)
     *  return on fail  : user() & system.out.println()
     */
    public users user_attempt_registration(String user, String pasword){
        Connection connection = connector.GetConnection();
        
        try {
            //
            Statement stmt = connection.createStatement();
            
            // 
            int rs = stmt.executeUpdate("INSERT INTO users (user_name, user_pasw) VALUES ('" + user + "', '" + pasword + "');", Statement.RETURN_GENERATED_KEYS);
            

            if (rs == 1)
            {
                // get user_id
                ResultSet res = stmt.getGeneratedKeys();
                int user_id = 0;
                while (res.next())
                user_id = res.getInt(1);
                
                users current_user = new users(user_id, user, true);
                
                // adding achievements
                achievement achievement_login = new achievement(current_user);
                achievement_login.insert("new_entry", 0);
                achievement_login.insert("login", 0);
                
                // http://jachievement.sourceforge.net
                Achievement a = new Achievement("Nieuwe Gebruiker Achievement! Welkom!");
                AchievementQueue queue = new AchievementQueue();
                queue.add(a);
                
                // return new user
                return current_user;
            }
            else
            {
                return new users();
            }
        } catch (Exception ex) {
                System.out.println("attemt reg" +ex.getMessage());
                return new users();
        } finally {
            try {
                connection.close();
            } catch (Exception ex) {
            }
        }
    }
}
