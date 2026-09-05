package flappybird.game;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

public class Game {
    private Bird bird;
    private List<PipePair> pipes; //Mỗi PipePair tự quản lý 2 ống trên/dưới, còn Game quản lý nhiều PipePair.
    private Background background1;
    private Background background2;
    private Ground ground1;
    private Ground ground2;

    private SoundManager soundManager;

    private GameState state;
    private long startTime;

    private void initializeGame(){ //Khởi tạo game để dễ sử dụng hơn tránh lặp code
        bird = new Bird(200, 200);
        pipes = new ArrayList<>();

        pipes.add(new PipePair(600));
        pipes.add(new PipePair(1000));
        pipes.add(new PipePair(1400));
        pipes.add(new PipePair(1800));

        background1 = new Background(0,0 );
        background2 = new Background(960, 0);

        ground1 = new Ground(0, 445);
        ground2 = new Ground(960, 445);
    }

    public Game(){
        initializeGame();

        soundManager = new SoundManager();//âm thanh không phải trạng thái của 1 ván game không cần tạo lại

        state = GameState.READY;
    }

    public void drawPipes(Graphics g){
        for (PipePair pipe : pipes) {
            pipe.draw(g);
        }
    }

    public void update(){
        if(state == GameState.STARTING){  
            background1.update();
            background2.update();
            ground1.update();
            ground2.update();
            if(System.currentTimeMillis() - startTime >= 2000){
                state = GameState.PLAYING;
            }
            return;
        }
        
        if(state == GameState.PLAYING){
            bird.update();   
            for(PipePair pipe : pipes){
                pipe.update();
            }

            if (pipes.get(0).isOffScreen()) {
                pipes.remove(0);

                PipePair lastPipe = pipes.get(pipes.size() - 1); 

                pipes.add(new PipePair(lastPipe.getX() + 400));
            }

            background1.update();
            background2.update();

            ground1.update();
            ground2.update();

            if(background1.getX() <= - 960){
                background1.setX(background2.getX() + 960);
            }
            //Nếu x của background1 bé hơn hoặc bằng -960 thì thực hiện code bên trong.
            if(background2.getX() <= -960){
                background2.setX(background1.getX() + 960);
            }

            if(ground1.getX() <= -960){
                ground1.setX(ground2.getX() + 960);
            }

            if(ground2.getX() <= -960){
                ground2.setX(ground1.getX() + 960);
            }

            for(PipePair pipe : pipes){
                if(pipe.isColliding(bird.getBounds())){ //Kiểm tra có đụng không
                    die(); //Tạo tiếng chết
                    state = GameState.GAME_OVER; //True thì thực hiện lệnh này
                    return;
                }
            }
            if(bird.getY() + bird.getHeight() >= 445){ //Nếu chim đi quá 445px y thì true
                die(); //Tọa tiếng chết
                state = GameState.GAME_OVER; //Đúng lệnh thực thi
                return;
            }
            if(bird.getY() <= -200){
                die();
                state = GameState.GAME_OVER;
                return;
            }
        }
    }

    public void resetGame(){
        initializeGame();

        state = GameState.READY;
    }

    public Bird getBird(){
        return bird;
    }

    public Background getBackground1() {
        return background1;
    }

    public Background getBackground2() {
        return background2;
    }

    public Ground getGround1(){
        return ground1;
    }

    public Ground getGround2(){
        return ground2;
    }

    public void jump(){
        if(state == GameState.READY){
            state = GameState.STARTING;
            startTime = System.currentTimeMillis();
            return;
        }
        if(state == GameState.GAME_OVER){
            resetGame(); //reset nếu game thua sau khi nhấn
            return;
        }

        bird.jump();
        soundManager.playJump();
    }

    public void die(){
        soundManager.playDie();
    }

    public GameState getState(){
        return state;
    }

    public void setState(GameState state){
        this.state = state;
    }
}