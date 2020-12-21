package gameClient;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class loginFrame extends JFrame  {
    private static JPanel panel;
    private static JTextField id;
    private static JTextField level;
    private static String tz="";
    private static int l=-1;
    private static JButton login;


    @Override
    public void paintComponents(Graphics g) {
       super.paintComponents(g);
       drawBG();
       drawLogin(g);

    }

    public void listen()
    {
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tz=(id.getText());
                l=Integer.parseInt(level.getText());
            }
        });
    }
    private void drawBG() {

        setSize(400,200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(true);

    }

    private void drawLogin(Graphics g)
    {
        panel=new JPanel();

        panel.setLayout(null);
        panel.setBounds(getBounds());

        JLabel enterId=new JLabel("id:");
        enterId.setBounds(10,20,80,25);
        panel. add(enterId);

        JLabel enterLevel=new JLabel("level:");
        enterLevel.setBounds(10,50,80,25);
        panel.add(enterLevel);

        id=new JFormattedTextField();
        id.setBounds(80,20,165,25);
        panel.add(id);

        level=new JFormattedTextField();
        level.setBounds(80,50,165,25);
        panel.add(level);

        login=new JButton("Login");
        login.setBounds(10,80,80,25);



        panel.add(login);
        panel.setVisible(true);
        id.setVisible(true);
        level.setVisible(true);
        login.setVisible(true);add(panel);
        setVisible(true);
    }



    public static int getL() {
        return l;
    }

    public static String getTz() {
        return tz;
    }
}
