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

    // READ (nhiều dòng): lấy toàn bộ lịch sử chơi của 1 user, ván mới nhất lên đầu.
    // Dùng while (không phải if) để đọc HẾT các dòng trong ResultSet.
    public List<Score> getScoresByUserId(int userId) throws SQLException{
        String sql = """
                SELECT * FROM SCORES WHERE user_id = ?
                ORDER BY create_at DESC, id DESC
                """;

        List<Score> scores = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);

            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    scores.add(new Score(resultSet.getInt("id"), resultSet.getInt("user_id"), resultSet.getInt("score"), resultSet.getTimestamp("create_at").toLocalDateTime()));
                }
            }
        }
        return scores;
    }

    // DELETE có kiểm tra chủ sở hữu: thêm "AND user_id = ?" để user A
    // không thể xóa điểm của user B dù biết id của ván chơi.
    // executeUpdate() trả về số dòng bị ảnh hưởng -> > 0 nghĩa là xóa thành công.
    public boolean deleteScoreOfUser(int scoreId, int userId) throws SQLException{
        String sql = """
                DELETE FROM SCORES WHERE id = ? AND user_id = ?
                """;

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, scoreId);
            statement.setInt(2, userId);

            return statement.executeUpdate() > 0;
        }
    }

    // Điểm cao nhất còn lại của user (dùng để tính lại highest_score sau khi xóa 1 ván).
    // COALESCE(MAX(score), 0): nếu user không còn ván nào thì MAX = NULL -> trả về 0.
    public int getMaxScoreOfUser(int userId) throws SQLException{
        String sql = """
                SELECT COALESCE(MAX(score), 0) FROM SCORES WHERE user_id = ?
                """;

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return resultSet.getInt(1); // cột thứ 1 của kết quả
                }
            }
        }
        return 0;
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
