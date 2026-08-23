package flappybird.game;

public class Game {
    private Bird bird;
    private PipePair pipe;
    private Background background1;
    private Background background2;
    private Ground ground1;
    private Ground ground2;

    public Game(){
        bird = new Bird(200, 200);
        pipe = new PipePair(600);
        background1 = new Background(0,0 );
        background2 = new Background(960, 0);
        ground1 = new Ground(0, 445);
        ground2 = new Ground(960, 445);
    }

    public void update(){
        bird.update();
        pipe.update();
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
    }

    public Bird getBird(){
        return bird;
    }

    public PipePair getPipe(){
        return pipe;
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
}