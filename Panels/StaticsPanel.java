package Panels;

import db.dataset;
import howest.dhert.svenn.users;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JLabel;
import javax.swing.JPanel;

/*
 *  trace   : AppHandler
 */
public class StaticsPanel 
{
    JPanel target_screen;
    users current_user;
    
    // constructor
    public StaticsPanel(users user)
    {
        this.current_user = user;
    }
    
    /*
     *  name    : show_statics_screen
     *  use     : will show data pulled from db.dataset->get_statics
     *  trace   : AppHandler
     */
    public JPanel show_statics_screen()
    {
            target_screen = new JPanel(null);

                JLabel txt_targedd = new JLabel("Statistieken");
            txt_targedd.setFont(new Font("Dialog", Font.PLAIN, 24));
            txt_targedd.setBounds(20, 0, 150, 30);
                target_screen.add(txt_targedd);

           dataset dt = new dataset();
           String[] stat = dt.get_statics(current_user.getId());
           
            
           SimpleDateFormat formatter = new SimpleDateFormat("EEE. d MMM yy");
           String st = "Uw maximaal gewicht was, " + stat[0] + "kg."
                   + " Uw minimaal gewicht " + stat[1] + "kg <br/>"
                   + "Uw eerste entry was op " + formatter.format(new Date(Long.parseLong(stat[4]))) + " <br/>"
                   + " aantal entrys " + stat[6] + " <br/>"
                   ;
           
                JLabel all_stats = new JLabel("<html>" + st + "</html>");
                    all_stats.setBounds(20, 0, 150, 150);
                target_screen.add(all_stats);
                
        return target_screen;
    }
}
