package flappybird.ui;

import java.awt.*;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class RankingPanel {

    private int menuWidth = 600;
    private int menuHeight = 450;

    private int buttonWidth = 180;
    private int buttonHeight = 40;

    MenuPanel menuPanel = new MenuPanel();

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
        String[] columns = {
            "RANK",
            "PLAYER",
            "SCORE"
        };

        DefaultTableModel model = new DefaultTableModel(columns, 0);

        String[] players = {
            "Huy",
            "Nam",
            "Minh",
            "An",
            "Tùng",
            "Long",
            "Đức",
            "Hoàng",
            "Duy",
            "Khoa",
            "Tuấn",
            "Phúc",
            "Linh",
            "Quân",
            "Thắng",
            "Dũng",
            "Sơn",
            "Khang",
            "Bình",
            "Anh"
        };

        int[] scores = {
            100,
            95,
            90,
            88,
            85,
            80,
            78,
            75,
            72,
            70,
            68,
            65,
            63,
            60,
            58,
            55,
            52,
            50,
            48,
            45
        };

        for (int i = 0; i < players.length; i++) {
            model.addRow(new Object[]{
                i + 1,
                players[i],
                scores[i]
            });
        }

        JTable table = new JTable(model);

        table.setFont(new Font("Arial", Font.PLAIN, 15));
        table.setRowHeight(25);

        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 15));

        table.getColumnModel().getColumn(0).setPreferredWidth(80);

        table.getColumnModel().getColumn(1).setPreferredWidth(250);

        table.getColumnModel().getColumn(2).setPreferredWidth(100);

        // Không cho sửa dữ liệu trong bảng
        table.setEnabled(false);

        JScrollPane scrollPane = new JScrollPane(table);

        return scrollPane;
    }

    public int getMenuWidth() {
        return menuWidth;
    }

    public int getMenuHeight() {
        return menuHeight;
    }
}