package flappybird.game;

import java.awt.Graphics;
import java.awt.Rectangle;

public class PipePair {
    private Pipe topPipe;
    private Pipe bottomPipe;

    public PipePair(int x){
        int gap = 120;
        
        int minGapY = 100; //Ống trên ngắn nhất
        int maxGapY = 320; //Ống trên dài nhất

        int gapY = (int)(Math.random() * (maxGapY - minGapY + 1)) + minGapY;
        //+1 để int ép kiểu không bị làm tròn xuống thành 100 -> 319;
        int topY = gapY - 320; //tính ống trên topY = 250 - 320 = -70
        int bottomY = gapY + gap; //Tính ống dưới

        topPipe = new Pipe(x, topY, true);

        bottomPipe = new Pipe(x, bottomY, false);
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

    public boolean isColliding(Rectangle birdBounds){
        return topPipe.getBounds().intersects(birdBounds) || //Đụng top chết
        bottomPipe.getBounds().intersects(birdBounds); //Đụng bottom chết
        //Không đụng false sống
    }
}
// Bước 1:
// Math.random()
//        ↓
// 0.0 → <1.0

// Bước 2:
// × 221
//        ↓
// 0.0 → <221.0
//        ↓
// (int)
//        ↓
// 0 → 220

// Bước 3:
// + 100
//        ↓
// 100 → 320

// Score 0     → gap 150
// Score 5     → gap 140
// Score 10    → gap 130
// Score 15    → gap 120
// Score 20    → gap 110
// Score 30    → gap 100
// Score 40+   → gap 100