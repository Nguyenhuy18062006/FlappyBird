package flappybird.service;

import java.sql.SQLException;
import java.util.List;

import flappybird.database.SkinDAO;
import flappybird.model.Skin;

public class SkinService {
    private final SkinDAO skinDAO;

    public SkinService(){
        this.skinDAO = new SkinDAO();
    }

    public void createSkin(Skin skin) throws SQLException {
        skinDAO.createSkin(skin);
    }

    public Skin getSkinById(int id) throws SQLException {
        return skinDAO.getSkinById(id);
    }

    public List<Skin> getAllSkins() throws SQLException {
        return skinDAO.getAllSkins();
    }

    public void updateSkin(Skin skin) throws SQLException {
        skinDAO.updateSkin(skin);
    }

    public void deleteSkin(int id) throws SQLException {
        skinDAO.deleteSkin(id);
    }
}
