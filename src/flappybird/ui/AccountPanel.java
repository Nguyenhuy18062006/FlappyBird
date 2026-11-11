package flappybird.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import flappybird.model.Score;
import flappybird.model.User;

/*
 * Màn hình ACCOUNT - nơi thể hiện đủ CRUD cho user đang đăng nhập:
 *   READ   : bảng lịch sử các lượt chơi
 *   DELETE : xóa 1 lượt chơi được chọn
 *   UPDATE : đổi mật khẩu
 *   DELETE : xóa tài khoản
 * (CREATE nằm ở màn hình Đăng ký và khi lưu điểm sau mỗi ván.)
 *
 * Panel này CHỈ lo hiển thị + lấy dữ liệu người dùng nhập.
 * Việc gọi database do GamePanel -> Game -> Service -> DAO đảm nhận.
 */
public class AccountPanel extends JPanel {

    public static final int PANEL_WIDTH = 600;
    public static final int PANEL_HEIGHT = 450;

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    private final JLabel infoLabel;

    private final DefaultTableModel tableModel;
    private final JTable scoreTable;

    // Giữ danh sách Score đang hiển thị để biết dòng được chọn ứng với id nào trong DB
    private List<Score> currentScores = new ArrayList<>();

    private final JPasswordField oldPasswordField;
    private final JPasswordField newPasswordField;
    private final JPasswordField confirmPasswordField;

    private final JButton deleteScoreButton;
    private final JButton changePasswordButton;
    private final JButton deleteAccountButton;
    private final JButton backButton;

    private final JLabel messageLabel;

    public AccountPanel() {
        setLayout(null);
        setOpaque(false);
        setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        // ================= TITLE =================
        JLabel title = new JLabel("ACCOUNT");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        title.setBounds(0, 12, PANEL_WIDTH, 38);
        add(title);

        infoLabel = new JLabel(" ");
        infoLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        infoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        infoLabel.setBounds(0, 50, PANEL_WIDTH, 22);
        add(infoLabel);

        // ================= LỊCH SỬ CHƠI (READ) =================
        JLabel historyLabel = new JLabel("Lịch sử chơi");
        historyLabel.setFont(new Font("Arial", Font.BOLD, 14));
        historyLabel.setBounds(20, 75, 200, 20);
        add(historyLabel);

        String[] columns = {"#", "ĐIỂM", "THỜI GIAN"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        scoreTable = new JTable(tableModel);
        scoreTable.setFont(new Font("Arial", Font.PLAIN, 13));
        scoreTable.setRowHeight(24);
        scoreTable.setFillsViewportHeight(true);
        scoreTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        scoreTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        scoreTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        scoreTable.getColumnModel().getColumn(1).setPreferredWidth(70);
        scoreTable.getColumnModel().getColumn(2).setPreferredWidth(200);

        JScrollPane scrollPane = new JScrollPane(scoreTable);
        scrollPane.setBounds(20, 97, 320, 228);
        add(scrollPane);

        // ================= XÓA LƯỢT CHƠI (DELETE) =================
        deleteScoreButton = new JButton("XÓA LƯỢT CHƠI ĐÃ CHỌN");
        deleteScoreButton.setFont(new Font("Arial", Font.BOLD, 13));
        deleteScoreButton.setBounds(20, 332, 320, 30);
        add(deleteScoreButton);

        // ================= ĐỔI MẬT KHẨU (UPDATE) =================
        JLabel changeLabel = new JLabel("Đổi mật khẩu");
        changeLabel.setFont(new Font("Arial", Font.BOLD, 14));
        changeLabel.setBounds(360, 75, 220, 20);
        add(changeLabel);

        oldPasswordField = addPasswordRow("Mật khẩu hiện tại", 100);
        newPasswordField = addPasswordRow("Mật khẩu mới", 152);
        confirmPasswordField = addPasswordRow("Nhập lại mật khẩu mới", 204);

        changePasswordButton = new JButton("ĐỔI MẬT KHẨU");
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 13));
        changePasswordButton.setBounds(360, 260, 220, 30);
        add(changePasswordButton);

        // ================= XÓA TÀI KHOẢN (DELETE) =================
        deleteAccountButton = new JButton("XÓA TÀI KHOẢN");
        deleteAccountButton.setFont(new Font("Arial", Font.BOLD, 13));
        deleteAccountButton.setForeground(new Color(190, 0, 0));
        deleteAccountButton.setBounds(360, 332, 220, 30);
        add(deleteAccountButton);

        // ================= THÔNG BÁO =================
        messageLabel = new JLabel(" ");
        messageLabel.setFont(new Font("Arial", Font.PLAIN, 13));
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        messageLabel.setBounds(20, 368, 560, 22);
        add(messageLabel);

        // ================= BACK =================
        backButton = new JButton("BACK TO MENU");
        backButton.setFont(new Font("Arial", Font.BOLD, 13));
        backButton.setBounds(210, 398, 180, 35);
        add(backButton);

        // Enter ở ô cuối = bấm ĐỔI MẬT KHẨU
        confirmPasswordField.addActionListener(e -> changePasswordButton.doClick());
    }

    // Tạo 1 cặp (nhãn + ô mật khẩu) cho gọn, tránh lặp code
    private JPasswordField addPasswordRow(String text, int y) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.PLAIN, 13));
        label.setBounds(360, y, 220, 18);
        add(label);

        JPasswordField field = new JPasswordField();
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBounds(360, y + 19, 220, 28);
        add(field);
        return field;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int w = getWidth() - 2;
        int h = getHeight() - 2;

        g2.setColor(new Color(255, 255, 255, 220));
        g2.fillRoundRect(0, 0, w, h, 20, 20);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(0, 0, w, h, 20, 20);

        g2.dispose();
    }

    // ================= ĐỔ DỮ LIỆU VÀO MÀN HÌNH =================

    public void loadData(User user, List<Score> scores) {
        infoLabel.setText("Người chơi: " + user.getUserName() + "   |   Kỷ lục: " + user.getHighestScore());

        currentScores = scores;
        tableModel.setRowCount(0);

        if (scores.isEmpty()) {
            tableModel.addRow(new Object[]{"-", "-", "Chưa có lượt chơi nào"});
            return;
        }

        int index = 1;
        for (Score score : scores) {
            tableModel.addRow(new Object[]{
                index++,
                score.getScore(),
                score.getCreateAt() == null ? "" : score.getCreateAt().format(TIME_FORMAT)
            });
        }
    }

    public void showLoadError() {
        currentScores = new ArrayList<>();
        tableModel.setRowCount(0);
        tableModel.addRow(new Object[]{"!", "-", "Không tải được dữ liệu"});
    }

    // Trả về id (trong DB) của lượt chơi đang chọn, -1 nếu chưa chọn
    public int getSelectedScoreId() {
        int row = scoreTable.getSelectedRow();
        if (row < 0 || row >= currentScores.size()) {
            return -1;
        }
        return currentScores.get(row).getId();
    }

    // ================= KIỂM TRA FORM ĐỔI MẬT KHẨU =================

    // Trả về "" nếu hợp lệ, ngược lại trả về câu báo lỗi
    public String getChangePasswordValidationMessage() {
        String oldPassword = getOldPassword();
        String newPassword = getNewPassword();

        if (oldPassword.isEmpty() || newPassword.isEmpty() || getConfirmPassword().isEmpty()) {
            return "Vui lòng nhập đầy đủ 3 ô mật khẩu.";
        }
        if (!newPassword.equals(getConfirmPassword())) {
            return "Mật khẩu xác nhận không khớp.";
        }
        if (newPassword.equals(oldPassword)) {
            return "Mật khẩu mới phải khác mật khẩu cũ.";
        }
        // Cùng luật với màn hình Đăng ký
        if (newPassword.length() < 8) {
            return "Mật khẩu mới phải có ít nhất 8 ký tự.";
        }
        if (!newPassword.matches(".*[A-Z].*")) {
            return "Mật khẩu mới cần ít nhất 1 chữ hoa.";
        }
        if (!newPassword.matches(".*[a-z].*")) {
            return "Mật khẩu mới cần ít nhất 1 chữ thường.";
        }
        if (!newPassword.matches(".*[0-9].*")) {
            return "Mật khẩu mới cần ít nhất 1 chữ số.";
        }
        if (!newPassword.matches(".*[^a-zA-Z0-9].*")) {
            return "Mật khẩu mới cần ít nhất 1 ký tự đặc biệt.";
        }
        return "";
    }

    public void clearPasswordFields() {
        oldPasswordField.setText("");
        newPasswordField.setText("");
        confirmPasswordField.setText("");
    }

    // ================= THÔNG BÁO =================

    public void setMessage(String message, boolean isError) {
        messageLabel.setForeground(isError ? Color.RED : new Color(0, 130, 0));
        messageLabel.setText(message);
    }

    public void clearMessage() {
        messageLabel.setText(" ");
    }

    // ================= GET DATA =================

    public String getOldPassword() {
        return new String(oldPasswordField.getPassword());
    }

    public String getNewPassword() {
        return new String(newPasswordField.getPassword());
    }

    public String getConfirmPassword() {
        return new String(confirmPasswordField.getPassword());
    }

    // ================= GET BUTTONS =================

    public JButton getDeleteScoreButton() {
        return deleteScoreButton;
    }

    public JButton getChangePasswordButton() {
        return changePasswordButton;
    }

    public JButton getDeleteAccountButton() {
        return deleteAccountButton;
    }

    public JButton getBackButton() {
        return backButton;
    }
}
