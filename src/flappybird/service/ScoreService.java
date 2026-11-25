package flappybird.service;

import java.sql.SQLException;
import java.util.List;

import flappybird.database.ScoreDAO;
import flappybird.database.UserDAO;
import flappybird.model.Score;
import flappybird.model.User;

public class ScoreService {
    private final ScoreDAO scoreDAO;
    private final UserDAO userDAO;

    public ScoreService() {
        this.scoreDAO = new ScoreDAO();
        this.userDAO = new UserDAO();
    }

    public void createScore(Score score) throws SQLException {
        scoreDAO.createScore(score);
    }

    public Score getScoreById(int id) throws SQLException{
        return scoreDAO.getScoreById(id);
    }

    public List<Score> getAllScores() throws SQLException{
        return scoreDAO.getAllScores();
    }

    public List<Score> getScoresByUserId(int userId) throws SQLException{
        return scoreDAO.getScoresByUserId(userId);
    }

    public void deleteScore(int scoreId) throws SQLException{
        scoreDAO.deleteScore(scoreId);
    }

    // Xóa 1 lượt chơi rồi tính lại highest_score
    public boolean deleteScoreOfUser(int scoreId, User user) throws SQLException{
        boolean deleted = scoreDAO.deleteScoreOfUser(scoreId, user.getId());

        if (deleted) {
            int newHighest = scoreDAO.getMaxScoreOfUser(user.getId());
            user.setHighestScore(newHighest);
            userDAO.updateUser(user);
        }

        return deleted;
    }
}
