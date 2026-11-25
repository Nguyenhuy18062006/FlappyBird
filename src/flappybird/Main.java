package flappybird;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
 
import flappybird.game.GamePanel; 

public class Main {
    public static void main(String[] args) {
        
        JFrame frame = new JFrame();
        //tạo 1 frame (Cửa sổ)
        
        frame.setTitle("Flappy Bird");
        //Đặt tên cho cửa sổ

        frame.setIconImage(new ImageIcon("resources/images/birds/pixel_skin1_yellow.png").getImage());
        //Icon cửa sổ
        
        frame.setSize(860, 540);
        //Kích thước tính bằng pixel
        
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //Tắt hẳn chương trình khi đóng cửa sổ
        
        frame.setLocationRelativeTo(null);
        //Đặt cửa sổ xuất hiện ở giữa màn hình.
        
        GamePanel gamePanel = new GamePanel();
        frame.add(gamePanel);
       
        frame.setVisible(true);
        //Cho cửa sổ xuất hiện (Nếu không có thì cửa sổ vẫn tạo nhưng không hiện ra)
    }
}
