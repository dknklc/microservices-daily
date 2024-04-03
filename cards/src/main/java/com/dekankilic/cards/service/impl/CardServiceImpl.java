package com.dekankilic.cards.service.impl;

import com.dekankilic.cards.exception.CardAlreadyExistsException;
import com.dekankilic.cards.constants.CardConstants;
import com.dekankilic.cards.dto.CardDto;
import com.dekankilic.cards.exception.ResourceNotFoundException;
import com.dekankilic.cards.mapper.CardMapper;
import com.dekankilic.cards.model.Card;
import com.dekankilic.cards.repository.CardRepository;
import com.dekankilic.cards.service.ICardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements ICardService {
    private final CardRepository cardRepository;


    @Override
    public void createCard(String mobileNumber) {
        Optional<Card> optionalCard = cardRepository.findByMobileNumber(mobileNumber);
        if(optionalCard.isPresent()){
            throw new CardAlreadyExistsException("Card already registered with given mobileNumber " + mobileNumber);
        }
        cardRepository.save(createNewCard(mobileNumber));
    }

    private Card createNewCard(String mobileNumber) {
        long randomCardNumber = 100000000000L + new Random().nextInt(900000000);
        return Card.builder()
                .mobileNumber(mobileNumber)
                .cardNumber(Long.toString(randomCardNumber))
                .cardType(CardConstants.CREDIT_CARD)
                .totalLimit(CardConstants.NEW_CARD_LIMIT)
                .amountUsed(0)
                .availableAmount(CardConstants.NEW_CARD_LIMIT)
                .build();
    }

    @Override
    public CardDto fetchCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "mobileNumber", mobileNumber));
        return CardMapper.mapToCardDto(card);
    }

    @Override
    public boolean updateCard(CardDto cardDto) {
        Card card = cardRepository.findByCardNumber(cardDto.getCardNumber()).orElseThrow(() -> new ResourceNotFoundException("Card", "CardNumber", cardDto.getCardNumber()));
        Card willBeUpdated = CardMapper.mapToCard(cardDto);
        willBeUpdated.setCardId(card.getCardId());
        cardRepository.save(willBeUpdated);
        return true;
    }

    @Override
    public boolean deleteCard(String mobileNumber) {
        Card card = cardRepository.findByMobileNumber(mobileNumber).orElseThrow(() -> new ResourceNotFoundException("Card", "Mobilenumber", mobileNumber));
        cardRepository.deleteById(card.getCardId());
        return true;
    }
}
