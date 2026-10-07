package flappybird.ui;

import java.awt.*;
import javax.swing.*;

public class RegisterPanel extends JPanel {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton registerButton;
    private JButton loginButton;
    private JButton backButton;

    public RegisterPanel() {
        setLayout(null);
        setOpaque(false);

        // ================= TITLE =================
        JLabel title = new JLabel("REGISTER");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(60, 15, 200, 40);
        add(title);

        // ================= USERNAME =================
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        usernameLabel.setBounds(45, 65, 150, 25);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(45, 90, 230, 35);
        add(usernameField);

        // ================= PASSWORD =================
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 14));
        passwordLabel.setBounds(45, 135, 150, 25);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(45, 160, 230, 35);
        add(passwordField);

        // ================= CONFIRM PASSWORD =================
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmLabel.setBounds(45, 205, 180, 25);
        add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setBounds(45, 230, 230, 35);
        add(confirmPasswordField);

        // ================= REGISTER =================
        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBounds(85, 275, 150, 35);
        add(registerButton);

        // ================= LOGIN =================
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 13));
        loginButton.setBounds(45, 315, 230, 25);
        add(loginButton);

        // ================= BACK =================
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setBounds(85, 345, 150, 30);
        add(backButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        // ================= WHITE PANEL =================
        g2.setColor(new Color(255, 255, 255, 220));

        g2.fillRoundRect(
                0,
                0,
                320,
                380,
                20,
                20
        );

        // ================= BORDER =================
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));

        g2.drawRoundRect(
                0,
                0,
                320,
                380,
                20,
                20
        );

        g2.dispose();
    }

    // ================= GET DATA =================

    public String getUsername() {
        return usernameField.getText();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    // ================= GET BUTTONS =================

    public JButton getRegisterButton() {
        return registerButton;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}