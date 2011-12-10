package Panels;

import db.achievement;
import db.user;
import howest.dhert.svenn.AppHandler;
import howest.dhert.svenn.users;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import javax.swing.*;
import net.sf.jachievement.*;

/*
 *  trace   : AppHandler
 */
public class LoginScreen implements ActionListener 
{

    JLabel txt_result;
    JButton btn_new_user, btn_login;
    JFrame screen;
    JTextField u_login, u_pasw;

    /*
     * constructor
    */
    public LoginScreen (JFrame screen)
    {
        this.screen = screen;
    }
    
    /*  name    : show_login_screen
     *  use     : will make and show a login screen
     *  trace   : AppHandler -> if(!is_logged_in)
     */
    public JPanel show_login_screen() 
    {
        JPanel login_screen = new JPanel();

        JLabel txt_login = new JLabel("Name :");
            txt_login.setBounds(20, 10, 50, 15);

        JLabel txt_pasw = new JLabel("Password :");
            txt_pasw.setBounds(20, 40, 80, 15);

        txt_result = new JLabel("");
            txt_result.setBounds(230, 130, 120, 15);

        u_login = new JTextField();
            u_login.setBounds(130, 10, 100, 25);

        u_pasw = new JTextField();
            u_pasw.setBounds(130, 40, 100, 25);

        btn_login = new JButton("Login");
            btn_login.setBounds(130, 70, 100, 25);
        btn_login.addActionListener(this);
        btn_login.setActionCommand("login");
        
        btn_new_user = new JButton("New Account"); 
            btn_new_user.setBounds(70, 100, 160, 25);
        btn_new_user.addActionListener(this);
        btn_new_user.setActionCommand("new_account");


        // hardcoded layout
        login_screen.setLayout(null);
        
        // add image   
        try 
        {
            // add file from resource files
            URL resource_url = this.getClass().getClassLoader().getResource("Resource/login.jpg");
            
            JLabel picLabel = new JLabel(new ImageIcon(resource_url));
                picLabel.setBounds(300, 0, 100, 100);
            login_screen.add( picLabel );
        }
        catch(Exception x){System.out.println("show_login" +x.getMessage());}
                
        // add other stuff
        login_screen.add(txt_login);
        login_screen.add(txt_pasw);
        login_screen.add(txt_result);
        login_screen.add(u_login);
        login_screen.add(u_pasw);
        login_screen.add(btn_login);
        login_screen.add(btn_new_user);    
        
        return login_screen;
    }
    
    /*
     *  will attempt to register or login the user
     *  upon succes it will remove the login screen and
     *  open a new AppHandler with is_logged = true;
     */
    public void actionPerformed(ActionEvent e) 
    {
        // pull info from fields
        String name = u_login.getText();
        String pasw = u_pasw.getText();
        
        if (!name.isEmpty() && !pasw.isEmpty())
        {
            // database user
            user user = new user();

            // abstract users
            users current_user = new users();

            // attempt login
            if("login".equals(e.getActionCommand()))
            {
                current_user = user.user_attempt_login(name, pasw);
            }

            // attempt new account registration
            else if ("new_account".equals(e.getActionCommand()))
            {
                current_user = user.user_attempt_registration(name, pasw);
            }

            // login geslaagt 
            if (current_user.isLogged_in())
            {
                // niewe app, nu wel logged in
                AppHandler app = new AppHandler(true);

                // main
                app.main_screen(current_user);

                // remove login screen
                screen.dispose();

                // achievement
                achievement achievement_login = new achievement(current_user);
                achievement_login.update("login", 1);

                int times_login = achievement_login.get_value("login");
                if (times_login == 5)
                {
                    Achievement a = new Achievement("Ik vind deze applicatie leuk! (5times_login)");
                    AchievementQueue queue = new AchievementQueue();
                    queue.add(a);
                }
                else if (times_login == 10)
                {
                    Achievement a = new Achievement("Ik vind deze applicatie epic! (10times_login)");
                    AchievementQueue queue = new AchievementQueue();
                    queue.add(a);
                }
            }
            // something is not correct
            else
            {
                // wrong login
                if ("login".equals(e.getActionCommand()))
                {
                    txt_result.setText("Login failed.");
                }

                // error registration
                else
                {
                    txt_result.setText("Registration failed.");
                }
            }
        }
        else
        {
            txt_result.setText("Enter name/psw.");
        }

    }
}
