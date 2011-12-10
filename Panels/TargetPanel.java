package Panels;

import Resource.FloatHelper;
import db.*;
import howest.dhert.svenn.users;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.*;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 *  trace   : AppHandler, NewdpPanel
 */
public class TargetPanel extends FloatHelper{
    
    public users current_user;
    public static JLabel target_data; // need static for update
    public JPanel target_screen;
          
    // constructor
    public TargetPanel (users current_user)
    {
        this.current_user = current_user;
    }

    /*
     *  name    : show_target_screen
     *  use     : will generate the basic screen
     *  trace   : AppHandler
     */
    public JPanel show_target_screen(){
        
        target_screen = new JPanel(new GridLayout(3,1));
                
            JLabel txt_target = new JLabel("  Target Lijst");
             txt_target.setFont(new Font("Dialog", Font.PLAIN, 24));
                 target_screen.add(txt_target);
                 
            target_data = new JLabel();
                target_screen.add(target_data);
                
            update_target_screen(current_user.getId());        
        return target_screen;
    }
    
    /*
     *  name    : update_target_screen
     *  use     : will allow panels to update the text inside target_data
     *  trace   : AppHandler, NewdpPanel    (ManageDataPanel should be added aswell)
     */
    public void update_target_screen(int user_id)
    {
        Connection connection = connector.GetConnection();
        
        String ResultString = "";
        try 
        {
            Statement stmt = connection.createStatement();

            // get dataset for user_id
            ResultSet rs = stmt.executeQuery("SELECT * FROM target WHERE user_id = '" + user_id + "' LIMIT 0,3;");
            
            int type = 0, value = 0;
            float last_bmi = 0, last_weight = 0, difference = 0;
            long date = 0;
            
            dataset data = new dataset();
            
            while (rs.next()) {
                    type    = rs.getInt("type");
                    value   = rs.getInt("value");
                    date    = rs.getLong("date");
                    
                // delete entry if passed
                if ( System.currentTimeMillis() > date)
                {
                    target x = new target();
                    x.delete_target(rs.getInt("id"));
                }
                    
                    // BMI
                    if (type == 0)
                    {
                        last_bmi = data.get_last_bmi(user_id);
                        difference = data.round2(value - last_bmi);
                    }
                    // gewicht
                    else
                    {
                        last_weight = data.get_last_weight(user_id);
                        difference = data.round2(value - last_weight);
                    }
                    
                    // days left
                    long now = System.currentTimeMillis();
                    long time_left = date - now;
                    long days_left = (time_left / (24 * 60 * 60 * 1000));
                    
                    if (type == 0) // bmi
                        ResultString += "Still <i>" + days_left + "</i> days<br/>Till Target BMI : " 
                                        + value + " (" + difference + ")<br/>";
                    else    // gewicht
                        ResultString += "                Still <i>" + days_left + "</i> days<br/>Till Target Gewicht : " 
                                        + value + " (" + difference + ")<br/>";
                    
            }
            
            target_data.setText("<html>                " + ResultString + "<html>"); 

        } 
        catch (Exception ex) {System.out.println("update target screen" + ex.getMessage());} 
        finally {try {connection.close();} catch (Exception ex) {}}
    }
}
