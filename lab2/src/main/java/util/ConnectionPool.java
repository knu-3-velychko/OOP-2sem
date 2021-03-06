package util;

import java.sql.Connection;

public interface ConnectionPool {
    Connection getConnection();

    boolean release(Connection connection);
}
