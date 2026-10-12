package flappybird.game;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import flappybird.service.SkinService;

public class SkinManager {
    private List<Skin> birdSkins;
    private List<Skin> pipeSkins;
    private List<Skin> backgroundSkins;
    private List<Skin> groundSkins;

    public SkinManager(){
        birdSkins = new ArrayList<>();
        pipeSkins = new ArrayList<>();
        backgroundSkins = new ArrayList<>();
        groundSkins = new ArrayList<>();

        loadSkins();
    }

    private void loadSkins(){
        birdSkins.clear();
        pipeSkins.clear();
        backgroundSkins.clear();
        groundSkins.clear();

        try {
            SkinService skinService = new SkinService();

            for(flappybird.model.Skin dbSkin : skinService.getAllSkins()){
                if(!dbSkin.isActive()){
                    continue;
                }

                Skin skin = new Skin(dbSkin.getId(), dbSkin.getSkinName(), dbSkin.getSkinType(), dbSkin.getImagePath(), dbSkin.getUnlockScore(), dbSkin.isActive());

                switch(skin.getSkinType().toUpperCase()){
                    case "BIRD":
                        birdSkins.add(skin);
                        break;
                     case "PIPE":
                        pipeSkins.add(skin);
                        break;

                    case "GROUND":
                        groundSkins.add(skin);
                        break;

                    case "BACKGROUND":
                        backgroundSkins.add(skin);
                        break;

                    default:
                        System.err.println(
                            "Loai skin khong hop le: "
                            + skin.getSkinType()
                        );
                }
            }
        } catch (SQLException e) {
            System.err.println("Khong the tai skin tu database.");
            e.printStackTrace();
        }
    }

    public List<Skin> getBirdSkins() {
        return birdSkins;
    }

    public List<Skin> getPipeSkins() {
        return pipeSkins;
    }

    public List<Skin> getBackgroundSkins() {
        return backgroundSkins;
    }

    public List<Skin> getGroundSkins() {
        return groundSkins;
    }
}
