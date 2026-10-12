package flappybird.game;

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
}
