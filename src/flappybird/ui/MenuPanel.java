package flappybird.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{
    public MenuPanel(){
        setFocusable(true);
    }

    @Override 
    protected void paintComponent(Graphics g){
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setColor(new Color(135, 206, 235));
        g2.fillRect(0, 0, getWidth(), getHeight());//Tô một hình chữ nhật.
        
    }
}
