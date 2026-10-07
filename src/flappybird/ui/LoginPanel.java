package flappybird.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;
    private JButton backButton;

    public LoginPanel() {
        setLayout(null);
        setOpaque(false);

        // ================= TITLE =================
        JLabel title = new JLabel("LOGIN");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(60, 25, 200, 40);
        add(title);

        // ================= USERNAME =================
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setBounds(45, 80, 150, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(45, 105, 230, 35);
        add(usernameField);

        // ================= PASSWORD =================
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setBounds(45, 150, 150, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(45, 175, 230, 35);
        add(passwordField);

        // ================= LOGIN =================
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBounds(85, 225, 150, 35);
        add(loginButton);

        // ================= REGISTER =================
        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 13));
        registerButton.setBounds(45, 265, 230, 25);
        add(registerButton);

        // ================= BACK =================
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setBounds(85, 305, 150, 35);
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // ================= WHITE PANEL =================
        g2.setColor(new Color(255, 255, 255, 220));

        g2.fillRoundRect( 0,0,320,380,20,20);

        // ================= BORDER =================
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        g2.drawRoundRect( 0,0,320,380,20,20);

        g2.dispose();
    }

    // ================= GET DATA =================

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    // ================= GET BUTTONS =================

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}