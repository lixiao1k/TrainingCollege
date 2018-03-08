package com.xrom.ssh.service;

import com.xrom.ssh.entity.Card;
import com.xrom.ssh.exceptions.CreateSameCardException;
import com.xrom.ssh.exceptions.NoCardException;

public interface CardService {
    Long saveCard(Card card);
    Card getCard(Long userId);
    Card getCard(String cardNumber);
    void deleteCard(Long userId);
    void flush();
    void update(Long userId, int amount) throws NoCardException;
    void createCard(Long userId, String cardNumber) throws CreateSameCardException;
}
