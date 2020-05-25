package com.lab3.demo.service.data;

import com.lab3.demo.entity.Card;
import com.lab3.demo.exception.CardNotFoundException;
import com.lab3.demo.exception.CardStateException;
import com.lab3.demo.repository.CardRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    private CardRepository cardRepository;

    public List<Card> findByUser(String email) {
        return cardRepository.findByUser(email);
    }

//    public Card addCard(Card)

    @Transactional
    public Card blockCard(String cardNumber) {
        Optional<Card> card = cardRepository.findById(cardNumber);
        Card c = card.orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
        if (c.getAccount().isBlocked()) {
            throw new CardStateException("Card with number " + cardNumber + " is blocked.");
        }

        c.getAccount().setBlocked(true);
        return cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
    }

    @Transactional
    public Card unblockCard(String cardNumber) {
        Optional<Card> card = cardRepository.findById(cardNumber);
        Card c = card.orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
        if (!c.getAccount().isBlocked()) {
            throw new CardStateException("Card with number " + cardNumber + " is unblocked.");
        }

        c.getAccount().setBlocked(false);
        return cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
    }

    @Transactional
    public Card replenishAccount(String cardNumber, int amount) {
        Optional<Card> card = cardRepository.findById(cardNumber);
        Card c = card.orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
        if (c.getAccount().isBlocked()) {
            throw new CardStateException("Card with number " + cardNumber + " is unblocked.");
        }

        int currentBalance = c.getAccount().getBalance();
        c.getAccount().setBalance(currentBalance + amount);
        return cardRepository.findById(cardNumber).orElseThrow(() -> new CardNotFoundException("Card with number " + cardNumber + " not found."));
    }

}
