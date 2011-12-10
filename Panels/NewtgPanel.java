package Panels;

import com.michaelbaranov.microba.calendar.DatePicker;
import db.target;
import howest.dhert.svenn.users;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import net.sf.jachievement.*;

/*
 *  trace   : AppHandler
 */
public class NewtgPanel implements ActionListener 
{
    
    public JComboBox target_combo;
    public JTextField target_weight_bmi;
    public DatePicker kies;
    public JLabel target_value;
    public users current_user;
    public DefaultTableModel model;
   
    // constructor
    public NewtgPanel (users current_user, DefaultTableModel model)
    {
        this.current_user   = current_user;
        this.model = model;
    }
    
    /*
     *  name    : show_newtarget_screen()
     *  use     : make a panel to add a new target to screen
     *  trace   : AppHanlder
     */
    public JPanel show_newtarget_screen()
    {
        JPanel new_target = new JPanel(null);
        
        JLabel tx_new_target = new JLabel("Nieuw Target");
                tx_new_target.setFont(new Font("Dialog", Font.PLAIN, 24));
                tx_new_target.setBounds(80, 0, 400, 100);
                
        JLabel txt_new_target = new JLabel("Target");
            txt_new_target.setBounds(60, 80, 80, 25);
        
        target_combo = new JComboBox(new String[]{"BMI", "Gewicht"});
            target_combo.setSelectedIndex(1);
            target_combo.setBounds(140, 80, 100, 25); // 340
            target_combo.addActionListener(this);
            target_combo.setActionCommand("change_text");
        
        target_value = new JLabel("Gewicht (Kg)");
                target_value.setBounds(60, 110, 100, 25);
        
        target_weight_bmi = new JTextField();
            target_weight_bmi.setBounds(140, 110, 100, 25);
        
        JLabel tegen_date = new JLabel("Tegen :");
                tegen_date.setBounds(60, 140, 100, 25);
        
        kies = new DatePicker();
            kies.setBounds(140, 140, 120, 25);
            
        JButton addtarget = new JButton("add");
            addtarget.setBounds(140, 170, 80, 25);
            addtarget.addActionListener(this);
            addtarget.setActionCommand("add_target");
            
       JButton edit_value      = new JButton("Bewerk rij");
            edit_value.setBounds(150, 240, 120, 25);
            edit_value.addActionListener(this);
            edit_value.setActionCommand("edit_value");    
            
        JButton delete_value    = new JButton("Verwijder rij");
            delete_value.setBounds(20, 240, 120, 25);
            delete_value.addActionListener(this);
            delete_value.setActionCommand("delete_value");   
            
        new_target.add(edit_value);
        new_target.add(delete_value);
        
        new_target.add(txt_new_target);
        new_target.add(tx_new_target);
        new_target.add(target_combo);
        new_target.add(target_value);
        new_target.add(target_weight_bmi);
        new_target.add(tegen_date);
        new_target.add(kies);
        new_target.add(addtarget);
        
        return new_target;
    }
    
    /*
     *  <add_targt>
     *  will attempt to add a new target
     *  on negativ date it will give GUI update & achievement
     *  on 0 target it will give only a achievement
     *  on + date and + target it will update GUI and save it to database
     *  
     *  <change>
     *  if JComboBox switches, the text will also switch to the appropiote text
     *  
     *  <edit/delete>
     *  will call ManageDataPanel -> show_delete_screen(), show_edit_screen()
     */
    public void actionPerformed(ActionEvent e)
    {
        if (e.getActionCommand().equals("add_target"))
        {
            Date date = kies.getDate();
            long target_date = date.getTime();
            target target = new target();

            try
            {
                String target_bmi_gewicht_value = target_weight_bmi.getText();
                if (!target_bmi_gewicht_value.isEmpty())
                {
                    float value = Float.parseFloat(target_bmi_gewicht_value);
                    target.add_target(target_date, target_combo.getSelectedIndex(), value, current_user.getId());

                    // update targetscreen
                    TargetPanel label_update = new TargetPanel(current_user);
                    label_update.update_target_screen(current_user.getId());
                } else
                {
                    Achievement a = new Achievement("Dum dum achievement!");
                    AchievementQueue queue = new AchievementQueue();
                    queue.add(a);
                }
            } catch (Exception ex){System.out.println("newtgPanel" + ex.getMessage());}
            
        } 
        else if (e.getActionCommand().equals("change_text"))
        {
            JComboBox cb = (JComboBox) e.getSource();
            String newSelected = (String) cb.getSelectedItem();

            if (newSelected.equals("BMI"))
            {
                target_value.setText("BMI");
            } else
            {
                target_value.setText("Gewicht (Kg)");
            }
        } 
        else if (e.getActionCommand().equals("edit_value"))
        {
            ManageDataPanel mg = new ManageDataPanel(current_user, model);
            mg.show_edit_screen();
        } 
        else if (e.getActionCommand().equals("delete_value"))
        {
            ManageDataPanel mg = new ManageDataPanel(current_user, model);
            mg.show_delete_screen();
        }
    }
}
