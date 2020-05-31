package com.lab3.demo.service.data;

import com.lab3.demo.entity.Account;
import com.lab3.demo.entity.Card;
import com.lab3.demo.exception.CardAlreadyExists;
import com.lab3.demo.exception.CardNotFoundException;
import com.lab3.demo.exception.CardStateException;
import com.lab3.demo.repository.AccountRepository;
import com.lab3.demo.repository.CardRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final AccountRepository accountRepository;

    public List<Card> findByUser(String email) {
        return cardRepository.findByUserEmail(email);
    }

    @Transactional
    public Card addCard(Card card) {
        Optional<Card> oldCard = cardRepository.findById(card.getId());
        if (oldCard.isPresent()) {
            throw new CardAlreadyExists("Card with id " + card.getId() + " alredy exists");
        }
        accountRepository.save(card.getAccount());
        return cardRepository.save(card);
    }

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
