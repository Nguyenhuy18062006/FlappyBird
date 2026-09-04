package flappybird.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Pipe {
    private int x;
    private int y;
    private int width;
    private int height;

    private Image image;

    private int speed;

    private boolean flipped;

    public Pipe(int x, int y, boolean flipped){
        this.x = x;
        this.y = y;

        this.width = 70;
        this.height = 320;

        this.speed = 3;

        this.flipped = flipped;

        image = new ImageIcon("resources/images/pipes/pipe_spring.png").getImage();
    }

    public void draw(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        if(flipped){
            g2.drawImage(image, x, y + height, width, -height, null);
        }
        else{
            g2.drawImage(image, x, y, width, height, null);
        }
    }

    public void update(){
        x -= speed;
    }

    public int getX() {
        return x;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
