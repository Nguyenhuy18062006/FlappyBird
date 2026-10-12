package flappybird.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import flappybird.model.Skin;

public class SkinDAO {
    public void createSkin(Skin skin) throws SQLException {
        String sql = """
                INSERT INTO SKINS (skin_name, skin_type, image_path, unlock_score, is_active)
                VALUE(?, ?, ?, ?, ?);
                """;
        
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, skin.getSkinName());
            statement.setString(2, skin.getSkinType());
            statement.setString(3, skin.getImagePath());
            statement.setInt(4, skin.getUnlockScore());
            statement.setBoolean(5, skin.isActive());

            statement.executeUpdate();
        }
    }

    public Skin getSkinById(int id) throws SQLException{
        String sql = """
                SELECT * FROM SKINS WHERE id = ?;
                """;

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return new Skin(resultSet.getInt("id"), resultSet.getString("skin_name"), resultSet.getString("skin_type"), resultSet.getString("image_path"), resultSet.getInt("unlock_score"), resultSet.getBoolean("is_active"));
                }
            }
        }
        return null;
    }

    public List<Skin> getAllSkins() throws SQLException {
        String sql = """
                SELECT * FROM SKINS;
                """;

        List<Skin> skins = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    Skin skin = new Skin(resultSet.getInt("id"), resultSet.getString("skin_name"), resultSet.getString("skin_type"), resultSet.getString("image_path"), resultSet.getInt("unlock_score"), resultSet.getBoolean("is_active"));
                    skins.add(skin);
                }
            }
        }
        return skins;
    }

    public void updateSkin(Skin skin) throws SQLException{
        String sql = """
                UPDATE SKINS SET skin_name = ?, skin_type = ?, image_path = ?, unlock_score = ?, is_active = ? WHERE id = ?;
                """;
        
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, skin.getSkinName());
            statement.setString(2, skin.getSkinType());
            statement.setString(3, skin.getImagePath());
            statement.setInt(4, skin.getUnlockScore());
            statement.setBoolean(5, skin.isActive());
            statement.setInt(6, skin.getId());

            statement.executeUpdate();
        }
    }

    public void deleteSkin(int id) throws SQLException{
        String sql = """
                DELETE FROM SKINS WHERE id = ?;
                """;

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, id);

            statement.executeUpdate();
        }
    }
}