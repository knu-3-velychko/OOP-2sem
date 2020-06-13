package com.lab3.demo.service.data;

import com.lab3.demo.converter.PaymentConverter;
import com.lab3.demo.dto.PaymentDTO;
import com.lab3.demo.entity.Account;
import com.lab3.demo.entity.Card;
import com.lab3.demo.exception.CardIsBlocked;
import com.lab3.demo.exception.CardNotFoundException;
import com.lab3.demo.exception.OutOfAccountBalance;
import com.lab3.demo.repository.CardRepository;
import com.lab3.demo.repository.PaymentRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class PaymentServiceTest {
    private final String cardFromStr = "cardFrom";
    private final String cardToStr = "cardTo";
    @Mock
    private PaymentRepository paymentRepository;
    @Mock
    private PaymentConverter paymentConverter;
    @Mock
    private CardRepository cardRepository;
    @InjectMocks
    private PaymentService paymentService;
    @Mock
    private PaymentDTO paymentDTO;
    @Mock
    private Card cardFrom;
    @Mock
    private Card cardTo;
    @Mock
    private Account acFrom;
    @Mock
    private Account acTo;

    @Before
    public void before() {

        when(paymentDTO.getCardFrom()).thenReturn(cardFromStr);
        when(paymentDTO.getCardTo()).thenReturn(cardToStr);

        Optional<Card> cardFromOpt = Optional.of(cardFrom);
        Optional<Card> cardToOpt = Optional.of(cardTo);

        when(cardFrom.getAccount()).thenReturn(acFrom);
        when(cardTo.getAccount()).thenReturn(acTo);

        when(cardRepository.findById(cardFromStr)).thenReturn(cardFromOpt);
        when(cardRepository.findById(cardToStr)).thenReturn(cardToOpt);

        when(cardFrom.getId()).thenReturn(cardFromStr);
        when(cardTo.getId()).thenReturn(cardToStr);
    }

    @Test
    public void addPaymentCorrect() {

        final int balanceFrom = 20;
        final int balanceTo = 10;
        final int amount = 5;

        when(paymentDTO.getAmount()).thenReturn(amount);

        when(acFrom.getBalance()).thenReturn(balanceFrom);
        when(acFrom.isBlocked()).thenReturn(false);

        when(acTo.getBalance()).thenReturn(balanceTo);
        when(acTo.isBlocked()).thenReturn(false);

        assertEquals(paymentService.addPayment(paymentDTO), cardFrom);

        verify(acFrom).setBalance(balanceFrom - amount);
        verify(acTo).setBalance(balanceTo + amount);
    }

    @Test
    public void addPaymentThrowNoEnoughMoney() {
        final int balanceFrom = 20;
        final int amount = 25;

        when(paymentDTO.getAmount()).thenReturn(amount);

        when(acFrom.getBalance()).thenReturn(balanceFrom);

        OutOfAccountBalance ex = assertThrows(OutOfAccountBalance.class,
                () -> paymentService.addPayment(paymentDTO));

        assertEquals(ex.getMessage(), "Not enough money on card " + cardFromStr);
        verify(acFrom, never()).setBalance(anyInt());
        verify(acTo, never()).setBalance(anyInt());
    }

    @Test
    public void addPaymentThrowCardFromBlocked() {

        final int balanceFrom = 20;
        final int amount = 5;

        when(paymentDTO.getAmount()).thenReturn(amount);

        when(acFrom.getBalance()).thenReturn(balanceFrom);
        when(acFrom.isBlocked()).thenReturn(true);

        CardIsBlocked ex = assertThrows(CardIsBlocked.class,
                () -> paymentService.addPayment(paymentDTO));
        assertEquals(ex.getMessage(), "Card from with id " + cardFromStr + " is blocked");
        verify(acFrom, never()).setBalance(anyInt());
        verify(acTo, never()).setBalance(anyInt());
    }

    @Test
    public void addPaymentThrowCardToBlocked() {

        final int balanceFrom = 20;
        final int balanceTo = 20;
        final int amount = 5;

        when(paymentDTO.getAmount()).thenReturn(amount);

        when(acFrom.getBalance()).thenReturn(balanceFrom);
        when(acFrom.isBlocked()).thenReturn(false);

        when(acTo.isBlocked()).thenReturn(true);

        CardIsBlocked ex = assertThrows(CardIsBlocked.class,
                () -> paymentService.addPayment(paymentDTO));
        assertEquals(ex.getMessage(), "Card to with id " + cardToStr + " is blocked");

        verify(acFrom, never()).setBalance(anyInt());
        verify(acTo, never()).setBalance(anyInt());
    }

    @Test
    public void replenishAccountCorrect() {
        final int amount = 5;
        final int balanceTo = 10;
        Optional<Card> cardToOpt = Optional.of(cardTo);

        when(cardRepository.findById(anyString())).thenReturn(cardToOpt);
        when(paymentDTO.getAmount()).thenReturn(amount);
        when(acTo.isBlocked()).thenReturn(false);
        when(acTo.getBalance()).thenReturn(balanceTo);

        paymentService.replenishAccount(paymentDTO);

        verify(acTo).setBalance(balanceTo + amount);
        verify(paymentRepository).save(any());
    }

    @Test
    public void replenishAccountThrowCardBlocked() {
        Optional<Card> cardToOpt = Optional.of(cardTo);

        when(cardRepository.findById(anyString())).thenReturn(cardToOpt);
        when(acTo.isBlocked()).thenReturn(true);

        CardIsBlocked ex = assertThrows(CardIsBlocked.class,
                () -> paymentService.replenishAccount(paymentDTO));

        assertEquals(ex.getMessage(), "Card to with id " + cardToStr + " is blocked");
        verify(paymentRepository, never()).save(any());
    }

    @Test
    public void replenishAccountThrowCardNoFound() {
        when(cardRepository.findById(anyString())).thenReturn(Optional.empty());
        CardNotFoundException ex = assertThrows(CardNotFoundException.class,
                () -> paymentService.replenishAccount(paymentDTO));

        assertEquals(ex.getMessage(), "Card  not found.");
        verify(paymentRepository, never()).save(any());
    }
}