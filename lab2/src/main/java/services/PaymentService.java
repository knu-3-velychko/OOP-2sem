package services;

import dao.CardDAO;
import dao.PaymentDAO;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PaymentService {
    private final PaymentDAO paymentDao;
    private final CardDAO cardDao;

    public void addPayment(Long userId, Long cardId, Float sum, String comment) {
        if (cardDao.getCards(userId).stream().anyMatch(item -> item.getAccount().getId().equals(cardId))) {
            paymentDao.addPayment(cardId, sum, comment);
        }
    }
}
