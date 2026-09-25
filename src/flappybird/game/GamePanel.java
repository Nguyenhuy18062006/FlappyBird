package flappybird.game;

import flappybird.ui.GameOverPanel;
import flappybird.ui.MenuPanel;
import flappybird.ui.PausePanel;
import flappybird.ui.RankingPanel;
import flappybird.ui.SkinPanel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, MouseListener{
    private Game game;
    private Timer timer;

    private JScrollPane rankingJScrollPane;
    
    private MenuPanel menuPanel;
    private GameOverPanel gameOverPanel;
    private PausePanel pausePanel;
    private RankingPanel rankingPanel;
    private SkinPanel skinPanel;

    public GamePanel() {

        game = new Game();

        skinPanel = new SkinPanel(game.getSkinManager());
        menuPanel = new MenuPanel();
        gameOverPanel = new GameOverPanel();
        pausePanel = new PausePanel();
        rankingPanel = new RankingPanel();

        //Dùng null layout để tự đặt vị trí JScrollPane.
        // Vì GamePanel đang tự vẽ giao diện bằng Graphics.
        setLayout(null);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        addMouseWheelListener(e -> {
            if (game.getState() == GameState.SKINS) {
                skinPanel.scroll(e.getWheelRotation() * 30);
                repaint();
            }
        });

        //Không tạo nó trong paintComponent().
        rankingJScrollPane = rankingPanel.createRankingScrollPane();
        //Add vào GamePanel nhưng ẩn lúc đầu.
        add(rankingJScrollPane);
        rankingJScrollPane.setVisible(false);

        timer = new Timer(16, e ->{ //Timer(khoảng thời gian,việc cần thực hiện);  -> là cú pháp của lambda expression nhận e thực hiện lệnh bên phải
            game.update();
            repaint();
        });
        //1 giây / 0.016 giây ≈ 62.5 lần
        timer.start();
        
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Background luôn được vẽ
        game.getBackground1().draw(g);
        game.getBackground2().draw(g);

        // Kiểm tra trạng thái
        if(game.getState() == GameState.MENU){//=> tức là Game mới là thằng quản lý trạng thái, còn GamePanel chỉ nhìn trạng thái đó để quyết định vẽ cái gì.
            menuPanel.drawMenu(g, getWidth(), getHeight());

            game.getGround1().draw(g);
            game.getGround2().draw(g);
        }
        else if(game.getState() == GameState.GAME_OVER){
            game.getGround1().draw(g);
            game.getGround2().draw(g);
            gameOverPanel.drawGameOver(g, getWidth(), getHeight(), game.getScore());
        }
        else if(game.getState() == GameState.PAUSE){
            game.getGround1().draw(g);
            game.getGround2().draw(g);
            pausePanel.drawPause(g, getWidth(), getHeight(), game.getScore());
        }
        else if (game.getState() == GameState.RANKING){
            game.getGround1().draw(g);
            game.getGround2().draw(g);
            rankingPanel.drawRanking(g, getWidth(), getHeight());
            rankingPanel.drawBackButton(g,getWidth(),getHeight());

            updateRankingScrollPaneBounds();
        }
        else if (game.getState() == GameState.SKINS) {
            skinPanel.drawSkinPanel((Graphics2D) g, getWidth(), getHeight());
        }
        else{
            drawGame(g);
            game.getGround1().draw(g);
            game.getGround2().draw(g);
        }
    }

    private void drawGame(Graphics g){
        game.drawPipes(g);

        game.getBird().draw(g);

        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Score: " + game.getScore(), 20, 30);
    }

    // Đặt vị trí và kích thước JScrollPane.
    private void updateRankingScrollPaneBounds() {

        if (rankingJScrollPane == null) {
            return;
        }
        int menuWidth = rankingPanel.getMenuWidth();
        int menuHeight = rankingPanel.getMenuHeight();

        int menuX = (getWidth() - menuWidth) / 2;
        int menuY = (getHeight() - menuHeight) / 2;

        
        //JScrollPane nằm ở giữa khung Ranking.
        // menuY + 70:
        //    bắt đầu bên dưới title.

        // 500 x 280:
        //  vùng hiển thị danh sách.
        rankingJScrollPane.setBounds(menuX + 50, menuY + 70, 500, 280);
    }

    /*
     * Hiện JScrollPane khi vào Ranking.
     */
    private void showRankingScrollPane() {

        rankingJScrollPane.setVisible(true);

        updateRankingScrollPaneBounds();

        revalidate();
        repaint();
    }

    /*
     * Ẩn JScrollPane khi rời Ranking.
     */
    private void hideRankingScrollPane() {

        rankingJScrollPane.setVisible(false);

        revalidate();
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE 
            || e.getKeyCode() == KeyEvent.VK_UP){
            game.jump();
        } 
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            if(game.getState() == GameState.PLAYING){
                game.setState(GameState.PAUSE);
            }
            System.out.println("Thoat game");
        } 
    }
    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1) {
            return;
        }
        //MENU
        if(game.getState() == GameState.MENU){
            String button = menuPanel.getClickedButton( e.getX(), e.getY(), getWidth(), getHeight());

            if (button == null){
                return;
            }

            game.playClickSound();

            // ==       → hai biến có trỏ cùng object không?
            //.equals  → hai object có cùng nội dung không?
            if (button.equals("PLAY")){ //equals() so sánh nội dung.// còn == so sánh 2 chuỗi luôn luôn là false vì ở 2 ô nhớ khác nhau là 2 object khác nhau
                game.resetGame();
                System.out.println(game.getState()); // in console để sau này mò lỗi cho dễ
            }
            else if (button.equals("SKINS")) {
                game.setState(GameState.SKINS);
                System.out.println(game.getState());
            }
            else if (button.equals("RANKING")) {
                game.setState(GameState.RANKING);
                showRankingScrollPane();
                System.out.println(game.getState());
            }
            else if (button.equals("LOGIN")) {
                game.setState(GameState.LOGIN);
                System.out.println(game.getState());
            }
            else if (button.equals("REGISTER")) {
                game.setState(GameState.REGISTER);
                System.out.println(game.getState());
            }
            else if (button.equals("EXIT")) {
                System.exit(0);
            }
            return;
        }
        if (game.getState() == GameState.GAME_OVER){
            String button = gameOverPanel.getClickedButtonGameOver(e.getX(), e.getY(), getWidth(), getHeight());
            if(button == null){
                return;
            }
            game.playClickSound();

            if(button.equals("PLAY_AGAIN")){
                game.resetGame();
            }
            else if(button.equals("MAIN_MENU")){
                game.setState(GameState.MENU);
            }

            return;
        }

        if(game.getState() == GameState.PAUSE){
            String button = pausePanel.getClickedPause(e.getX(), e.getY(), getWidth(), getHeight());
            if(button == null){
                return;
            }
            game.playClickSound();

            if (button.equals("PLAY_AGAIN")){
                game.resetGame();
            }
            else if(button.equals("MAIN_MENU")){
                game.setState(GameState.MENU);
            }
            else if (button.equals("CONTINUE")){
                game.setState(GameState.READY);
            }

            return;
        }

        if(game.getState() == GameState.RANKING){
            String button = rankingPanel.getClickedRanking(e.getX(), e.getY(), getWidth(), getHeight());
            if(button == null){
                return;
            }
            game.playClickSound();

            if (button.equals("MAIN_MENU")){
                hideRankingScrollPane();
                game.setState(GameState.MENU);
            }

            return;
        }
        if (game.getState() == GameState.SKINS) {
        // CATEGORY
        String category = skinPanel.getClickedCategory(e.getX(), e.getY(), getWidth(), getHeight());

        if (category != null) {
            skinPanel.setSelectedCategory(category);
            game.playClickSound();
            repaint();
            return;
        }
        // SKIN
        Skin clickedSkin = skinPanel.getClickedSkin(e.getX(), e.getY(), getWidth(), getHeight());

        if (clickedSkin != null) {
            game.selectSkin(clickedSkin);
            skinPanel.setSelectedSkin(clickedSkin);
            game.playClickSound();
            repaint();
            return;
        }
        // BACK
        if (skinPanel.isBackClicked(e.getX(), e.getY(), getWidth(), getHeight())) {
            game.setState(GameState.MENU);
            game.playClickSound();
            repaint();
            return;
        }
    }
        game.jump();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
/*Timer.start()
     ↓
chờ 16ms
     ↓
gọi actionPerformed(...)
     ↓
e -> {
    bird.update();
    repaint();
}
     ↓
chờ 16ms
     ↓
gọi actionPerformed(...)
     ↓
e -> {
    bird.update();
    repaint();
}
     ↓
chờ 16ms
     ↓ */