package Panels;

import Resource.*;
import db.dataset;
import howest.dhert.svenn.users;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
 *  trace   : MenuBar, NewtgPanel
 */
public class ManageDataPanel extends FloatHelper implements ActionListener 
{
    
    users current_user;
    JComboBox id;
    JTextField weight, length;
    JFrame screen_change, screen_delete;
    DefaultTableModel model;
    
    // constructor
    public ManageDataPanel(users user, DefaultTableModel model)
    {
        this.current_user   = user;
        this.model          = model;
    }
    
    /*
     *  name    : show_edit_screen
     *  trace   : menu(dataset)->edit or button
     *  will make the GUI for editing rows
     */
    public void show_edit_screen()
    {
        screen_change = new JFrame();
        new SetupScreen(250, 200, "Bewerk rij", screen_change);
        
            JPanel edit_screen = new JPanel(null);

                // add the remove/add functionality
                basic_layout(edit_screen);

            JButton edit = new JButton("Bewerk");
                edit.setBounds(120, 100, 100, 25);
                edit.addActionListener(this);
                edit.setActionCommand("edit");

                edit_screen.add(edit);
                
        screen_change.add(edit_screen);
        screen_change.setVisible(true);
    }
    
    /*
     *  name    : show_delete_screen
     *  trace   : menu(dataset)->remove or button
     *  will make the GUI for removing entry's
     */  
    public void show_delete_screen()
    {
        screen_delete = new JFrame();
        new SetupScreen(250, 200, "Verwijder rij", screen_delete);

        JPanel delete_screen = new JPanel(null);
        
            // add the remove/add functionality
            basic_layout(delete_screen);
            
            length.setEnabled(false); // on delete you cannot edit
            weight.setEnabled(false); // only visual for check
            
        JButton delete = new JButton("Verwijder");
            delete.setBounds(120, 100, 100, 25);
            delete.addActionListener(this);
            delete.setActionCommand("delete");
            delete_screen.add(delete);
            
        screen_delete.add(delete_screen);
        screen_delete.setVisible(true);
    }
    
    /*
     *  name    : basic_layout
     *  trace   : show_delete_screen() & show_edit_screen()
     *  will build the basic JCompo's
     */
    private void basic_layout(JPanel jp)
    {
        JLabel txt_id           = new JLabel("id : ");
            txt_id.setBounds(10, 10, 100, 25);
            
        JLabel txt_weigth       = new JLabel("Gewicht (kg) : ");
            txt_weigth.setBounds(10, 40, 100, 25);
            
        JLabel txt_length       = new JLabel("Lengte (m) : ");
            txt_length.setBounds(10, 70, 100, 25);
            
            
        id            = new JComboBox();
            // id's mee geven die kunnen worden bewerkt/verwijderd
            dataset dt = new dataset();
            
            // start waarde
            id.addItem("-");
            
            // [] => (database_key, rowcount_key)
            int[] x = dt.get_id_list(current_user.getId());
            int t = 1;
            for (int i : x)
            {
                id.addItem(new JComboBoxHelper("# " + Integer.toString(t), Integer.toString(i)));
                t++;
            }
            id.addActionListener(this);
            id.setActionCommand("change_id");
            
        id.setBounds(120, 10, 100, 25);
       
        weight       = new JTextField();
            weight.setBounds(120, 40, 100, 25);
        
        length       = new JTextField();
            length.setBounds(120, 70, 100, 25);
            
        jp.add(txt_id, BorderLayout.CENTER);
        jp.add(txt_weigth, BorderLayout.CENTER);
        jp.add(txt_length, BorderLayout.CENTER);
        jp.add(id, BorderLayout.CENTER);
        jp.add(weight, BorderLayout.CENTER);
        jp.add(length, BorderLayout.CENTER);
    }

    /*
     *      <delete>
     *      will attempt to remove a row from database
     *      if succes it will remove it will attempt to remove
     *      the entry from the JTable, using the model (defaulttablemodel)
     *      this uses the JComboBoxHelper
     *      
     *      <edit>
     *      will attempt to addapt a row from database
     *      if succes it will remove the row from table,
     *      and add the know values in the table this will make
     *      a de-synch of the previous and next table on "df" fields
     */
    public void actionPerformed(ActionEvent e) {
        
        // if empty set then this might fail
        String selected_value = ((JComboBoxHelper) id.getSelectedItem()).getValue();
       
        dataset dt = new dataset();
        
        if (e.getActionCommand().equals("change_id") && !"-".equals(selected_value))
        {            
                float[] x = dt.get_values_for(Integer.parseInt(selected_value));
      
                    weight.setText(Float.toString(x[0]));
                    length.setText(Float.toString(x[1]));
        }
        else if (e.getActionCommand().equals("delete") && !"-".equals(selected_value))
        {
             dt.remove_id(Integer.parseInt(selected_value));
             model.removeRow(id.getSelectedIndex() -1 );
                     
             // remove delete screen
             screen_delete.dispose();
        }
        else if (e.getActionCommand().equals("edit") && !"-".equals(selected_value))
        {
             float vweight = Float.parseFloat(weight.getText());
             float vlength = Float.parseFloat(length.getText());
             
             dt.edit_id(vweight, vlength, Integer.parseInt(selected_value));
            
             int row = id.getSelectedIndex() -1;
             
             String[] data = new String[8];
                 data[0] = Integer.toString(row + 1);
                 data[1] = Float.toString(vweight);
                 data[3] = Float.toString(vlength);
                 data[5] = Float.toString(round2(vweight/(vlength*vlength)));

             model.removeRow( row );
             model.insertRow( row, data);
             
             // remove edit screen
             screen_change.dispose();
        }
    }
    
}
