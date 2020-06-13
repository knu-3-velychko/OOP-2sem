package util;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

import org.apache.ibatis.jdbc.ScriptRunner;


public class JdbcConnection implements ConnectionPool {
    private static final int POOL_SIZE = 4;
    private static final Logger logger = LogManager.getRootLogger();

    private final String url, username, password;
    private final List<Connection> connectionPool = new ArrayList<>();
    private final List<Connection> usedConnections = new ArrayList<>();

    private static final Object monitor = new Object();

    private static JdbcConnection instance;


    private JdbcConnection() throws SQLException, ClassNotFoundException, FileNotFoundException {
        String currentPath = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).getPath();
        String configPath = currentPath + "application.properties";

        Properties properties = new Properties();
        try {
            properties.load(new FileInputStream(configPath));
        } catch (IOException exception) {
            exception.printStackTrace();
        }

        url = properties.getProperty("jdbc.database.url");
        username = properties.getProperty("jdbc.database.username");
        password = properties.getProperty("jdbc.database.password");

        Connection connection = createConnection();
        ScriptRunner sr = new ScriptRunner(createConnection());
        Reader reader = new BufferedReader(new FileReader(currentPath + "/db/ddl.sql"));
        sr.runScript(reader);

        connection.close();

        for (int i = 0; i < POOL_SIZE; i++) {
            connectionPool.add(createConnection());
        }
    }

    public static JdbcConnection getInstance() throws SQLException, ClassNotFoundException, FileNotFoundException {
        if (instance == null) {
            instance = new JdbcConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        while (connectionPool.isEmpty()) {
            synchronized (monitor) {
                try {
                    monitor.wait();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage());
                }
            }
        }

        Connection connection = connectionPool.remove(connectionPool.size() - 1);
        usedConnections.add(connection);

        return connection;
    }

    public boolean release(Connection connection) {
        connectionPool.add(connection);
        return usedConnections.remove(connection);
    }

    private Connection createConnection() throws ClassNotFoundException, SQLException {
        Class.forName("org.postgresql.Driver");
        return DriverManager.getConnection(url, username, password);
    }
}
