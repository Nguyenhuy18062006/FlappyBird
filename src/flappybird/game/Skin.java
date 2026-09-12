package flappybird.game;

public class Skin {
    private int id;
    private  String skinName;
    private String skinType;
    private String imagePath;

    public Skin(int id, String skinName, String skinType,String imagePath){
        this.id = id;
        this.skinName = skinName;
        this.skinType = skinType;
        this.imagePath = imagePath;
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
}
