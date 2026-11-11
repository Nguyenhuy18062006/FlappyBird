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

    /*
     * User tự xóa 1 lượt chơi của mình.
     * Nghiệp vụ (vì sao nằm ở Service chứ không ở DAO):
     *   1. Xóa ván chơi (chỉ xóa được nếu ván đó thuộc về user).
     *   2. Nếu ván vừa xóa là kỷ lục thì highest_score phải giảm theo
     *      -> tính lại MAX(score) từ các ván còn lại rồi cập nhật USERS.
     */
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
