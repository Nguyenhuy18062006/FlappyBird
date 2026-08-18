package flappybird;

import flappybird.game.GamePanel;
import javax.swing.JFrame;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        //tạo 1 frame (Cửa sổ)
        
        frame.setTitle("Flappy Bird");
        //Đặt tên cho cửa sổ
        
        frame.setSize(800, 600);
        //Kích thước tính bằng pixel 
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //(Dòng này rất quan trọng) Nếu không có khi bấm X để tắt cửa sổ thì chương trình vẫn chạy ngầm
        //Còn có thì chương trình sẽ kết thúc.
        
        frame.setLocationRelativeTo(null);
        //Đặt cửa sổ xuất hiện ở giữa màn hình       
        
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
       
        frame.setVisible(true);
        //Cho cửa sổ xuất hiện (Nếu không có thì cửa sổ vẫn tạo nhưng không hiện ra)
    }
}
