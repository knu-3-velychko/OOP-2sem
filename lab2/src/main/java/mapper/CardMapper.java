package mapper;

import entity.Account;
import entity.Card;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CardMapper {
    public static final String CARD_CARDNAME = "cardname";
    public static final String CARD_BLOCKED = "blocked";
    public static final String CARD_ACCOUNT_ID = "account_id";

    public Card map(ResultSet resultSet) throws SQLException {
        Card card = new Card();

        card.setName(resultSet.getString(CARD_CARDNAME));
        card.setBlocked(resultSet.getBoolean(CARD_BLOCKED));
        Account account = new Account();
        account.setId(resultSet.getLong(CARD_ACCOUNT_ID));
        card.setAccount(account);

        return card;
    }
}
