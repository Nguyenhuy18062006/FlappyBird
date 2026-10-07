package flappybird.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import flappybird.model.Score;

public class ScoreDAO {
    public void createScore(Score score) throws SQLException{
        String sql = """
                INSERT INTO SCORES (user_id, score)
                VALUE (?, ?)
                """;

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, score.getUserId());
            statement.setInt( 2, score.getScore());

            statement.executeUpdate();
        }
    }

    public Score getScoreById(int id) throws SQLException {
        String sql = """
                SELECT * FROM SCORES WHERE id = ?;
                """;

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt( 1, id);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return new Score(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("score"), resultSet.getTimestamp("create_at").toLocalDateTime());
                }
            }        
        }
        return null;
    }

    public List<Score> getAllScores() throws SQLException{
        String sql = """
                SELECT * FROM SCORES;
                """;

        List<Score> scores = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    Score score = new Score(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("score"), resultSet.getTimestamp("create_at").toLocalDateTime());

                    scores.add(score);
                }
            }
        }
        return scores;
    }

    public Score getScoresByUserId(int userId) throws SQLException{
        String sql = """
                SELECT * FROM SCORES WHERE user_id = ?;
                """;

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return new Score(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("score"), resultSet.getTimestamp("create_at").toLocalDateTime());
                }
            }
        }
        return null;
    }

    public void deleteScore(int scoreId) throws SQLException{
        String sql = """
                DELETE FROM SCORES WHERE id = ?
                """;
        
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, scoreId);

            statement.executeUpdate();
        }
    }
}
