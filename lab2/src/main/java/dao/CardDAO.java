package dao;

import entity.Account;
import entity.Card;
import lombok.RequiredArgsConstructor;
import mapper.CardMapper;
import util.JdbcConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class CardDAO {
    private final JdbcConnection jdbcConnection;
    private final CardMapper cardMapper;

    private final AccountDAO accountDAO;

    private final static String INSERT_CARD = "INSERT INTO cards (client_id, account_id, cardname) VALUES (?, ? ,?)";

    private final static String UPDATE_BLOCKED = "UPDATE cards SET blocked = ? WHERE account_id = ?";

    private final static String GET_ACCOUNT_ID = "SELECT account_id FROM cards WHERE client_id = ?";
    private final static String GET_CARD_BY_USER_ID = "SELECT cards.account_id, balance, cardname,blocked " +
            "FROM cards INNER JOIN accounts ON cards.account_id=accounts.id WHERE client_id = ?";

    private final static String DELETE_CARD = "DELETE FROM cards WHERE account_id = ?";

    public void save(Long userId, String id) {
        Connection connection = jdbcConnection.getConnection();
        try {
            Account account = new Account();
            account.setBalance(0.0f);
            account = accountDAO.save(account);

            PreparedStatement statement = connection.prepareStatement(INSERT_CARD);
            statement.setLong(1, userId);
            statement.setLong(2, account.getId());
            statement.setString(3, id);

            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public Long findCardId(Long userId, Long cardNo) {
        Connection connection = jdbcConnection.getConnection();
        long result = -1L;
        try {
            PreparedStatement statement = connection.prepareStatement(GET_ACCOUNT_ID);
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                if (cardNo == 0) {
                    result = resultSet.getLong(1);
                }
                cardNo--;
            }

            statement.close();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void setBlocked(Long userId, Long cardNo, boolean blocked) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(UPDATE_BLOCKED);
            statement.setBoolean(1, blocked);
            statement.setLong(2, findCardId(userId, cardNo));

            statement.executeUpdate();

            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public List<Card> getCards(Long userId) {
        Connection connection = jdbcConnection.getConnection();
        List<Card> cards = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(GET_CARD_BY_USER_ID);
            statement.setLong(1, userId);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Card card = cardMapper.map(resultSet);
                card.setAccount(accountDAO.findAccountById(card.getAccount().getId()));
                cards.add(card);
            }

            statement.close();
            return cards;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        } finally {
            jdbcConnection.release(connection);
        }
    }

    public void deleteCard(Long cardId) {
        Connection connection = jdbcConnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(DELETE_CARD);
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
