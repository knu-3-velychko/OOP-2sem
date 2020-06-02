package com.lab3.demo.converter;

import com.lab3.demo.dto.CardDTO;
import com.lab3.demo.entity.Account;
import com.lab3.demo.entity.Card;
import com.lab3.demo.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CardConverterTest {

    @InjectMocks
    private CardConverter cardConverter;
    @Mock
    private Card card;
    @Mock
    private User user;
    @Mock
    private CardDTO cardDTO;
    @Mock
    private Account account;

    @Before
    public void before() {
        String cardFromId = "cardId";
        when(card.getId()).thenReturn(cardFromId);
    }

    @Test
    public void convertToDTO() {
        when(card.getAccount()).thenReturn(account);
        when(card.getUser()).thenReturn(user);

        CardDTO cardDTO = cardConverter.convertToDTO(card);

        assertEquals(account.getBalance(), cardDTO.getBalance());
        assertEquals(user.getEmail(), cardDTO.getUserEmail());
        assertEquals(card.getId(), cardDTO.getCardNumber());
    }

    @Test
    public void convertToEntity() {
        Card card = cardConverter.convertToEntity(cardDTO);

        assertEquals(cardDTO.getCardNumber(), card.getId());
    }
}