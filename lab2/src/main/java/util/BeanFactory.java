package util;

import dao.AccountDAO;
import dao.CardDAO;
import dao.PaymentDAO;
import dao.UserDAO;
import entity.Card;
import entity.Payment;
import mapper.AccountMapper;
import mapper.CardMapper;
import mapper.PaymentMapper;
import mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import services.CardService;
import services.PaymentService;
import services.UserService;

import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class BeanFactory {
    private static Map<Class<?>, Object> beans = new HashMap<>();
    private static final Logger logger = LogManager.getRootLogger();

    static {
        JdbcConnection jdbcConnection = null;
        try {
            jdbcConnection = JdbcConnection.getInstance();
        } catch (SQLException | ClassNotFoundException | FileNotFoundException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }

        AccountDAO accountDAO = new AccountDAO(jdbcConnection, new AccountMapper());
        CardDAO cardDAO = new CardDAO(jdbcConnection, new CardMapper(), accountDAO);
        UserDAO userDAO = new UserDAO(jdbcConnection, cardDAO);
        PaymentDAO paymentDAO = new PaymentDAO(jdbcConnection, new PaymentMapper(), accountDAO);

        beans.put(AccountDAO.class, accountDAO);
        beans.put(CardDAO.class, cardDAO);
        beans.put(UserDAO.class, userDAO);
        beans.put(PaymentDAO.class, paymentDAO);

        CardService cardService = new CardService(cardDAO,paymentDAO);
        PaymentService paymentService = new PaymentService(paymentDAO, cardDAO);
        UserService userService = new UserService(userDAO);

        beans.put(CardService.class, cardService);
        beans.put(PaymentService.class, paymentService);
        beans.put(UserService.class, userService);
    }

    public static Object getBean(Class<?> beanClass) {
        return beans.get(beanClass);
    }
}
