package dao;

import entity.Payment;
import lombok.RequiredArgsConstructor;
import mapper.PaymentMapper;
import util.JdbcConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class PaymentDAO {
    private final JdbcConnection jdbcConnection;
    private final PaymentMapper paymentMapper;

    private final AccountDAO accountDAO;

    private static final String INSERT_PAYMENT = "INSERT INTO payments (account_id,pay, comment) VALUES (?,?,?)";

    private static final String DELETE_PAYMENT = "DELETE FROM payments WHERE account_id = ? ";

    private static final String GET_PAYMENTS_BY_ACCOUNT = "SELECT  pay, comment FROM payments WHERE account_id = ?";

    public List<Payment> getPaymentsByAccountId(Long accountId) {
        Connection connection = jdbcConnection.getConnection();
        List<Payment> payments = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_PAYMENTS_BY_ACCOUNT);
            statement.setLong(1, accountId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                payments.add(paymentMapper.map(resultSet));
            }

            statement.close();
            return payments;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void addPayment(Long cardId, Float sum, String comment) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT_PAYMENT);
            statement.setLong(1, cardId);
            statement.setFloat(2, sum);
            statement.setString(3, comment);

            statement.executeUpdate();

            accountDAO.updateAccountBalance(sum, cardId);

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void deletePayments(Long cardId) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_PAYMENT);
            statement.setLong(1, cardId);

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
