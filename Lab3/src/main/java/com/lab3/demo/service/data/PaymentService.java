package com.lab3.demo.service.data;

import com.lab3.demo.converter.PaymentConverter;
import com.lab3.demo.dto.PaymentDTO;
import com.lab3.demo.entity.Card;
import com.lab3.demo.exception.CardNotFoundException;
import com.lab3.demo.exception.OutOfAccountBalance;
import com.lab3.demo.repository.CardRepository;
import com.lab3.demo.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final PaymentConverter paymentConverter;

    private final CardRepository cardRepository;

    @Transactional
    public Card addPayment(PaymentDTO paymentDTO) {
        Optional<Card> cardFrom = cardRepository.findById(paymentDTO.getCardFrom());
        Optional<Card> cardTo = cardRepository.findById(paymentDTO.getCardTo());

        Card cFrom = cardFrom.orElseThrow(() -> new CardNotFoundException("Card  not found."));
        Card cTo = cardTo.orElseThrow(() -> new CardNotFoundException("Card  not found."));


        if (cFrom.getAccount().getBalance() < paymentDTO.getAmount()) {
            throw new OutOfAccountBalance("Not enoung money on card " + cFrom.getId());
        }

        int balanceFrom = cFrom.getAccount().getBalance();
        int balanceTo = cTo.getAccount().getBalance();

        cFrom.getAccount().setBalance(balanceFrom - paymentDTO.getAmount());
        cTo.getAccount().setBalance(balanceTo + paymentDTO.getAmount());

        paymentRepository.save(paymentConverter.convertToEntity(paymentDTO, cFrom, cTo));

        return cFrom;
    }
}
