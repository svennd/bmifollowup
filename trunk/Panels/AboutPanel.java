package Panels;

import Resource.SetupScreen;
import javax.swing.JFrame;
import javax.swing.JLabel;

/*
 *  trace   : MenuBar
 */
public class AboutPanel 
{
    /* name     : show_aboutscreen 
     * use      : will call about screen
     * trace    : menu(help) -> about
     */
    public void show_aboutscreen()
    {
            JFrame  about = new JFrame();
            new SetupScreen(400, 400, "About : Svenn", about);

                JLabel bron = new JLabel("<html>Special thx to the Interwebz! <br>"
                        + "microba (http://microba.sourceforge.net/),<br> "
                        + "jFreechars (www.jfree.org/jfreechart/),<br> "
                        + "JAchievement (http://jachievement.sourceforge.net/),<br> "
                        + "</html>");
                bron.setBounds(40, 10, 300, 300);
                about.add(bron);

            about.setVisible(true);
            
    }
}


