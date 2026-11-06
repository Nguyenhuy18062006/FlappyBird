package flappybird.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class LoginPanel extends JPanel {

    public static final int PANEL_WIDTH = 320;
    public static final int PANEL_HEIGHT = 380;

    private JTextField usernameField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;
    private JButton backButton;
    private JButton showPasswordButton;

    private JLabel errorLabel;
    private char defaultEcho;

    public LoginPanel() {

        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

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
        passwordField.setBounds(45, 175, 180, 35);
        add(passwordField);

        defaultEcho = passwordField.getEchoChar();

        showPasswordButton = new JButton("Xem");
        showPasswordButton.setFont(new Font("Arial", Font.PLAIN, 11));
        showPasswordButton.setMargin(new Insets(0, 0, 0, 0));
        showPasswordButton.setFocusable(false);
        showPasswordButton.setBounds(230, 175, 45, 35);
        add(showPasswordButton);

        showPasswordButton.addActionListener(e -> {
            boolean visible = passwordField.getEchoChar() == 0;
            passwordField.setEchoChar(visible ? defaultEcho : (char) 0);
            showPasswordButton.setText(visible ? "Xem" : "Ẩn");
        });

        // ================= ERROR =================
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBounds(25, 215, 270, 20);
        add(errorLabel);

        // ================= LOGIN =================
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setBounds(85, 240, 150, 35);
        add(loginButton);

        // ================= REGISTER =================
        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.PLAIN, 13));
        registerButton.setBounds(45, 282, 230, 25);
        add(registerButton);

        // ================= BACK =================
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setBounds(85, 315, 150, 35);
        add(backButton);

        // Nhấn Enter ở ô mật khẩu hoặc tên đăng nhập = bấm LOGIN
        usernameField.addActionListener(e -> loginButton.doClick());
        passwordField.addActionListener(e -> loginButton.doClick());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth() - 2;
        int h = getHeight() - 2;

        // ================= WHITE PANEL =================
        g2.setColor(new Color(255, 255, 255, 220));
        g2.fillRoundRect(0, 0, w, h, 20, 20);

        // ================= BORDER =================
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, w, h, 20, 20);

        g2.dispose();
    }

    // ================= VALIDATION =================

    public String getInputValidationMessage() {
        if (getUsername().isEmpty()) {
            return "Vui lòng nhập tên đăng nhập.";
        }

        if (getPassword().isEmpty()) {
            return "Vui lòng nhập mật khẩu.";
        }

        return "";
    }

    // ================= ERROR MESSAGE =================

    public void setErrorMessage(String message) {
        errorLabel.setText(message);
    }

    public void clearErrorMessage() {
        errorLabel.setText(" ");
    }

    // Xóa dữ liệu đã nhập, đưa form về trạng thái ban đầu
    public void reset() {
        usernameField.setText("");
        passwordField.setText("");
        passwordField.setEchoChar(defaultEcho);
        showPasswordButton.setText("Xem");
        clearErrorMessage();
    }

    // ================= GET DATA =================

    public String getUsername() {
        return usernameField.getText().trim();
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