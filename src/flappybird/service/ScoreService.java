package flappybird.service;

import java.sql.SQLException;
import java.util.List;

import flappybird.database.ScoreDAO;
import flappybird.model.Score;
import flappybird.model.User;

public class ScoreService {
    private final ScoreDAO scoreDAO;

    public ScoreService() {
        this.scoreDAO = new ScoreDAO();
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

    public Score getScoreByUserId(int userId) throws SQLException{
        return scoreDAO.getScoresByUserId(userId);
    }

    public void deleteScore(int scoreId) throws SQLException{
        scoreDAO.deleteScore(scoreId);
    }

    public List<User> getTopScores() throws SQLException{
        return scoreDAO.getTopScores();
    }
}
