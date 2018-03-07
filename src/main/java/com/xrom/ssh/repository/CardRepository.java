package com.xrom.ssh.repository;

import com.xrom.ssh.entity.Card;

public interface CardRepository extends DomainRepository<Card,Long> {
    Card getCard(String cardNumber);
}
