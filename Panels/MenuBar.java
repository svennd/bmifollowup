package Panels;

import grafiek.*;
import howest.dhert.svenn.AppHandler;
import howest.dhert.svenn.users;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/*
 *  trace   : AppHandler
 */
public class MenuBar implements ActionListener 
{
    
    public JFrame screen;
    public users current_user;
    public DefaultTableModel model;
    
    /*
     *  makes a menubar we need :
     *  screen for file(logout, exit)
     *  current_user for dataset(edit, remove)
     *  model for dataset(edit, remove)
     */
    public MenuBar(JFrame screen, users current_user, DefaultTableModel model)
    {
        this.screen         = screen;
        this.current_user   = current_user;
        this.model          = model;
    }
    
    /*
     * makes a JMenuBar
     * structure : JMenuBar -> JMenu -> JMenuItem
     */
    public JMenuBar menu_lijst()
    {
        JMenuBar jmb = new JMenuBar();
        
        // menu
        // file
        JMenu file = new JMenu("File");
        
            // file - logout
            JMenuItem logout = new JMenuItem("logout");
                logout.addActionListener(this);
                logout.setActionCommand("logout");
            
            // file - exit
            JMenuItem exit = new JMenuItem("exit");
                exit.addActionListener(this);
                exit.setActionCommand("exit");
                
            file.add(logout);
            file.add(exit);
        
        // dataset
        JMenu dtset = new JMenu("Dataset");
        
            // grafieken
            JMenuItem delete         = new JMenuItem("Verwijder Rij");
                delete.addActionListener(this);
                delete.setActionCommand("delete_value");    
                
            JMenuItem edit      = new JMenuItem("Bewerk Rij");
                edit.addActionListener(this);
                edit.setActionCommand("edit_value");    
                 
        dtset.add(delete);
        dtset.add(edit);
        
        // grafieken
        JMenu graphs = new JMenu("Grafiek");
        
            // grafieken
            JMenuItem bmi_graph         = new JMenuItem("BMI Grafiek");
                bmi_graph.addActionListener(this);
                bmi_graph.setActionCommand("bmi_grafiek");    
                
            JMenuItem weight_graph      = new JMenuItem("Gewicht Grafiek");
                weight_graph.addActionListener(this);
                weight_graph.setActionCommand("gewicht_grafiek");    
        
        graphs.add(bmi_graph);
        graphs.add(weight_graph);
                
        // help
        JMenu help = new JMenu("Help");
            
            // help - about
             JMenuItem about = new JMenuItem("About");
                about.addActionListener(this);
                about.setActionCommand("about");    
         help.add(about);
        
        // add to top layer
          jmb.add(file);
          jmb.add(dtset);
          jmb.add(graphs);
          jmb.add(help);
        return jmb;
    }
    
    /*  
     *  logout          : remove screen, start !logged_in screen
     *  exit            : remove screen
     *  about           : call show_aboutscreen();
     *  edit_values     : call show_edit_screen();
     *  delete_values   : call show_delete_screen();
     *  bmi_grafiek     : call show_chart();
     *  gewicht_grafiek : call show_chart();
     * 
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("logout"))
        {
            screen.dispose();
            
            AppHandler app = new AppHandler(false);
        }
        
        else if (e.getActionCommand().equals("exit"))
        {
            screen.dispose();
        }
        
        else if (e.getActionCommand().equals("about"))
        {
            AboutPanel ap = new AboutPanel();
            ap.show_aboutscreen();
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
        
        else if (e.getActionCommand().equals("bmi_grafiek"))
        {
            bmi x = new bmi();
            x.show_chart(current_user.getId());
        }
        
        else if (e.getActionCommand().equals("gewicht_grafiek"))
        {
            weight x = new weight();
            x.show_chart(current_user.getId());
        }

    }
}
