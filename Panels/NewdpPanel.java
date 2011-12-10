package Panels;

import db.achievement;
import db.dataset;
import howest.dhert.svenn.users;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.jachievement.Achievement;
import net.sf.jachievement.AchievementQueue;

/*
 *  trace   : AppHandler
 */
public class NewdpPanel implements ActionListener 
{
 
    public JTextField weight, length;
    public users current_user;
    public DefaultTableModel model;
   
    // constructor
    public NewdpPanel (users current_user, DefaultTableModel model)
    {
        this.current_user   = current_user;
        this.model          = model;
    }
    
    /*
     *  name    : show_newdatapoint_screen()
     *  use     : make panel to add a datapoint to the database
     *  trace   : AppHandler
     */
    public JPanel show_newdatapoint_screen()
    {
       JPanel new_datapoint = new JPanel(null);
        
       JLabel txt_new_entry = new JLabel("Nieuw datapunt");
                txt_new_entry.setFont(new Font("Dialog", Font.PLAIN, 24));
                txt_new_entry.setBounds(10, 0, 400, 100);

        // tags
        JLabel txt_new_weight = new JLabel("Gewicht (Kg)");
            txt_new_weight.setBounds(10, 80, 100, 25);
            
        JLabel txt_new_length = new JLabel("Lengte (m)");
            txt_new_length.setBounds(10, 110, 100, 25);

        // input fields
        weight = new JTextField();
            weight.setBounds(120, 80, 80, 25);

        length = new JTextField();
            length.setBounds(120, 110, 80, 25);

        // button
        JButton addbutton = new JButton("add");
            addbutton.setBounds(120, 140, 80, 25);
            addbutton.addActionListener(this);

        // add everything to the JPanel
        new_datapoint.add(txt_new_entry);
        new_datapoint.add(txt_new_weight);
        new_datapoint.add(txt_new_length);
        new_datapoint.add(weight);
        new_datapoint.add(length);
        new_datapoint.add(addbutton);
        
        return new_datapoint;
    }
    
    /*
     *     will attempt to add a new entry to
     *     the database, on succes it will add a row 
     *     to the model. It also checks for achievements
     *     and will attempt to update the target screen
     */
    public void actionPerformed(ActionEvent e) 
    {
        // table info needed (in case something is not set)
        dataset data_set = new dataset();

        String new_entry_result[] = data_set.add_entry(weight.getText(), length.getText(), current_user.getId(), model);

        model.insertRow(model.getRowCount(), new_entry_result);

        // update targetscreen
        TargetPanel label_update = new TargetPanel(current_user);
        label_update.update_target_screen(current_user.getId());

        achievement achievement_login = new achievement(current_user);
        achievement_login.update("new_entry", 1);

        int times_entry = achievement_login.get_value("new_entry");
        if (times_entry == 5)
        {
            Achievement a = new Achievement("Follow up Achievement!");
            AchievementQueue queue = new AchievementQueue();
            queue.add(a);
        }
        else if (times_entry == 100)
        {
            Achievement a = new Achievement("G33k Achievement!");
            AchievementQueue queue = new AchievementQueue();
            queue.add(a);
        }
    }
}
