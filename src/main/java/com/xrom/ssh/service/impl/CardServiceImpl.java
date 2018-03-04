package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Card;
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
    public void deleteCard(Long userId) {
        cardRepository.delete(userId);
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
}
