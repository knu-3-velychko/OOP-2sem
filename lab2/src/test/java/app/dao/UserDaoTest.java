package app.dao;

import app.entities.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;
import java.util.Random;

public class UserDaoTest {
    private final CardDao cardDao = new CardDao();
    private final UserDao userDao = new UserDao(cardDao);

    private String generateStr() {
        byte[] array = new byte[4];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    private User generateUser() {
        return new User(generateStr(), generateStr(), generateStr(), generateStr());
    }

    @Test
    public void testAddDeleteUser() {
        User user = generateUser();
        Assert.assertTrue(userDao.addUser(user));

        int id = userDao.checkUser(user.getUsername(), user.getPassword());
        Assert.assertNotEquals(id, -1);

        userDao.deleteUser(id);
        id = userDao.checkUser(user.getUsername(), user.getPassword());
        Assert.assertEquals(id, -1);
    }
}