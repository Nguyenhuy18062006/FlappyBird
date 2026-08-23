package flappybird.game;

import java.awt.Graphics;
import java.awt.Image;
import javax.swing.ImageIcon;

public class Background {
    private int x;
    private int y;
    private int width;
    private int height;

    private Image image;

    private int speed;

    public Background(int x, int y) {
        this.x = x;
        this.y = y;
        
        this.width = 960;
        this.height = 540;

        this.speed = 1;

        image = new ImageIcon("resources/images/backgrounds/bg_spring.png").getImage();
    }

    public void draw(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }

    public void update(){
        x -= speed;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }
}
