package flappybird.game;


import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JPanel;
import javax.swing.Timer;


public class GamePanel extends JPanel implements KeyListener, MouseListener{

    private Bird bird;
    private Timer timer;

    public GamePanel() {
        bird = new Bird(200, 200);

        setFocusable(true);
        addKeyListener(this);
        addMouseListener(this);

        timer = new Timer(16, e ->{ //Timer(khoảng thời gian,việc cần thực hiện);  -> là cú pháp của lambda expression nhận e thực hiện lệnh bên phải
            bird.update();
            repaint();
        });
        //1 giây / 0.016 giây ≈ 62.5 lần
        timer.start();
        
        requestFocusInWindow();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        bird.draw(g);
    }

    @Override
    public void keyPressed(KeyEvent e){
        if(e.getKeyCode() == KeyEvent.VK_SPACE 
            || e.getKeyCode() == KeyEvent.VK_UP){
            bird.jump();
        } 
        else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.out.println("Thoat game");
        } 
        else if (e.getKeyCode() == KeyEvent.VK_P) {
            System.out.println("Tam dung game");
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
        if (e.getButton() == MouseEvent.BUTTON1) {
            bird.jump();
        }
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