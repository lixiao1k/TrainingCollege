package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Card;
import com.xrom.ssh.exceptions.CreateSameCardException;
import com.xrom.ssh.repository.CardRepository;
import com.xrom.ssh.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardServiceImpl implements CardService {
    @Autowired(required = true)
    private CardRepository cardRepository;

    @Override
    public Long saveCard(Card card) {
        return cardRepository.save(card);
    }

    @Override
    public Card getCard(Long userId) {
        return cardRepository.get(userId);
    }

    @Override
    public Card getCard(String cardNumber) {
        return cardRepository.getCard(cardNumber);
    }

    @Override
    public void deleteCard(Long userId) {
        cardRepository.delete(userId);
        flush();
    }

    @Override
    public void flush() {
        cardRepository.flush();
    }

    @Override
    public void update(Long userId, int amount) {
        Card card= cardRepository.get(userId);
        card.setBalance(card.getBalance() + amount);
        cardRepository.update(card);
        cardRepository.flush();
    }

    @Override
    public void createCard(Long userId, String cardNumber) throws CreateSameCardException {
        Card card = getCard(cardNumber);
        if(card != null){
            throw new CreateSameCardException();
        }else {
            card = new Card();
        }
        card.setUserId(userId);
        card.setCardNumber(cardNumber);
        saveCard(card);
        flush();
    }
}
