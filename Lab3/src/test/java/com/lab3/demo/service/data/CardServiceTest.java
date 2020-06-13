package com.lab3.demo.service.data;

import com.lab3.demo.entity.Account;
import com.lab3.demo.entity.Card;
import com.lab3.demo.exception.CardAlreadyExists;
import com.lab3.demo.repository.AccountRepository;
import com.lab3.demo.repository.CardRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardServiceTest {

    @Mock
    private CardRepository cardRepository;

    @Mock
    private AccountRepository accountRepository;

    @InjectMocks
    private CardService cardService;


    @Mock
    private Card card;
    @Mock
    private Account account;

    private final String cardId = "cardId";

    @Before
    public void before() {
        when(card.getId()).thenReturn(cardId);
        when(card.getAccount()).thenReturn(account);
    }

    @Test
    public void findByUser() {
        final String email = "email";
        cardService.findByUser(email);
        verify(cardRepository).findByUserEmail(email);
    }

    @Test
    public void addCardCorrect() {
        Optional<Card> oldCard = Optional.empty();
        when(cardRepository.findById(anyString())).thenReturn(oldCard);

        cardService.addCard(card);

        verify(accountRepository).save(any());
        verify(cardRepository).save(any());
    }

    @Test
    public void addCardThrowCardExist() {
        Optional<Card> oldCard = Optional.of(card);
        when(cardRepository.findById(anyString())).thenReturn(oldCard);

        CardAlreadyExists ex = assertThrows(CardAlreadyExists.class,
                () -> cardService.addCard(card));

        assertEquals("Card with id " + cardId + " already exists", ex.getMessage());

        verify(accountRepository, never()).save(any());
        verify(cardRepository, never()).save(any());
    }
}