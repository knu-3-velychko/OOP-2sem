package app.dao;

import app.entities.Account;
import app.entities.Card;
import app.entities.Payment;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CardDao {
    public void addCard(int userId, String cardName) {
        try {
            Connection connection = DBConnection.createConnection();
            connection.setAutoCommit(false);
            PreparedStatement stmtAccountInsert = connection.prepareStatement("INSERT INTO accounts (balance) VALUES (0)", Statement.RETURN_GENERATED_KEYS);

            int affectedRows = stmtAccountInsert.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }

            int id;
            try (ResultSet generatedKeys = stmtAccountInsert.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }

            PreparedStatement stmtCardInsert = connection.prepareStatement("INSERT INTO cards (client_id, account_id, cardname) VALUES (?, ? ,?)");
            stmtCardInsert.setInt(1, userId);
            stmtCardInsert.setInt(2, id);
            stmtCardInsert.setString(3, cardName);

            stmtCardInsert.executeUpdate();

            connection.commit();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public int getCardId(int userId, int cardNo) {
        int result = -1;
        try {
            Connection connection = DBConnection.createConnection();

            PreparedStatement stmt = connection.prepareStatement("SELECT account_id FROM cards WHERE client_id = ?");
            stmt.setInt(1, userId);
            ResultSet userSet = stmt.executeQuery();

            while (userSet.next()) {
                if (cardNo == 0) {
                    result = userSet.getInt(1);
                }
                cardNo--;
            }

            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    public void setBlocked(int userID, int cardNo, boolean blocked) {
        try {
            Connection connection = DBConnection.createConnection();
            PreparedStatement stmt = connection.prepareStatement("UPDATE cards SET blocked = ? WHERE account_id = ?");
            stmt.setBoolean(1, blocked);
            stmt.setInt(2, getCardId(userID, cardNo));
            stmt.executeUpdate();
            connection.close();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public List<Card> getCards(int userId) {
        List<Card> cards = new LinkedList<>();
        try {
            Connection connection = DBConnection.createConnection();
            PreparedStatement stmt = connection.prepareStatement("SELECT cards.account_id, balance, cardname,blocked " +
                    "FROM cards INNER JOIN accounts ON cards.account_id=accounts.account_id WHERE client_id = ?");
            stmt.setInt(1, userId);

            ResultSet cardSet = stmt.executeQuery();

            while (cardSet.next()) {
                int balance = cardSet.getInt("balance");
                int accountId = cardSet.getInt("account_id");
                String cardName = cardSet.getString("cardname");
                boolean blocked = cardSet.getBoolean("blocked");


                stmt = connection.prepareStatement("SELECT  pay, comment FROM payments WHERE account_id = ?");
                stmt.setInt(1, accountId);
                ResultSet paymentSet = stmt.executeQuery();
                List<Payment> payments = new ArrayList<>();

                while (paymentSet.next()) {
                    int pay = paymentSet.getInt("pay");
                    String comment = paymentSet.getString("comment");
                    payments.add(new Payment(pay, comment));
                }
                cards.add(new Card(cardName, blocked, new Account(accountId, balance, payments)));
            }

            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return cards;
    }

    public void deleteCard(int cardId) {
        try {
            Connection connection = DBConnection.createConnection();
            PreparedStatement stmt = connection.prepareStatement("DELETE FROM cards WHERE account_id = ? ");
            stmt.setInt(1, cardId);

            stmt.executeUpdate();

            connection.close();

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
