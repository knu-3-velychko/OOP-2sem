package com.lab3.demo.converter;

import com.lab3.demo.dto.PaymentDTO;
import com.lab3.demo.entity.Card;
import com.lab3.demo.entity.Payment;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class PaymentConverterTest {

    private final int paymentAmount = 20;
    private final String cardFromId = "cardFromId";
    private final String cardToId = "cardToId";
    @InjectMocks
    private PaymentConverter paymentConverter;
    @Mock
    private Payment payment;
    @Mock
    private PaymentDTO paymentDto;
    @Mock
    private Card cardFrom;
    @Mock
    private Card cardTo;

    @Before
    public void before(){
        when(cardFrom.getId()).thenReturn(cardFromId);
        when(cardTo.getId()).thenReturn(cardToId);
    }

    @Test
    public void convertToDto() {
        when(payment.getAmount()).thenReturn(paymentAmount);
        when(payment.getFrom()).thenReturn(cardFrom);
        when(payment.getTo()).thenReturn(cardTo);

        PaymentDTO paymentDTO = paymentConverter.convertToDto(payment);

        assertEquals(paymentDTO.getAmount(), paymentAmount);
        assertEquals(paymentDTO.getCardFrom(), cardFromId);
        assertEquals(paymentDTO.getCardTo(), cardToId);
    }

    @Test
    public void convertToEntity() {
        when(paymentDto.getAmount()).thenReturn(paymentAmount);

        Payment payment = paymentConverter.convertToEntity(paymentDto, cardFrom, cardTo);

        assertEquals(payment.getAmount(), paymentAmount);
        assertEquals(payment.getFrom().getId(), cardFromId);
        assertEquals(payment.getTo().getId(), cardToId);
    }
}