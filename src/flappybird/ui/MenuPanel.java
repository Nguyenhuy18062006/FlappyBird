package flappybird.ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class MenuPanel {

    // Dùng để tạo hiệu ứng đổi màu cho tiêu đề
    private float hue = 0.0f;

    public void drawButton(Graphics2D g2, String text, int x, int y, int width, int height){
        g2.setColor(Color.GREEN);
        g2.fillRoundRect(x, y, width, height, 15, 15);
        // x :  Tọa độ X của góc trên bên trái hình chữ nhật.
        // y : Tọa độ Y của góc trên bên trái hình chữ nhật.
        // width : Chiều rộng của hình chữ nhật.
        // height : Chiều cao của hình chữ nhật.
        // 15 là bo góc
        g2.setColor(Color.BLACK);
        g2.setFont(new Font("Arial", Font.BOLD, 18));
        //cho biết thông tin về font hiện tại.

        FontMetrics fm = g2.getFontMetrics();

         // Căn chữ vào giữa nút
        int textX = x + (width - fm.stringWidth(text)) / 2;

        // getAscent() lấy độ cao phần chữ phía trên baseline
        // Dùng để căn chữ theo chiều dọc
        int textY = y + (height + fm.getAscent()) / 2 - 5;

        g2.drawString(text, textX, textY);
    }

    public String getClickedButton(int mouseX, int mouseY, int panelWidth, int panelHeight){
        int menuWidth = 320;
        int menuHeight = 380;

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2 - 35;

        int buttonWidth = 150;
        int buttonHeight = 35;
        int gap = 10;

        int buttonX = menuX + (menuWidth - buttonWidth) / 2;
        int startY = menuY + 100;

        String[] buttons = {"PLAY", "SKINS", "RANKING", "LOGIN", "REGISTER", "EXIT"};
        //các nút khi click

        for(int i = 0; i < buttons.length; i++){
            int buttonY = startY + i * (buttonHeight + gap);

            if (mouseX >= buttonX && mouseX <= buttonX + buttonWidth
                && mouseY >= buttonY && mouseY <= buttonY + buttonHeight){
                    return buttons[i];
            }
        }
        return null;
    }

    

    public void drawMenu(Graphics g, int panelWidth, int panelHeight) {

        // Graphics2D có thêm nhiều chức năng vẽ so với Graphics
        Graphics2D g2 = (Graphics2D) g;

        int menuWidth = 320;
        int menuHeight = 380;

        // Căn Menu vào chính giữa màn hình
        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2 - 35;

        // Khung Menu
        // 180 là độ trong suốt (Alpha)
        g2.setColor(new Color(255, 255, 255, 180));
        g2.fillRoundRect(
            menuX,
            menuY,
            menuWidth,
            menuHeight,
            20,
            20
        );

        // Tiêu đề
        g2.setFont(new Font("Arial", Font.BOLD, 30));

        // HSBtoRGB chuyển màu từ hệ HSB sang RGB
        int rgb = Color.HSBtoRGB(hue, 1.0f, 1.0f);
        g2.setColor(new Color(rgb));

        String title = "FLAPPY BIRD";

        // FontMetrics dùng để lấy thông tin/kích thước của chữ
        FontMetrics fm = g2.getFontMetrics();

        // Lấy chiều rộng chữ để căn giữa
        int titleX = menuX
                + (menuWidth - fm.stringWidth(title)) / 2;
        //Lấy chiều rộng menu - chiều rộng chữ rồi chia 2 → chữ nằm giữa menu.

        g2.drawString(title, titleX, menuY + 60);

        // Cho màu tiêu đề thay đổi liên tục
        if (hue >= 1.0f) {
            hue = 0.02f;
        }

        // các Nút
        int buttonWidth = 150;
        int buttonHeight = 35;
        int gap = 10;

        int buttonX = menuX + (menuWidth - buttonWidth) / 2;

        int startY = menuY + 100;

        drawButton(g2, "PLAY", buttonX, startY,buttonWidth, buttonHeight);
        drawButton(g2, "SKINS", buttonX, startY + (buttonHeight + gap), buttonWidth, buttonHeight);
        drawButton(g2, "RANKING", buttonX, startY + 2 * (buttonHeight + gap), buttonWidth, buttonHeight);
        drawButton(g2, "LOGIN", buttonX, startY + 3 * (buttonHeight + gap), buttonWidth, buttonHeight);
        drawButton(g2, "REGISTER", buttonX, startY + 4 * (buttonHeight + gap), buttonWidth, buttonHeight);
        drawButton(g2, "EXIT", buttonX, startY + 5 * (buttonHeight + gap), buttonWidth, buttonHeight);
    }
}