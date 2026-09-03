package flappybird.game;

import java.awt.Graphics;

public class PipePair {
    private Pipe topPipe;
    private Pipe bottomPipe;

    public PipePair(int x){
        int gap = 120;
        int topY = -230;

    topPipe = new Pipe(x, topY, true);

    bottomPipe = new Pipe(
        x,
        topY + 320 + gap,
        false
    );
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
}
// gap = bottomY - (topY + pipeHeight)

// gap = 210 - (-230 + 320)
//     = 210 - 90
//     = 120px

// Score 0     → gap 150
// Score 5     → gap 140
// Score 10    → gap 130
// Score 15    → gap 120
// Score 20    → gap 110
// Score 30    → gap 100
// Score 40+   → gap 100