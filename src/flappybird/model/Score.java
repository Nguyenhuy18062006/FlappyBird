package flappybird.model;

import java.time.LocalDateTime;

public class Score {
    private int id;
    private int userId;
    private int score;
    private  LocalDateTime createAt;

    public Score(int id, int userId, int score, LocalDateTime createAt){
        this.id = id;
        this.userId = userId;
        this.score = score;
        this.createAt = createAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public LocalDateTime getCreateAt() {
        return createAt;
    }

    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
}
