package flappybird.ui;

import javax.swing.JFrame;

public class MenuFrame extends JFrame{
    public MenuFrame(){
        setTitle("Fappybird");
        setSize(960, 540);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(new MenuPanel());
        setVisible(true);
    }
}
