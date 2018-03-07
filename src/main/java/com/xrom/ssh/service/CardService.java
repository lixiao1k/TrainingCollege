package com.xrom.ssh.service;

import com.xrom.ssh.entity.Card;
import com.xrom.ssh.exceptions.CreateSameCardException;

public interface CardService {
    Long saveCard(Card card);
    Card getCard(Long userId);
    Card getCard(String cardNumber);
    void deleteCard(Long userId);
    void flush();
    void update(Long userId, int amount);
    void createCard(Long userId, String cardNumber) throws CreateSameCardException;
}
