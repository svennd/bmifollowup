package howest.dhert.svenn;

import Panels.TargetPanel;
import Panels.LoginScreen;
import Panels.MenuBar;
import Panels.NewdpPanel;
import Panels.NewtgPanel;
import Panels.StaticsPanel;
import Panels.TablePanel;
import Resource.SetupScreen;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.table.DefaultTableModel;

public class AppHandler {

    public boolean logged_in = false;
    public JFrame login_screen, main_screen;
    public DefaultTableModel model;
   

    public AppHandler( boolean logged_in ) {
        
        // logged_in = true enkel als dit is gezet.
        this.logged_in = logged_in;
        
        login_screen = new JFrame();
        if (!logged_in)
        {
            login_screen(login_screen);
        }
    }

    public void login_screen(JFrame screen) {

        // add screen setup
        new SetupScreen(400, 200, "Login Screen", screen);

        // add LoginScreen panel
        LoginScreen alfa = new LoginScreen(screen);
        
        // get the valid JPanel & add it && set background color
        Container l_s = alfa.show_login_screen();
            l_s.setBackground(Color.white);
        screen.add(l_s);
            
        // show screen
        screen.setVisible(true);
    }

    public void main_screen(users current_user){
        
        // make new JFrame
        main_screen = new JFrame();
        
        // model declaration for model 
        model = new DefaultTableModel();    

        // add screen setup
        new SetupScreen(850, 600, "Main Screen", main_screen);

        // add menu bar
        MenuBar bar = new MenuBar(main_screen, current_user, model);
        
        main_screen.setJMenuBar(bar.menu_lijst());
               
        // dividing our main screen using JPanel
        // --P--
        // --P--
        JPanel main_jpanel = new JPanel(new GridLayout(2,1));
        
            // bottom screen
            // -P- -P- -P-
            JPanel bottom_panel = new JPanel (new GridLayout(1, 3));
            
                // statics panel
                StaticsPanel statics        = new StaticsPanel(current_user);
                    bottom_panel.add(statics.show_statics_screen());
                    
                // new datapoint panel
                NewdpPanel newdp            = new NewdpPanel(current_user, model);
                    bottom_panel.add(newdp.show_newdatapoint_screen());
                    
                // new target panel
                NewtgPanel newrg            = new NewtgPanel(current_user, model);
                    bottom_panel.add(newrg.show_newtarget_screen());
            
            // top
            // -P-  ----P----
            JPanel top_panel    = new JPanel(new BorderLayout());
            
                // target panel
                TargetPanel target      = new TargetPanel(current_user);
                    JPanel targetp  = target.show_target_screen();
                    
                    // add to panel
                    top_panel.add(targetp, BorderLayout.WEST);
                
                // tabel
                TablePanel table        = new TablePanel(current_user, model);
                    JPanel tablep = table.show_table_screen();
                    
                    // add to panel
                    top_panel.add(tablep, BorderLayout.EAST);
            
                    
           // add top & bottom panel to main panel 
           main_jpanel.add(top_panel);
           main_jpanel.add(bottom_panel);
        
        // add main JPanel to screen
        main_screen.add(main_jpanel);
        
        // show screen
        main_screen.setVisible(true);
    }
    
}
