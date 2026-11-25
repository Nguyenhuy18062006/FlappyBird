package flappybird.game;

import java.awt.Graphics;
import java.awt.geom.Ellipse2D;

public class PipePair {
    private Pipe topPipe;
    private Pipe bottomPipe;

    private boolean scored;

    public PipePair(int x ,Skin skin){
        int gap = 150;
        
        int minGapY = 40; //Ống trên ngắn nhất
        int maxGapY = 280; //Ống trên dài nhất

        int gapY = (int)(Math.random() * (maxGapY - minGapY + 1)) + minGapY;
        int topY = gapY - 320;
        int bottomY = gapY + gap; //Tính ống dưới

        topPipe = new Pipe(x, topY, true, skin);

        bottomPipe = new Pipe(x, bottomY, false, skin);

        scored = false;
    }

    public void update(){
        topPipe.update();
        bottomPipe.update();
    }

    public void draw(Graphics g){
        topPipe.draw(g);
        bottomPipe.draw(g);
    }

    public int getX(){
        return topPipe.getX();
    }

    public boolean isOffScreen(){
        return getX() + 70 < 0; //Vì pipe rộng 70.
    }

    public boolean isColliding(Ellipse2D birdBounds){
        return topPipe.getBounds().intersects(birdBounds.getBounds()) || //Đụng top chết
        bottomPipe.getBounds().intersects(birdBounds.getBounds()); //Đụng bottom chết
    }

    public boolean isScored(){
        return scored;
    }

    public void setScored(boolean scored){
        this.scored = scored;
    }
}
