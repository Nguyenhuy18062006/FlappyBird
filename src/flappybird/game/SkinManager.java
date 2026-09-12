package flappybird.game;

import java.util.ArrayList;
import java.util.List;

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
        // Brid skins
        birdSkins.add(new Skin(1, "Yellow", "BRID", "resources/images/birds/pixel_skin1_yellow.png"));
        birdSkins.add(new Skin(2,"Blue", "BIRD", "resources/images/birds/pixel_skin2_blue.png"));
        birdSkins.add(new Skin(3,"Red","BIRD","resources/images/birds/pixel_skin3_red.png"));
        birdSkins.add(new Skin(4,"Pink","BIRD","resources/images/birds/pixel_skin4_pink.png"));
        birdSkins.add(new Skin(5,"Green","BIRD","resources/images/birds/pixel_skin5_green.png"));
        // Pipe skins
        pipeSkins.add(new Skin(6,"Spring","PIPE","resources/images/pipes/pipe_spring.png"));
        pipeSkins.add(new Skin(7,"Summer","PIPE","resources/images/pipes/pipe_summer.png"));
        pipeSkins.add(new Skin(8,"Autumn","PIPE","resources/images/pipes/pipe_autumn.png"));
        pipeSkins.add(new Skin(9,"Winer","PIPE","resources/images/pipes/pipe_winter.png"));
        // Ground skins
        groundSkins.add(new Skin(10,"Spring","GROUND","resources/images/grounds/ground_spring.png"));
        groundSkins.add(new Skin(11,"Summer","GROUND","resources/images/grounds/ground_summer.png"));
        groundSkins.add(new Skin(12,"Autumn","GROUND","resources/images/grounds/ground_autumn.png"));
        groundSkins.add(new Skin(13,"Winter","GROUND","resources/images/grounds/ground_winter.png"));
        // Background skins
        backgroundSkins.add(new Skin(14,"Spring","BACKGROUND","resources/images/backgrounds/bg_spring.png"));
        backgroundSkins.add(new Skin(15,"Summer","BACKGROUND","resources/images/backgrounds/bg_summer.png"));
        backgroundSkins.add(new Skin(16,"Autumn","BACKGROUND","resources/images/backgrounds/bg_autumn.png"));
        backgroundSkins.add(new Skin(17,"Winter","BACKGROUND","resources/images/backgrounds/bg_winter.png"));
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
