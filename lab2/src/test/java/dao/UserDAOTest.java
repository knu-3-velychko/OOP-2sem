package dao;

import entity.User;
import org.junit.Assert;
import org.junit.Test;
import util.BeanFactory;

import java.util.ArrayList;
import java.util.Base64;
import java.util.Random;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class UserDAOTest {
    private final CardDAO cardDao = (CardDAO) BeanFactory.getBean(CardDAO.class);
    private final UserDAO userDao = (UserDAO) BeanFactory.getBean(UserDAO.class);

    private String generateStr() {
        byte[] array = new byte[4];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    private User generateUser() {
        return new User(generateStr(), generateStr(), generateStr(), generateStr(),new ArrayList<>());
    }

    @Test
    public void testAddDeleteUser() {
        User user = generateUser();
        Assert.assertTrue(userDao.addUser(user));

        Long id = userDao.findUserByLogin(user.getUsername(), user.getPassword());
        assertNotEquals(id, Long.valueOf(-1L));

        userDao.deleteUser(id);
        id = userDao.findUserByLogin(user.getUsername(), user.getPassword());

        assertEquals(id, Long.valueOf(-1L));
    }
}
