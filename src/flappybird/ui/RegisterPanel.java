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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public class RegisterPanel extends JPanel {

    // Giữ 380 để bằng LoginPanel và khung Menu, không cần sửa kích thước ở GamePanel.
    public static final int PANEL_WIDTH = 320;
    public static final int PANEL_HEIGHT = 380;

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;

    private JButton registerButton;
    private JButton loginButton;
    private JButton backButton;

    private JLabel passwordStrengthLabel;
    private JLabel errorLabel;
    private JButton showPasswordButton;
    private JButton showConfirmPasswordButton;
    private char defaultEcho;

    public RegisterPanel() {
        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // ================= TITLE =================
        JLabel title = new JLabel("REGISTER");
        title.setFont(new Font("Arial", Font.BOLD, 26));
        title.setForeground(Color.BLACK);
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(60, 8, 200, 36);
        add(title);

        // ================= USERNAME =================
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.BOLD, 13));
        usernameLabel.setBounds(45, 46, 150, 20);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBounds(45, 66, 230, 30);
        add(usernameField);

        // ================= PASSWORD =================
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 13));
        passwordLabel.setBounds(45, 100, 150, 20);
        add(passwordLabel);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBounds(45, 120, 180, 30);
        add(passwordField);

        showPasswordButton = createShowButton();
        showPasswordButton.setBounds(230, 120, 45, 30);
        add(showPasswordButton);

        // ================= PASSWORD STRENGTH =================
        passwordStrengthLabel = new JLabel("Độ mạnh mật khẩu: Chưa nhập");
        passwordStrengthLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        passwordStrengthLabel.setForeground(Color.GRAY);
        passwordStrengthLabel.setBounds(45, 151, 230, 16);
        add(passwordStrengthLabel);

        passwordField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updatePasswordStrength();
            }
        });

        // ================= CONFIRM PASSWORD =================
        JLabel confirmLabel = new JLabel("Confirm Password");
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 13));
        confirmLabel.setBounds(45, 169, 180, 20);
        add(confirmLabel);

        confirmPasswordField = new JPasswordField();
        confirmPasswordField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPasswordField.setBounds(45, 189, 180, 30);
        add(confirmPasswordField);

        showConfirmPasswordButton = createShowButton();
        showConfirmPasswordButton.setBounds(230, 189, 45, 30);
        add(showConfirmPasswordButton);

        // ================= SHOW / HIDE PASSWORD =================
        defaultEcho = passwordField.getEchoChar();

        showPasswordButton.addActionListener(e -> {
            boolean visible = passwordField.getEchoChar() == 0;
            passwordField.setEchoChar(visible ? defaultEcho : (char) 0);
            showPasswordButton.setText(visible ? "Xem" : "Ẩn");
        });

        showConfirmPasswordButton.addActionListener(e -> {
            boolean visible = confirmPasswordField.getEchoChar() == 0;
            confirmPasswordField.setEchoChar(visible ? defaultEcho : (char) 0);
            showConfirmPasswordButton.setText(visible ? "Xem" : "Ẩn");
        });

        // ================= ERROR =================
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        errorLabel.setForeground(Color.RED);
        errorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        errorLabel.setBounds(25, 222, 270, 18);
        add(errorLabel);

        // ================= REGISTER =================
        registerButton = new JButton("REGISTER");
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        registerButton.setBounds(85, 244, 150, 34);
        add(registerButton);

        // ================= LOGIN =================
        loginButton = new JButton("LOGIN");
        loginButton.setFont(new Font("Arial", Font.PLAIN, 13));
        loginButton.setBounds(45, 284, 230, 26);
        add(loginButton);

        // ================= BACK =================
        backButton = new JButton("BACK");
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setBounds(85, 318, 150, 32);
        add(backButton);
    }

    private JButton createShowButton() {
        JButton button = new JButton("Xem");
        button.setFont(new Font("Arial", Font.PLAIN, 11));
        button.setMargin(new Insets(0, 0, 0, 0));
        button.setFocusable(false);
        return button;
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

    private void updatePasswordStrength() {
        String password = getPassword();

        if (password.isEmpty()) {
            passwordStrengthLabel.setText("Độ mạnh mật khẩu: Chưa nhập");
            passwordStrengthLabel.setForeground(Color.GRAY);
            return;
        }

        int score = 0;

        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*[0-9].*")) score++;
        if (password.matches(".*[^a-zA-Z0-9].*")) score++;

        if (score <= 2) {
            passwordStrengthLabel.setText("Độ mạnh mật khẩu: Yếu");
            passwordStrengthLabel.setForeground(Color.RED);
        } else if (score <= 4) {
            passwordStrengthLabel.setText("Độ mạnh mật khẩu: Trung bình");
            passwordStrengthLabel.setForeground(new Color(190, 125, 0));
        } else {
            passwordStrengthLabel.setText("Độ mạnh mật khẩu: Mạnh");
            passwordStrengthLabel.setForeground(new Color(20, 135, 65));
        }
    }

    // ================= VALIDATION =================

    public String getUsernameValidationMessage() {
        if (getUsername().isEmpty()) {
            return "Vui lòng nhập tên đăng nhập.";
        }

        return "";
    }

    public String getPasswordValidationMessage() {
        String password = getPassword();

        if (password.length() < 8) {
            return "Mật khẩu phải có ít nhất 8 ký tự.";
        }
        if (!password.matches(".*[A-Z].*")) {
            return "Cần ít nhất 1 chữ hoa.";
        }
        if (!password.matches(".*[a-z].*")) {
            return "Cần ít nhất 1 chữ thường.";
        }
        if (!password.matches(".*[0-9].*")) {
            return "Cần ít nhất 1 chữ số.";
        }
        if (!password.matches(".*[^a-zA-Z0-9].*")) {
            return "Cần ít nhất 1 ký tự đặc biệt.";
        }

        return "";
    }

    public boolean isPasswordStrong() {
        return getPasswordValidationMessage().isEmpty();
    }

    public String getConfirmPasswordValidationMessage() {
        if (getConfirmPassword().isEmpty()) {
            return "Vui lòng xác nhận mật khẩu.";
        }

        if (!getPassword().equals(getConfirmPassword())) {
            return "Xác nhận mật khẩu không đúng.";
        }

        return "";
    }

    // Xóa dữ liệu đã nhập, đưa form về trạng thái ban đầu
    public void reset() {
        usernameField.setText("");
        passwordField.setText("");
        confirmPasswordField.setText("");
        passwordField.setEchoChar(defaultEcho);
        confirmPasswordField.setEchoChar(defaultEcho);
        showPasswordButton.setText("Xem");
        showConfirmPasswordButton.setText("Xem");
        clearErrorMessage();
    }

    // ================= ERROR MESSAGE =================

    public void setErrorMessage(String message) {
        errorLabel.setText(message);
    }

    public void clearErrorMessage() {
        errorLabel.setText(" ");
    }

    // ================= GET DATA =================

    public String getUsername() {
        return usernameField.getText().trim();
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