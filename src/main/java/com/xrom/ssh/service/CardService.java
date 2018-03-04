package com.xrom.ssh.service;

import com.xrom.ssh.entity.Card;

public interface CardService {
    Long saveCard(Card card);
    Card getCard(Long userId);
    void deleteCard(Long userId);
    void flush();
}
