package flappybird.model;

public class User {
    private int id;
    private String userName;
    private String password;
    private int highestScore;

    public User(int id, String userName, String password, int highestScore){
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.highestScore = highestScore;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getHighestScore() {
        return highestScore;
    }

    public void setHighestScore(int highestScore) {
        this.highestScore = highestScore;
    }
}
