package app.dao;

import app.entities.Card;
import app.entities.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;
import java.util.List;
import java.util.Random;

public class CardDaoTest {
    private final CardDao cardDao = new CardDao();
    private final UserDao userDao = new UserDao(cardDao);

    private User generateUser() {
        return new User(generateStr(), generateStr(), generateStr(), generateStr());
    }

    private String generateStr() {
        byte[] array = new byte[4];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    @Test
    public void testAddDeleteCard() {
        User user = generateUser();
        userDao.addUser(user);
        int userId = userDao.checkUser(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        cardDao.addCard(userId, cardName);
        int cardId = cardDao.getCardId(userId, 0);

        List<Card> cards = cardDao.getCards(userId);
        Assert.assertEquals(cards.size(), 1);
        Assert.assertEquals(cards.get(0).getName(), cardName);

        cardDao.deleteCard(cardId);
        cardId = cardDao.getCardId(userId, 0);
        Assert.assertEquals(cardId, -1);

        userDao.deleteUser(userId);
    }

    @Test
    public void testGetCards() {
        User user = generateUser();
        userDao.addUser(user);
        int userId = userDao.checkUser(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        cardDao.addCard(userId, cardName);
        List<Card> cards = cardDao.getCards(userId);
        Assert.assertEquals(cards.size(), 1);

        cardName = generateStr();
        cardDao.addCard(userId, cardName);
        cards = cardDao.getCards(userId);
        Assert.assertEquals(cards.size(), 2);

        int cardId = cardDao.getCardId(userId, 0);
        int cardId2 = cardDao.getCardId(userId, 1);

        cardDao.deleteCard(cardId);
        cardDao.deleteCard(cardId2);
        userDao.deleteUser(userId);
    }

    @Test
    public void testSetBlocked() {
        User user = generateUser();
        userDao.addUser(user);
        int userId = userDao.checkUser(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        cardDao.addCard(userId, cardName);
//        int cardId = cardDao.getCardId(userId, 0);
//
//        List<Card> cards = cardDao.getCards(userId);
//        cardDao.setBlocked(userId, 0, true);
//
//        Assert.assertFalse(cards.get(0).isBlocked());
//        cards = cardDao.getCards(userId);
//        Assert.assertTrue(cards.get(0).isBlocked());
//
//        cardDao.deleteCard(cardId);
//        userDao.deleteUser(userId);
    }
}