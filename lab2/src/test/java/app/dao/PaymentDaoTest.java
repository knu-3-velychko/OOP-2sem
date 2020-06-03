package app.dao;

import app.entities.Card;
import app.entities.Payment;
import app.entities.User;
import org.junit.Assert;
import org.junit.Test;

import java.util.Base64;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;

public class PaymentDaoTest {
    private final CardDao cardDao = new CardDao();
    private final UserDao userDao = new UserDao(cardDao);
    private final PaymentDao paymentDao = new PaymentDao();

    private User generateUser() {
        return new User(generateStr(), generateStr(), generateStr(), generateStr());
    }

    private String generateStr() {
        byte[] array = new byte[4];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    @Test
    public void testAddDeletePayment() {
        int sum = 20;

        User user = generateUser();
        String comment = generateStr();
        userDao.addUser(user);
        int userId = userDao.checkUser(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        cardDao.addCard(userId, cardName);
        int cardId = cardDao.getCardId(userId, 0);
        paymentDao.addPayment(cardId, sum, comment);


        List<Card> cards = cardDao.getCards(userId);

        Assert.assertEquals(comment, cards.get(0).getPayments().get(0).getComment());
        Assert.assertEquals(sum, cards.get(0).getPayments().get(0).getPay());

        paymentDao.deletePayments(cardId);
        cardDao.deleteCard(cardId);
        cardId = cardDao.getCardId(userId, 0);
        Assert.assertEquals(cardId, -1);

        userDao.deleteUser(userId);
    }
}