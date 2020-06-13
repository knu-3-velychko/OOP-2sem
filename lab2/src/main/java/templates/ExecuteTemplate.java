package templates;

import util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class ExecuteTemplate {
    public void execute(JdbcConnection jdbcConnection, String query) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(query);

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
