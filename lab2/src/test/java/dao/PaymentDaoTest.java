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
import java.util.stream.Collectors;

public class PaymentDaoTest {
    private final CardDAO cardDao = (CardDAO) BeanFactory.getBean(CardDAO.class);
    private final UserDAO userDao = (UserDAO) BeanFactory.getBean(UserDAO.class);
    private final PaymentDAO paymentDao = (PaymentDAO) BeanFactory.getBean(PaymentDAO.class);

    private User generateUser() {
        return new User(generateStr(), generateStr(), generateStr(), generateStr(), new ArrayList<>());
    }

    private String generateStr() {
        byte[] array = new byte[4];
        new Random().nextBytes(array);
        return Base64.getEncoder().encodeToString(array);
    }

    @Test
    public void testAddDeletePayment() {
        float sum = 20f;

        User user = generateUser();
        String comment = generateStr();
        userDao.addUser(user);
        Long userId = userDao.findUserByLogin(user.getUsername(), user.getPassword());

        String cardName = generateStr();
        cardDao.save(userId, cardName);
        Long cardId = cardDao.findCardId(userId, 0L);
        paymentDao.addPayment(cardId, sum, comment);


        List<Card> cards = cardDao.getCards(userId);

        cards = cards.stream().peek(item -> item.getAccount().setPayments(paymentDao.getPaymentsByAccountId(item.getAccount().getId()))).collect(Collectors.toList());

        Assert.assertEquals(comment, cards.get(0).getAccount().getPayments().get(0).getComment());
        Assert.assertEquals(Float.valueOf(sum), cards.get(0).getAccount().getPayments().get(0).getPay());

        paymentDao.deletePayments(cardId);
        cardDao.deleteCard(cardId);
        cardId = cardDao.findCardId(userId, 0L);
        Assert.assertEquals(cardId, Long.valueOf(-1L));

        userDao.deleteUser(userId);
    }
}
