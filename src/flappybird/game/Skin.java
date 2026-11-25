package flappybird.game;

import java.awt.Image;
import java.io.File;
import javax.swing.ImageIcon;

public class Skin {
    private int id;
    private  String skinName;
    private String skinType;
    private String imagePath;
    private int unlockScore;
    private boolean active;

    public Skin(int id, String skinName, String skinType,String imagePath, int unlockScore, boolean active){
        this.id = id;
        this.skinName = skinName;
        this.skinType = skinType;
        this.imagePath = imagePath;
        this.unlockScore = unlockScore;
        this.active = active;
    }

    public int getId(){
        return id;
    }

    public String getSkinName() {
        return skinName;
    }

    public String getSkinType() {
        return skinType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public int getUnlockScore() {
        return unlockScore;
    }

    public boolean isActive() {
        return active;
    }

    // Load ảnh skin; nếu file không tồn tại thì dùng ảnh mặc định của loại skin đó
    public Image loadImage() {
        String path = new File(imagePath).exists() ? imagePath : getDefaultImagePath(skinType);
        return new ImageIcon(path).getImage();
    }

    private static String getDefaultImagePath(String skinType) {
        switch (skinType) {
            case "BIRD":
                return "resources/images/birds/pixel_skin1_yellow.png";
            case "PIPE":
                return "resources/images/pipes/pipe_spring.png";
            case "GROUND":
                return "resources/images/grounds/ground_spring.png";
            default:
                return "resources/images/backgrounds/bg_spring.png";
        }
    }
}
