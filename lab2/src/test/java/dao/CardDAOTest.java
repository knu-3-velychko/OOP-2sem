package dao;

import entity.Card;
import entity.User;
import org.junit.Assert;
import org.junit.Test;
import util.BeanFactory;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Random;

public class CardDAOTest {
    private final CardDAO cardDao = (CardDAO) BeanFactory.getBean(CardDAO.class);
    private final UserDAO userDao = (UserDAO) BeanFactory.getBean(UserDAO.class);

    private User generateUser() {
        return new User(generateStr(), generateStr(), generateStr(), generateStr(),new ArrayList<>());
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
        Long userId = userDao.findUserByLogin(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        cardDao.save(userId, cardName);
        Long cardId = cardDao.findCardId(userId, 0L);

        List<Card> cards = cardDao.getCards(userId);
        Assert.assertEquals(cards.size(), 1);
        Assert.assertEquals(cards.get(0).getName(), cardName);

        cardDao.deleteCard(cardId);
        cardId = cardDao.findCardId(userId, 0L);
        Assert.assertEquals(java.util.Optional.ofNullable(cardId), -1L);

        userDao.deleteUser(userId);
    }

    @Test
    public void testGetCards() {
        User user = generateUser();
        userDao.addUser(user);
        Long userId = userDao.findUserByLogin(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        cardDao.save(userId, cardName);
        List<Card> cards = cardDao.getCards(userId);
        Assert.assertEquals(cards.size(), 1);

        cardName = generateStr();
        cardDao.save(userId, cardName);
        cards = cardDao.getCards(userId);
        Assert.assertEquals(cards.size(), 2);

        Long cardId = cardDao.findCardId(userId, 0L);
        Long cardId2 = cardDao.findCardId(userId, 1L);

        cardDao.deleteCard(cardId);
        cardDao.deleteCard(cardId2);
        userDao.deleteUser(userId);
    }

    @Test
    public void testSetBlocked() {
        User user = generateUser();
        userDao.addUser(user);
        Long userId = userDao.findUserByLogin(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        cardDao.save(userId, cardName);
        Long cardId = cardDao.findCardId(userId, 0L);

        List<Card> cards = cardDao.getCards(userId);
        cardDao.setBlocked(userId, 0L, true);

        Assert.assertFalse(cards.get(0).isBlocked());
        cards = cardDao.getCards(userId);
        Assert.assertTrue(cards.get(0).isBlocked());

        cardDao.deleteCard(cardId);
        userDao.deleteUser(userId);
    }
}
