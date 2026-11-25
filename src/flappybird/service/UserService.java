package flappybird.service;

import java.sql.SQLException;
import java.util.List;

import at.favre.lib.crypto.bcrypt.BCrypt;
import flappybird.database.UserDAO;
import flappybird.model.User;

public class UserService {
    private final UserDAO userDAO;

    public UserService(){
        this.userDAO = new UserDAO();
    }

    public void createUser(User user) throws SQLException{
        String hashed = BCrypt.withDefaults().hashToString(12, user.getPassword().toCharArray());
        user.setPassword(hashed);
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

        BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
        if (!result.verified) {
            return null;
        }


        return user;
    }

    // Kiểm tra mật khẩu thô có khớp với hash BCrypt đang lưu trong DB không.
    private boolean checkPassword(User user, String rawPassword){
        return BCrypt.verifyer().verify(rawPassword.toCharArray(), user.getPassword()).verified;
    }

    // UPDATE: đổi mật khẩu (xác nhận mật khẩu cũ, hash mật khẩu mới)
    public boolean changePassword(User user, String oldPassword, String newPassword) throws SQLException{
        if (!checkPassword(user, oldPassword)) {
            return false;
        }

        String hashed = BCrypt.withDefaults().hashToString(12, newPassword.toCharArray());
        user.setPassword(hashed);
        userDAO.updateUser(user);
        return true;
    }

    // DELETE: xóa tài khoản (dữ liệu liên quan tự xóa nhờ ON DELETE CASCADE)
    public boolean deleteAccount(User user, String password) throws SQLException{
        if (!checkPassword(user, password)) {
            return false;
        }

        userDAO.deleteUser(user.getId());
        return true;
    }

    public List<User> getTopUsers() throws SQLException{
        return userDAO.getTopUsers();
    }
}
