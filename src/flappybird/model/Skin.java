package flappybird.model;

public class Skin {
    private int id;
    private String skinName;
    private String skinType;
    private String imagePath;
    private int unlockScore;
    private boolean active;

    public Skin(int id, String skinName, String skinType, String imagepath, int unlockScore, boolean  active){
        this.id = id;
        this.skinName = skinName;
        this.skinType = skinType;
        this.imagePath = imagepath;
        this.unlockScore = unlockScore;
        this.active = active;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSkinName() {
        return skinName;
    }

    public void setSkinName(String skinName) {
        this.skinName = skinName;
    }

    public String getSkinType() {
        return skinType;
    }

    public void setSkinType(String skinType) {
        this.skinType = skinType;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public void setUnlockScore(int unlockScore) {
        this.unlockScore = unlockScore;
    }

    public int getUnlockScore() {
        return unlockScore;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
