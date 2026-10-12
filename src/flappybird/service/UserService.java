package flappybird.service;

import java.sql.SQLException;
import java.util.List;

import flappybird.database.UserDAO;
import flappybird.model.User;

public class UserService {
    private final UserDAO userDAO;

    public UserService(){
        this.userDAO = new UserDAO();
    }

    public void createUser(User user) throws SQLException{
        userDAO.createUser(user);
    }

    public User getUserById(int userId) throws SQLException{
        return  userDAO.getUseById(userId);
    }

    public User getUserByUserName(String userName) throws SQLException{
        return userDAO.getUserByUserName(userName);
    }

    public List<User> getAllUsers() throws SQLException{
        return userDAO.getAllUsers();
    }

    public void updateUser(User user) throws  SQLException{
        userDAO.updateUser(user);
    }

    public void deleteUser(int userId) throws SQLException{
        userDAO.deleteUser(userId);
    }

    public User login(String userName, String password) throws SQLException{
        User user = userDAO.getUserByUserName(userName);

        if(user == null){
            return null;
        }

        if (!user.getPassword().equals(password)) {
            return null;
        }

        return user;
    }

    public List<User> getTopUsers() throws SQLException{
        return userDAO.getTopUsers();
    }
}
