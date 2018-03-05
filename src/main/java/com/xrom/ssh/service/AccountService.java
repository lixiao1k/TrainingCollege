package com.xrom.ssh.service;

import com.xrom.ssh.entity.Account;

public interface AccountService {
    Long createAccount(Account account);
    Account getAccount(Long userId);
    void deleteAccount(Long userId);
    void updateAccount(Long userId, int amount);
    void flush();
}
