package flappybird.game;

public enum GameState{ //GameState không phải một đối tượng game
    READY, //→ vừa vào game, chưa bắt đầu
    STARTING, //→ đang bay khoảng 2 giây đầu
    PLAYING, //→ gravity hoạt động, chơi bình thường
    GAME_OVER //→ chết
}
