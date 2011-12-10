package Resource;

import javax.swing.JFrame;

/*
 *  name    : SetupScreen
 *  use     : will set basic screen settings (not really propper way :P)
 *  trace   : AppHandler, MenuBar
 */
public class SetupScreen
{
        
        public SetupScreen(int w, int h, String screentitle, JFrame screen)
        {
            // set screen
            screen.setSize(w, h);
            screen.setTitle(screentitle);
            screen.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // enables dispose();
            screen.setLocationRelativeTo(null);
        }
}
