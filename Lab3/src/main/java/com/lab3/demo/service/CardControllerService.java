package com.lab3.demo.service;

import com.lab3.demo.converter.CardConverter;
import com.lab3.demo.dto.CardDTO;
import com.lab3.demo.entity.Account;
import com.lab3.demo.entity.Card;
import com.lab3.demo.entity.User;
import com.lab3.demo.exception.CardNumberNotNullException;
import com.lab3.demo.exception.UserNotFound;
import com.lab3.demo.service.data.CardService;
import com.lab3.demo.service.data.UserService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardControllerService {
    private final CardService cardService;
    private final UserService userService;

    private final CardConverter cardConverter;

    public CardDTO addCard(CardDTO cardDTO) {
        Card card = cardConverter.convertToEntity(cardDTO);
        Optional<User> user = userService.findUserByEmail(cardDTO.getUserEmail());
        if (!user.isPresent()) {
            throw new UserNotFound("User with email " + cardDTO.getUserEmail() + " not found");
        }
        card.setUser(user.get());
        card.setAccount(new Account(null, cardDTO.getBalance(), cardDTO.isBlocked()));
        card = this.cardService.addCard(card);
        return cardConverter.convertToDTO(card);
    }

    public List<CardDTO> findByUser(String email) {
        return cardConverter.convertToListDTO(cardService.findByUser(email));
    }

    public CardDTO blockCard(String cardNumber) {
        if (cardNumber == null) {
            throw new CardNumberNotNullException();
        }

        return cardConverter.convertToDTO(cardService.blockCard(cardNumber));
    }

    public CardDTO unblockCard(String cardNumber) {
        if (cardNumber == null) {
            throw new CardNumberNotNullException();
        }

        return cardConverter.convertToDTO(cardService.unblockCard(cardNumber));
    }
}
