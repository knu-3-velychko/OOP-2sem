package app.dao;

import app.entities.User;
import lombok.RequiredArgsConstructor;

import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class UserDao {
    private final CardDao cardDao;

    public boolean addUser(User user) {
        try {
            Connection connection = DBConnection.createConnection();
            PreparedStatement stmt = connection.prepareStatement("INSERT INTO users ( name, surname,username, password) VALUES ( ?, ?, ?, ?)");

            stmt.setString(1, user.getFirstName());
            stmt.setString(2, user.getSecondName());
            stmt.setString(3, user.getUsername());
            stmt.setString(4, user.getPassword());

            connection.setAutoCommit(true);
            stmt.executeUpdate();
            connection.close();

            return true;

        } catch (SQLException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return false;
    }

    public boolean isAdmin(String username, String password) {
        int clientId = -1;
        try {
            Connection connection = DBConnection.createConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT client_id FROM users WHERE role='ADMIN' AND username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                clientId = resultSet.getInt(1);
            }

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clientId == -1;
    }

    public int checkUser(String username, String password) {
        int clientId = -1;
        try {
            Connection connection = DBConnection.createConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT client_id FROM users WHERE username = ? AND password = ?");
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet resultSet = stmt.executeQuery();

            if (resultSet.next()) {
                clientId = resultSet.getInt(1);
            }

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return clientId;
    }

    public List<User> getUsers() {
        List<User> users = new LinkedList<>();
        String sql = "SELECT  client_id, username FROM users ";
        try {
            Connection connection = DBConnection.createConnection();
            Statement statement = connection.createStatement();
            ResultSet userSet = statement.executeQuery(sql);
            while (userSet.next()) {

                users.add(new User(userSet.getString(2), cardDao.getCards(userSet.getInt(1))));

            }

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return users;
    }

    public void deleteUser(int id) {
        try {
            Connection connection = DBConnection.createConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM users WHERE client_id = ? ");
            stmt.setInt(1, id);

            stmt.executeUpdate();

            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
