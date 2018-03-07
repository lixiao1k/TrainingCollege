package com.xrom.ssh.service;

import com.xrom.ssh.entity.Account;
import com.xrom.ssh.exceptions.NotValidatedUserException;

public interface AccountService {
    Long createAccount(Long userId);
    Account getAccount(Long userId);
    void deleteAccount(Long userId);
    void updateAccount(Long userId, int amount);
    void flush();
    void insertCard(String cardNumber, Long userId);
    int getConsumption(Long userId) throws NotValidatedUserException;
}
