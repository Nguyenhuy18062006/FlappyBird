
package flappybird.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import flappybird.model.User;
import flappybird.service.UserService;

public class GameOverPanel {
    private final int menuWidth = 320;
    private final int menuHeight = 380;

    private final int buttonWidth = 150;
    private final int buttonHeight = 35;
    private final int gap = 10;

    private final MenuPanel menuPanel = new MenuPanel();
    private final UserService userService = new UserService();

    private List<User> topUsers = new ArrayList<>();
    private boolean rankingLoaded = false;
    private boolean rankingError = false;

    // Gọi hàm này khi game chuyển sang màn hình GAME OVER.
    public void refreshTopScores() {
        rankingLoaded = false;
        rankingError = false;

        try {
            List<User> users = userService.getTopUsers();
            topUsers = new ArrayList<>();

            for (int i = 0; i < users.size() && i < 3; i++) {
                topUsers.add(users.get(i));
            }

            rankingLoaded = true;
        } catch (SQLException e) {
            e.printStackTrace();
            topUsers.clear();
            rankingError = true;
        }
    }

    public String getClickedButtonGameOver(
            int mouseX, int mouseY, int panelWidth, int panelHeight) {

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2 - 70;

        int buttonX = menuX + (menuWidth - buttonWidth) / 2;
        int startY = menuY + 155;

        String[] buttons = {"PLAY_AGAIN", "MAIN_MENU"};

        for (int i = 0; i < buttons.length; i++) {
            int buttonY = startY + i * (buttonHeight + gap);

            if (mouseX >= buttonX
                    && mouseX <= buttonX + buttonWidth
                    && mouseY >= buttonY
                    && mouseY <= buttonY + buttonHeight) {
                return buttons[i];
            }
        }

        return null;
    }

    public void drawGameOver(
            Graphics g, int panelWidth, int panelHeight, int score) {

        Graphics2D g2 = (Graphics2D) g;

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2 - 35;

        // Khung Game Over
        g2.setColor(new Color(255, 255, 255, 180));
        g2.fillRoundRect(
                menuX, menuY, menuWidth, menuHeight, 20, 20);

        // Tiêu đề
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 32));

        String title = "GAME OVER";
        int titleX = menuX
                + (menuWidth - g2.getFontMetrics().stringWidth(title)) / 2;

        g2.drawString(title, titleX, menuY + 55);

        // Điểm ván vừa chơi
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));

        String scoreText = "SCORE: " + score;
        int scoreX = menuX
                + (menuWidth - g2.getFontMetrics().stringWidth(scoreText)) / 2;

        g2.drawString(scoreText, scoreX, menuY + 95);

        // Nút chơi lại và về menu
        int buttonX = menuX + (menuWidth - buttonWidth) / 2;
        int startY = menuY + 120;

        menuPanel.drawButton(
                g2, "PLAY AGAIN", buttonX, startY, buttonWidth, buttonHeight);

        menuPanel.drawButton(
                g2, "MENU", buttonX,
                startY + buttonHeight + gap, buttonWidth, buttonHeight);

        // TOP SCORES
        g2.setColor(new Color(30, 30, 30));
        g2.setFont(new Font("Arial", Font.BOLD, 16));

        String rankingTitle = "TOP SCORES";
        int rankingTitleX = menuX
                + (menuWidth - g2.getFontMetrics().stringWidth(rankingTitle)) / 2;

        g2.drawString(rankingTitle, rankingTitleX, menuY + 225);

        g2.setFont(new Font("Arial", Font.PLAIN, 14));

        if (!rankingLoaded && !rankingError) {
            drawCenteredText(g2, "Loading ranking...",
                    menuX, menuWidth, menuY + 250);
            return;
        }

        if (rankingError) {
            drawCenteredText(g2, "Cannot load ranking",
                    menuX, menuWidth, menuY + 250);
            return;
        }

        if (topUsers.isEmpty()) {
            drawCenteredText(g2, "No scores yet",
                    menuX, menuWidth, menuY + 250);
            return;
        }

        int limit = Math.min(topUsers.size(), 3);

        for (int i = 0; i < limit; i++) {
            User user = topUsers.get(i);
            int y = menuY + 252 + i * 23;

            String playerName = user.getUserName();
            String playerScore = String.valueOf(user.getHighestScore());

            // Cắt tên quá dài để không tràn khỏi khung
            if (playerName.length() > 15) {
                playerName = playerName.substring(0, 12) + "...";
            }

            g2.setColor(Color.BLACK);
            g2.drawString((i + 1) + ". " + playerName, menuX + 45, y);
            g2.drawString(playerScore, menuX + 245, y);
        }
    }

    private void drawCenteredText(
            Graphics2D g2, String text, int menuX, int width, int y) {

        int textX = menuX
                + (width - g2.getFontMetrics().stringWidth(text)) / 2;

        g2.setColor(Color.DARK_GRAY);
        g2.drawString(text, textX, y);
    }
}