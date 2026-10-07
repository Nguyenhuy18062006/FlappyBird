package flappybird.database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import flappybird.model.User;

public class UserDAO {
    public void createUser(User user) throws SQLException{
        String sql = """
                INSERT INTO USERS (user_name, password)
                VALUE (?, ?) 
                """;

        
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, user.getUserName());
            statement.setString( 2, user.getPassword());

            //Yêu cầu JDBC thực thi câu lệnh SQL nằm trong biến sql và thực hiện thay đổi dữ liệu trong database.
            statement.executeUpdate();
        }
        //Vậy bản chất PreparedStatement đang chuẩn bị câu truy vấn sql trên
    }

    public User getUseById(int userId) throws SQLException{
        String sql = """
                SELECT * FROM USERS WHERE id = ?;
                """;
        
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);

            //Nhận kết quả truy vấn về resultSet
            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return new User(resultSet.getInt("id"), resultSet.getString("user_name"), resultSet.getString("password"), resultSet.getInt("highest_score"));
                }
            }
        }
        return null;
    }

    public User getUserByUserName(String userName) throws SQLException{
        String sql = """
                SELECT * FROM USERS WHERE user_name = ?;
                """;

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, userName);

            try(ResultSet resultSet = statement.executeQuery()){
                if(resultSet.next()){
                    return new User(resultSet.getInt("id"), resultSet.getString("user_name"), resultSet.getString("password"), resultSet.getInt("highest_score"));
                }
            }
        }
        return null;
    }

    public List<User> getAllUsers() throws SQLException{
        String sql = """
                SELECT * FROM USERS;
                """;
        
        List<User> users = new ArrayList<>();

        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            
            try(ResultSet resultSet = statement.executeQuery()){
                while(resultSet.next()){
                    User user = new User(resultSet.getInt("id"), resultSet.getString("user_name"), resultSet.getString("password"), resultSet.getInt("highest_score"));
                    
                    //Thêm User vào list
                    users.add(user);
                }
            }
        }
        return users;
    }

    public void updateUser(User user) throws SQLException{
        String sql = """
                UPDATE USERS SET user_name = ?, password = ?, highest_score = ? WHERE id = ?
                """;
        try (Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setString(1, user.getUserName());
            statement.setString( 2, user.getPassword());
            statement.setInt(3, user.getHighestScore());
            statement.setInt(4, user.getId());

            statement.executeUpdate();
        }
    }

    public void deleteUser(int userId) throws SQLException{
        String sql = """
                DELETE FROM USERS WHERE id = ?;
                """;
        try(Connection connection = DatabaseConnection.getConnection();
        PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);

            statement.executeUpdate();
        }
    }
}
