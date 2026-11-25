package flappybird.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.Ellipse2D;

public class Bird {
    private int x;
    private int y;
    private int width;
    private int height;

    private double velocityY;
    private double gravity;
    
    private Image image;
    
    public Bird(int x, int y, Skin skin){
        this.x = x;
        this.y = y;
        
        this.width = 50;
        this.height = 37;

        this.velocityY = 0;
        this.gravity = 0.5;

        image = skin.loadImage();
    }
    
    public void draw(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }

    public int getX(){
        return x;
    }
    
    public int getY(){
        return y;
    }

    public int getHeight() {
        return height;
    }

    public void update(){
        velocityY += gravity;
        y += velocityY;
    }

    public void jump(){
        velocityY = -8;
    }

    public Ellipse2D getBounds(){
        return new Ellipse2D.Double(x, y, width, height);
    }
}
