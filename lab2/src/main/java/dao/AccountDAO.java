package dao;

import entity.Account;
import lombok.RequiredArgsConstructor;
import mapper.AccountMapper;
import util.JdbcConnection;

import java.sql.*;

@RequiredArgsConstructor
public class AccountDAO {
    private final JdbcConnection jdbcConnection;
    private final AccountMapper accountMapper;

    private static final String INSERT_ACCOUNT = "INSERT INTO accounts (balance) VALUES (?)";

    private static final String UPDATE_ACCOUNT_BALANCE = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

    private static final String GET_ACCOUNT_BY_ID = "SELECT * FROM accounts WHERE id=?";


    public Account save(Account account) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_ACCOUNT, Statement.RETURN_GENERATED_KEYS);
            statement.setFloat(1, account.getBalance());
            statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();

            while (resultSet.next()) {
                account.setId(resultSet.getLong(AccountMapper.ACCOUNT_ID));
            }

            statement.close();
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void updateAccountBalance(Float sum, Long cardId) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_ACCOUNT_BALANCE);
            statement.setFloat(1, sum);
            statement.setLong(2, cardId);

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public Account findAccountById(Long id) {
        Connection connection = jdbcConnection.getConnection();
        try {
            Account account = null;
            PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_BY_ID);
            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();


            while (resultSet.next()) {
                account = accountMapper.map(resultSet);
            }

            statement.close();
            return account;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }
}
