package flappybird.game;

public enum GameState{ //GameState không phải một đối tượng game
    MENU, //→ chính là trạng thái lúc vừa mở game.
    SKINS, //Skins
    RANKING, //Ranking
    ACCOUNT, //Quản lý tài khoản: lịch sử điểm, đổi mật khẩu, xóa tài khoản
    LOGIN, //Login
    REGISTER,//Register
    PAUSE, // Dừng game nhưng vẫn được chơi tiếp
    EXIT, // Exit
    
    READY, //→ vừa vào game, chưa bắt đầu
    STARTING, //→ đang bay khoảng 2 giây đầu
    PLAYING, //→ gravity hoạt động, chơi bình thường
    GAME_OVER //→ chết
}
