package mapper;

import entity.Account;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountMapper {
    public static final String ACCOUNT_ID = "id";
    public static final String ACCOUNT_BALANCE = "balance";

    public Account map(ResultSet resultSet) throws SQLException {
        Account account = new Account();

        account.setId(resultSet.getLong(ACCOUNT_ID));
        account.setBalance(resultSet.getFloat(ACCOUNT_BALANCE));

        return account;
    }
}
