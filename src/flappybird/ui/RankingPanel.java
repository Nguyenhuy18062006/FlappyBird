package flappybird.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.SQLException;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import flappybird.model.User;
import flappybird.service.UserService;

public class RankingPanel {

    private int menuWidth = 600;
    private int menuHeight = 450;

    private int buttonWidth = 180;
    private int buttonHeight = 40;

    MenuPanel menuPanel = new MenuPanel();
    private final UserService userService = new UserService();

    private DefaultTableModel tableModel;

    public String getClickedRanking(int mouseX, int mouseY, int panelWidth, int panelHeight) {

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2;

        int buttonX = menuX + (menuWidth - buttonWidth) / 2;

        int buttonY = menuY + 385;

        if (mouseX >= buttonX
                && mouseX <= buttonX + buttonWidth
                && mouseY >= buttonY
                && mouseY <= buttonY + buttonHeight) {

            return "MAIN_MENU";
        }

        return null;
    }

    public void drawRanking(Graphics g, int panelWidth, int panelHeight) {

        Graphics2D g2 = (Graphics2D) g;

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2;

        // Khung
        g2.setColor(new Color(255, 255, 255, 180));
        g2.fillRoundRect(menuX, menuY, menuWidth, menuHeight, 20, 20);

        // TITLE
        g2.setColor(Color.BLACK);
        g2.setFont(new Font( "Arial", Font.BOLD, 32));

        String title = "RANKING";

        int titleX = menuX + (menuWidth - g2.getFontMetrics().stringWidth(title)) / 2;

        g2.drawString( title, titleX, menuY + 45);
    }

    public void drawBackButton(Graphics g, int panelWidth, int panelHeight) {

        Graphics2D g2 = (Graphics2D) g;

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2;

        int buttonX =
            menuX + (menuWidth - buttonWidth) / 2;

        int buttonY = menuY + 385;

        menuPanel.drawButton(
            g2,
            "BACK TO MENU",
            buttonX,
            buttonY,
            buttonWidth,
            buttonHeight
        );
    }

    public JScrollPane createRankingScrollPane() {

        String[] columns = {"RANK", "PLAYER", "SCORE"};

        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setRowHeight(28);
        table.setFillsViewportHeight(true);

        table.getTableHeader().setFont(
                new Font("Arial", Font.BOLD, 15));

        table.getColumnModel().getColumn(0).setPreferredWidth(80);
        table.getColumnModel().getColumn(1).setPreferredWidth(250);
        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        JScrollPane scrollPane = new JScrollPane(table);

        // Ban đầu hiển thị trạng thái chờ tải dữ liệu.
        tableModel.addRow(
                new Object[]{"-", "Đang tải dữ liệu...", "-"});

        return scrollPane;
    }

    public void refreshRanking() {

        if (tableModel == null) {
            return;
        }

        tableModel.setRowCount(0);

        try {
            List<User> users = userService.getTopUsers();

            if (users.isEmpty()) {
                tableModel.addRow(
                        new Object[]{"-", "Chưa có người chơi", "-"});
                return;
            }

            int rank = 1;

            for (User user : users) {
                tableModel.addRow(new Object[]{
                    rank++,
                    user.getUserName(),
                    user.getHighestScore()
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();

            tableModel.setRowCount(0);
            tableModel.addRow(
                    new Object[]{"!", "Không tải được dữ liệu", "-"});
        }
    }

    public int getMenuWidth() {
        return menuWidth;
    }

    public int getMenuHeight() {
        return menuHeight;
    }
}