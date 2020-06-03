package app.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PaymentDao {

    public void addPayment(int cardId, int sum, String comment) {
        try {
            Connection connection = DBConnection.createConnection();
            connection.setAutoCommit(false);

            PreparedStatement stmtPaymentInsert = connection.prepareStatement("INSERT INTO payments (account_id,pay, comment) VALUES (?,?,?)");
            stmtPaymentInsert.setInt(1, cardId);
            stmtPaymentInsert.setInt(2, sum);
            stmtPaymentInsert.setString(3, comment);

            int affectedRows = stmtPaymentInsert.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating payment failed, no rows affected.");
            }

            PreparedStatement stmtBalanceUpdate = connection.prepareStatement("UPDATE accounts SET balance = balance + ? WHERE account_id = ?");
            stmtBalanceUpdate.setInt(1, sum);
            stmtBalanceUpdate.setInt(2, cardId);

            stmtBalanceUpdate.executeUpdate();
            connection.commit();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void deletePayments(int cardId) {
        try {
            Connection connection = DBConnection.createConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM payments WHERE account_id = ? ");
            stmt.setInt(1, cardId);

            stmt.executeUpdate();

            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
