package flappybird.game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Bird {
    private int x;
    private int y;
    private int width;
    private int height;

    private double velocityY;
    private double gravity;
    
    private Image image;
    
    public Bird(int x, int y){
        this.x = x;
        this.y = y;
        
        this.width = 50;
        this.height = 37;

        this.velocityY = 0;
        this.gravity = 0.5;

        image = new ImageIcon("resources/images/birds/pixel_skin1_yellow.png").getImage();
    }
    
    public void draw(Graphics g){
        g.drawImage(image, x, y, width, height, null);
    }

//        bird       → ảnh PNG
//        100        → tọa độ X
//        200        → tọa độ Y
//        60         → chiều rộng
//        60         → chiều cao
//        this       → component đang vẽ
    
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
    //velocityY tăng
    //y tăng
    //Chim rơi xuống

    public void jump(){
        velocityY = -8;
    }

    public Rectangle getBounds(){
        return new Rectangle(x, y, width, height);
    }
}
