package flappybird.ui;

import flappybird.game.Skin;
import flappybird.game.SkinManager;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;

public class SkinPanel {
    private final int menuWidth = 720;
    private final int menuHeight = 450;

    private MenuPanel menuPanel;

    private final String[] categories = {
        "ALL",
        "BIRD",
        "GROUND",
        "PIPE",
        "BACKGROUND"
    };

    private String selectedCategory = "ALL";

    private int scrollOffset = 0;

    private final SkinManager skinManager;

    private Skin selectedSkin;

    public SkinPanel(SkinManager skinManager) {
        this.skinManager = skinManager;
        this.menuPanel = new MenuPanel();
    }

    public void drawSkinPanel(Graphics2D g, int panelWidth, int panelHeight) {

        int menuX = (panelWidth - menuWidth) / 2;
        int menuY = (panelHeight - menuHeight) / 2;
        // PANEL
        g.setColor(new Color(255, 255, 255, 235));
        g.fillRoundRect(
                menuX,
                menuY,
                menuWidth,
                menuHeight,
                25,
                25
        );

        g.setColor(Color.BLACK);
        g.setStroke(new BasicStroke(3));

        g.drawRoundRect(
                menuX,
                menuY,
                menuWidth,
                menuHeight,
                25,
                25
        );

        // =========================
        // TITLE
        // =========================

        g.setFont(new Font("Arial", Font.BOLD, 32));

        String title = "SKINS";

        FontMetrics fm = g.getFontMetrics();

        int titleX = menuX
                + (menuWidth - fm.stringWidth(title)) / 2;

        g.drawString(
                title,
                titleX,
                menuY + 42
        );

        // =========================
        // CATEGORY BUTTONS
        // =========================

        int buttonWidth = 120;
        int buttonHeight = 34;
        int gap = 8;

        int totalWidth =
                categories.length * buttonWidth
                + (categories.length - 1) * gap;

        int startX =
                menuX + (menuWidth - totalWidth) / 2;

        int buttonY = menuY + 58;

        for (int i = 0; i < categories.length; i++) {

            int buttonX =
                    startX + i * (buttonWidth + gap);

            drawCategoryButton(
                    g,
                    categories[i],
                    buttonX,
                    buttonY,
                    buttonWidth,
                    buttonHeight
            );
        }

        // =========================
        // SKIN CARDS
        // =========================

        List<Skin> skins = getDisplayedSkins();

        int cardWidth = 130;
        int cardHeight = 145;

        int cardGap = 12;

        int cardsPerRow = 4;

        int totalCardsWidth =
                cardsPerRow * cardWidth
                + (cardsPerRow - 1) * cardGap;

        int cardStartX =
                menuX + (menuWidth - totalCardsWidth) / 2;

        int cardStartY = menuY + 105;

        // Chỉ cho phép card hiển thị trong vùng cuộn.
        
        int backY = menuY + menuHeight - 45;

        Graphics2D cardGraphics = (Graphics2D) g.create();

        cardGraphics.clipRect(
                menuX + 10,
                cardStartY,
                menuWidth - 20,
                backY - cardStartY - 10
        );

        for (int i = 0; i < skins.size(); i++) {

            Skin skin = skins.get(i);

            int row = i / cardsPerRow;
            int column = i % cardsPerRow;

            int cardX =
                    cardStartX
                    + column * (cardWidth + cardGap);

            int cardY =
                    cardStartY
                    + row * (cardHeight + cardGap)
                    - scrollOffset;

            drawSkinCard(
                    cardGraphics,
                    skin,
                    cardX,
                    cardY,
                    cardWidth,
                    cardHeight
            );
        }

        cardGraphics.dispose();

        // BACK BUTTON
        int backWidth = 150;
        int backHeight = 35;

        int backX =
                menuX + (menuWidth - backWidth) / 2;

        menuPanel.drawButton(
                g,
                "BACK",
                backX,
                backY,
                backWidth,
                backHeight
        );
    }

    // CATEGORY BUTTON
    private void drawCategoryButton(
            Graphics2D g,
            String category,
            int x,
            int y,
            int width,
            int height) {

        if (category.equals(selectedCategory)) {
            g.setColor(new Color(80, 160, 80));
        } else {
            g.setColor(new Color(210, 210, 210));
        }

        g.fillRoundRect(
                x,
                y,
                width,
                height,
                10,
                10
        );

        g.setColor(Color.BLACK);

        g.drawRoundRect(
                x,
                y,
                width,
                height,
                10,
                10
        );

        g.setFont(
                new Font("Arial", Font.BOLD, 13)
        );

        FontMetrics fm = g.getFontMetrics();

        int textX =
                x + (width - fm.stringWidth(category)) / 2;

        int textY =
                y + (height + fm.getAscent()) / 2 - 3;

        g.drawString(
                category,
                textX,
                textY
        );
    }

    // SKIN CARD
    private void drawSkinCard(
            Graphics2D g,
            Skin skin,
            int x,
            int y,
            int width,
            int height) {

        g.setColor(new Color(245, 245, 245));

        g.fillRoundRect(
                x,
                y,
                width,
                height,
                12,
                12
        );

        if (selectedSkin != null && selectedSkin.getId() == skin.getId()) {

            g.setColor(new Color(80, 160, 80));
            g.setStroke(new BasicStroke(3));

        } else {

            g.setColor(Color.BLACK);
            g.setStroke(new BasicStroke(1));
        }

        g.drawRoundRect(
                x,
                y,
                width,
                height,
                12,
                12
        );

        // IMAGE
        ImageIcon icon =
                new ImageIcon(skin.getImagePath());

        Image image = icon.getImage();

        int imageSize = 70;

        int imageX =
                x + (width - imageSize) / 2;

        int imageY = y + 12;

        g.drawImage(
                image,
                imageX,
                imageY,
                imageSize,
                imageSize,
                null
        );

        // NAME
        g.setColor(Color.BLACK);

        g.setFont(
                new Font("Arial", Font.BOLD, 12)
        );

        FontMetrics fm = g.getFontMetrics();

        String name = skin.getSkinName();

        int nameX =
                x + (width - fm.stringWidth(name)) / 2;

        g.drawString(
                name,
                nameX,
                y + 103
        );

        // STATUS
        g.setFont(
                new Font("Arial", Font.PLAIN, 11)
        );

        String status;

        if (selectedSkin != null
                && selectedSkin.getId() == skin.getId()) {
            status = "SELECTED";
            g.setColor(new Color(80, 160, 80));
        } else {
            status = "SKIN";
            g.setColor(Color.BLACK);
        }

        int statusX =
                x + (width
                - g.getFontMetrics().stringWidth(status))
                / 2;

        g.drawString(
                status,
                statusX,
                y + 125
        );
    }

    private List<Skin> getDisplayedSkins() {

        List<Skin> skins = new ArrayList<>();

        switch (selectedCategory) {

            case "BIRD":
                skins.addAll(
                        skinManager.getBirdSkins()
                );
                break;

            case "GROUND":
                skins.addAll(
                        skinManager.getGroundSkins()
                );
                break;

            case "PIPE":
                skins.addAll(
                        skinManager.getPipeSkins()
                );
                break;

            case "BACKGROUND":
                skins.addAll(
                        skinManager.getBackgroundSkins()
                );
                break;

            case "ALL":

                skins.addAll(
                        skinManager.getBirdSkins()
                );

                skins.addAll(
                        skinManager.getGroundSkins()
                );

                skins.addAll(
                        skinManager.getPipeSkins()
                );

                skins.addAll(
                        skinManager.getBackgroundSkins()
                );

                break;
        }

        return skins;
    }

    // =====================================================
    // CLICK CATEGORY
    // =====================================================

    public String getClickedCategory(
            int mouseX,
            int mouseY,
            int panelWidth,
            int panelHeight) {

        int menuX =
                (panelWidth - menuWidth) / 2;

        int menuY =
                (panelHeight - menuHeight) / 2;

        int buttonWidth = 120;
        int buttonHeight = 34;
        int gap = 8;

        int totalWidth =
                categories.length * buttonWidth
                + (categories.length - 1) * gap;

        int startX =
                menuX + (menuWidth - totalWidth) / 2;

        int buttonY = menuY + 58;

        for (int i = 0; i < categories.length; i++) {

            int buttonX =
                    startX + i * (buttonWidth + gap);

            if (
                    mouseX >= buttonX
                    && mouseX <= buttonX + buttonWidth
                    && mouseY >= buttonY
                    && mouseY <= buttonY + buttonHeight
            ) {

                return categories[i];
            }
        }

        return null;
    }

    // =====================================================
    // CLICK SKIN
    // =====================================================

    public Skin getClickedSkin(
            int mouseX,
            int mouseY,
            int panelWidth,
            int panelHeight) {

        int menuX =
                (panelWidth - menuWidth) / 2;

        int menuY =
                (panelHeight - menuHeight) / 2;

        int cardWidth = 130;
        int cardHeight = 145;

        int cardGap = 12;

        int cardsPerRow = 4;

        int totalCardsWidth =
                cardsPerRow * cardWidth
                + (cardsPerRow - 1) * cardGap;

        int cardStartX =
                menuX + (menuWidth - totalCardsWidth) / 2;

        int cardStartY = menuY + 105;

        List<Skin> skins =
                getDisplayedSkins();

        for (int i = 0; i < skins.size(); i++) {

            int row = i / cardsPerRow;
            int column = i % cardsPerRow;

            int cardX =
                    cardStartX
                    + column * (cardWidth + cardGap);

            int cardY =
                    cardStartY
                    + row * (cardHeight + cardGap)
                    - scrollOffset;

            if (
                    mouseX >= cardX
                    && mouseX <= cardX + cardWidth
                    && mouseY >= cardY
                    && mouseY <= cardY + cardHeight
                    && mouseY >= cardStartY
                    && mouseY <= menuY + menuHeight - 55
            ) {

                return skins.get(i);
            }
        }

        return null;
    }

    // =====================================================
    // CLICK BACK
    // =====================================================

    public boolean isBackClicked(
            int mouseX,
            int mouseY,
            int panelWidth,
            int panelHeight) {

        int menuX =
                (panelWidth - menuWidth) / 2;

        int menuY =
                (panelHeight - menuHeight) / 2;

        int backWidth = 150;
        int backHeight = 35;

        int backX =
                menuX + (menuWidth - backWidth) / 2;

        int backY =
                menuY + menuHeight - 45;

        return mouseX >= backX
                && mouseX <= backX + backWidth
                && mouseY >= backY
                && mouseY <= backY + backHeight;
    }

    // =====================================================
    // CATEGORY
    // =====================================================

    public void setSelectedCategory(String category) {
        selectedCategory = category;
        scrollOffset = 0;
    }

    public void scroll(int amount) {

        scrollOffset += amount;

        if (scrollOffset < 0) {
            scrollOffset = 0;
        }

        int maxScroll = getMaxScroll();

        if (scrollOffset > maxScroll) {
            scrollOffset = maxScroll;
        }
    }

    private int getMaxScroll() {

        List<Skin> skins = getDisplayedSkins();

        int cardsPerRow = 4;
        int cardHeight = 145;
        int cardGap = 12;

        int rows =
                (int) Math.ceil(
                        (double) skins.size() / cardsPerRow
                );

        int contentHeight =
                rows * cardHeight
                + Math.max(0, rows - 1) * cardGap;

        int visibleHeight = 300;

        return Math.max(
                0,
                contentHeight - visibleHeight
        );
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    // =====================================================
    // SIZE
    // =====================================================

    public int getMenuWidth() {
        return menuWidth;
    }

    public int getMenuHeight() {
        return menuHeight;
    }

    public void setSelectedSkin(Skin skin) {
        selectedSkin = skin;
    }

    public Skin getSelectedSkin() {
        return selectedSkin;
    }
}