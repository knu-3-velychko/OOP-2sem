package app.services;

import app.dao.CardDao;
import app.entities.Card;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class CardService {
    private final CardDao cardDao;

    public void addCard(int userId, String cardName) {
        cardDao.addCard(userId, cardName);
    }

    public List<Card> getCards(int userId) {
        return cardDao.getCards(userId);
    }

    public void setBlocked(int userID, int cardNo, boolean blocked) {
        cardDao.setBlocked(userID, cardNo, blocked);
    }
}
