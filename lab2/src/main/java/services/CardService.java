package services;

import dao.CardDAO;
import dao.PaymentDAO;
import entity.Card;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CardService {
    private final CardDAO cardDao;
    private final PaymentDAO paymentDAO;

    public void addCard(Long userId, String cardName) {
        cardDao.save(userId, cardName);
    }

    public List<Card> getCards(Long userId) {

        return cardDao.getCards(userId).stream().peek(item -> item.getAccount().setPayments(paymentDAO.getPaymentsByAccountId(item.getAccount().getId()))).collect(Collectors.toList());
    }

    public void setBlocked(Long userID, Long cardNo, boolean blocked) {
        cardDao.setBlocked(userID, cardNo, blocked);
    }
}
