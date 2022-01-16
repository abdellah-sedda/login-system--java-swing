package login.sys;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


public class Authentification extends JFrame implements ActionListener{
    
    File loginInfo;
    static boolean logU = false;
    private boolean logP = false;
    Scanner scanner;
    static String loginStr,passwordStr;
    JTextField loginField;
    JPasswordField passwordField;
    JPanel panel;
            
    Authentification(){
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(300, 300, 700, 500);
        getContentPane().setBackground(Color.BLUE);
        
        ajoutPanelToFrame();
        
        setLayout(new GridBagLayout());
        setVisible(true);
    }
    
    
    private void ajoutPanelToFrame() {
        
        panel = new JPanel();
        panel.setPreferredSize(new Dimension(350, 150));
        panel.setLayout(new FlowLayout(FlowLayout.TRAILING, 15, 20));
        
        ajoutElementToPanel();
        
        add(panel);
    }

    private void ajoutElementToPanel() {
        JLabel login = new JLabel("Login:");
        loginField = new JTextField(20);
        JLabel password = new JLabel("Password:");
        passwordField = new JPasswordField(20);
        
        JButton button = new JButton("Log in");
        button.addActionListener(this);
        
        panel.add(login);
        panel.add(loginField);
        panel.add(password);
        panel.add(passwordField);
        panel.add(button);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        verfyInfo();
        
        if (logU == true & logP == true) {
            new Espace();
            dispose();
        } else if (logU == false & logP == false) {
            JOptionPane.showMessageDialog(null, "l'utilisateur n'existè pas.", "Vous ne pouvez pas vous connecter", JOptionPane.ERROR_MESSAGE);
        } else if (logU == false) {
            JOptionPane.showMessageDialog(null, "le nom d'utilisateur est faux.", "Vous ne pouvez pas vous connecter", JOptionPane.ERROR_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(null, "le mot de pass est faux.", "Vous ne pouvez pas vous connecter", JOptionPane.ERROR_MESSAGE);
        }
               
    }
    
    private void verfyInfo(){        
        
        loginStr = loginField.getText();
        passwordStr = passwordField.getText();
        
        loginInfo = new File("comptes");
        //j'utilise linux c'est pour ça le path de fichier est sans extansion (.txt)
        
        try {
            scanner = new Scanner(loginInfo);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Authentification.class.getName()).log(Level.SEVERE, null, ex);
        }
        while (scanner.hasNextLine()) {
                String tmp1 = scanner.nextLine();
                if (loginStr.equals(tmp1)) {
                    logU = true;
                }
                String tmp2 = scanner.nextLine();
                if (passwordStr.equals(tmp2)) {
                    logP = true;
                }
        }
          
    }
    

}