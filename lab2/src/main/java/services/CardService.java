package services;

import dao.CardDAO;
import entity.Card;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CardService {
    private final CardDAO cardDao;

    public void addCard(Long userId, String cardName) {
        cardDao.save(userId, cardName);
    }

    public List<Card> getCards(Long userId) {
        return cardDao.getCards(userId);
    }

    public void setBlocked(Long userID, Long cardNo, boolean blocked) {
        cardDao.setBlocked(userID, cardNo, blocked);
    }
}
