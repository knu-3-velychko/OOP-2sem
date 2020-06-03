package app.services;

import app.dao.CardDao;
import app.dao.PaymentDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentService {
    private final PaymentDao paymentDao;
    private final CardDao cardDao;

    public void addPayment(int userId, int cardId, int sum, String comment) {
        if (cardDao.getCards(userId).stream().anyMatch(item -> item.getId() == cardId)) {
            paymentDao.addPayment(cardId, sum, comment);
        }
    }
}
