package dao;

import entity.User;
import lombok.RequiredArgsConstructor;
import util.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class UserDAO {
    private final JdbcConnection jdbcConnection;
    private final CardDAO cardDao;

    private static final String INSERT_USER = "INSERT INTO users ( name, surname,username, password) VALUES ( ?, ?, ?, ?)";

    private static final String GET_ADMIN_USER = "SELECT id FROM users WHERE role='ADMIN' AND username = ? AND password = ?";
    private static final String GET_USER_BY_LOGIN = "SELECT id FROM users WHERE username = ? AND password = ?";
    private static final String GET_USERS = "SELECT  id, username FROM users";

    private static final String DELETE_USER = "DELETE FROM users WHERE id = ?";

    public boolean addUser(User user) {
        Connection connection = jdbcConnection.getConnection();
        int affectedRows;
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_USER);
            statement.setString(1, user.getFirstName());
            statement.setString(2, user.getSecondName());
            statement.setString(3, user.getUsername());
            statement.setString(4, user.getPassword());

            affectedRows = statement.executeUpdate();

            statement.close();

            return affectedRows <= 0;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public boolean isAdmin(String username, String password) {
        Connection connection = jdbcConnection.getConnection();
        int clientId = -1;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ADMIN_USER);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                clientId = resultSet.getInt(1);
            }

            statement.close();

            return clientId == -1;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public Long findUserByLogin(String username, String password) {
        Long clientId = -1L;
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_USER_BY_LOGIN);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                clientId = resultSet.getLong(1);
            }

            statement.close();

            return clientId;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public List<User> getUsers() {
        Connection connection = jdbcConnection.getConnection();
        List<User> users = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_USERS);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setUsername(resultSet.getString(2));
                user.setCards(cardDao.getCards(resultSet.getLong(1)));
                users.add(user);
            }
            statement.close();

            return users;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void deleteUser(Long id) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_USER);
            statement.setLong(1, id);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }
}
