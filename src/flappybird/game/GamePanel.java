package flappybird.game;

import flappybird.ui.GameOverPanel;
import flappybird.ui.MenuPanel;
import flappybird.ui.PausePanel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GamePanel extends JPanel implements KeyListener, MouseListener{
    private Game game;
    private Timer timer;
    private float hue = 0.0f;
    
    private MenuPanel menuPanel;
    private GameOverPanel gameOverPanel;
    private PausePanel pausePanel;

    public GamePanel() {

        game = new Game();

        menuPanel = new MenuPanel();
        gameOverPanel = new GameOverPanel();
        pausePanel = new PausePanel();

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

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

            if(button.equals("PLAY_AGAIN")){
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