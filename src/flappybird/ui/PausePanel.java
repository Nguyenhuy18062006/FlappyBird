package flappybird.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class PausePanel {
    int menuWidth = 320;
    int menuHeight = 380;

    private int buttonWidth = 150;
    private int buttonHeight = 35;
    private int gap = 10;

    MenuPanel menuPanel = new MenuPanel();

    public String getClickedPause(int mouseX, int mouseY, int panelWidth, int panelHeight){

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2 - 70;

        int buttonX = menuX + (menuWidth - buttonWidth) / 2;
        int startY = menuY + 155;

        String[] buttons = {
            "PLAY_AGAIN",
            "MAIN_MENU",
            "CONTINUE"
        };

        for(int i = 0; i < buttons.length; i++){

            int buttonY = startY + i * (buttonHeight + gap);

            if(mouseX >= buttonX
                && mouseX <= buttonX + buttonWidth
                && mouseY >= buttonY
                && mouseY <= buttonY + buttonHeight){

                return buttons[i];
            }
        }

        return null;
    }

    public void drawPause(Graphics g, int panelWidth, int panelHeight, int score){
        Graphics2D g2 = (Graphics2D) g;

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2 - 35;

        g2.setColor(new Color(255, 255, 255, 180));
        g2.fillRoundRect(
            menuX,
            menuY,
            menuWidth,
            menuHeight,
            20,
            20
        );

        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 32));

        // GAME OVER
        g2.setColor(Color.RED);
        g2.setFont(new Font("Arial", Font.BOLD, 32));

        String title = "GAME OVER";

        int titleX = menuX + (menuWidth - g2.getFontMetrics().stringWidth(title)) / 2;

        g2.drawString(title, titleX, menuY + 55);

        // SCORE
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 20));

        String scoreText = "SCORE: " + score;

        int scoreX = menuX + (menuWidth - g2.getFontMetrics().stringWidth(scoreText)) / 2;

        g2.drawString(scoreText, scoreX, menuY + 95);

         // BUTTON POSITION
        int buttonX = menuX + (menuWidth - buttonWidth) / 2;

        int startY = menuY + 155 - 35;

        menuPanel.drawButton(g2, "PLAY AGAIN", buttonX, startY, buttonWidth, buttonHeight);
        menuPanel.drawButton(g2, "MENU", buttonX, startY + buttonHeight + gap, buttonWidth, buttonHeight);
        menuPanel.drawButton(g2, "CONTINUE", buttonX, startY + 2 * (buttonHeight + gap), buttonWidth, buttonHeight);
        // RANKING
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 16));

        String rankingTitle = "TOP SCORES";

        int rankingTitleX =
                menuX + (menuWidth - g2.getFontMetrics().stringWidth(rankingTitle)) / 2;

        g2.drawString(rankingTitle, rankingTitleX, menuY + 256 + 10);

        String[] players = {
            "Huy",
            "Nam",
            "Minh"
        };

        int[] scores = {
            25,
            21,
            18
        };

        g2.setFont(new Font("Arial", Font.PLAIN, 14));
        for(int i = 0; i < players.length; i++){
            int y = menuY + 285 + 10 + i * 20; // 285 là khoảng cách tự căn từ đỉnh menu đến chữ, còn i * 20 là i tăng để cho biết khoảng cách giữa các dòng
            g2.drawString((i + 1) + ". " + players[i], menuX + 70, y);
            
            g2.drawString(String.valueOf(scores[i]), menuX + 220, y);
        }
    }
}