package mapper;

import entity.User;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserMapper {
    public static final String USER_NAME = "name";
    public static final String USER_SURNAME = "surname";
    public static final String USER_PASSWORD = "password";
    public static final String USER_USERNAME = "username";

    public User map(ResultSet resultSet) throws SQLException {
        User user = new User();

        user.setUsername(resultSet.getString(USER_USERNAME));
        user.setFirstName(resultSet.getString(USER_NAME));
        user.setSecondName(resultSet.getString(USER_SURNAME));
        user.setPassword(resultSet.getString(USER_PASSWORD));

        return user;
    }
}
