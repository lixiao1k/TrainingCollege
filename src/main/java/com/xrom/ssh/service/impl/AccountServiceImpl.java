package com.xrom.ssh.service.impl;

import com.xrom.ssh.entity.Account;
import com.xrom.ssh.entity.Card;
import com.xrom.ssh.entity.Student;
import com.xrom.ssh.exceptions.NotValidatedUserException;
import com.xrom.ssh.repository.AccountRepository;
import com.xrom.ssh.repository.CardRepository;
import com.xrom.ssh.service.AccountService;
import com.xrom.ssh.service.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService {
    @Autowired(required = true)
    private AccountRepository accountRepository;

    @Autowired(required = true)
    private CardService cardService;

    @Override
    public Long createAccount(Long userId) {
        System.out.println("here");
        Account account = new Account();
        account.setUserId(userId);
        Long id = accountRepository.save(account);
        flush();
        return id;
    }

    @Override
    public Account getAccount(Long userId) {
        return accountRepository.get(userId);
    }

    @Override
    public void deleteAccount(Long userId) {
        accountRepository.delete(userId);
        flush();
    }

    @Override
    public void updateAccount(Long userId, int amount) {
        Account account = getAccount(userId);
        account.setTotalConsumption(account.getTotalConsumption() + amount);
        accountRepository.update(account);
        flush();
    }

    @Override
    public void updateBpBalance(Long userId, int amount) {
        Account account = getAccount(userId);
        account.setBpBalance(account.getBpBalance() + amount);
        accountRepository.update(account);
        flush();
    }

    @Override
    public void updateTotalConsumption(Long userId, int amount) {
        Account account = getAccount(userId);
        account.setTotalConsumption(account.getTotalConsumption() + amount);
        accountRepository.update(account);
        flush();
    }

    @Override
    public void updateCardNumber(Long userId, String cardNumber) {
        Account account = getAccount(userId);
        account.setCardNumber(cardNumber);
        accountRepository.update(account);
        flush();
    }

    @Override
    public void flush() {
        accountRepository.flush();
    }

    @Override
    public void insertCard(String cardNumber, Long userId) {
        Card card = new Card();
        card.setUserId(userId);
        card.setCardNumber(cardNumber);
        cardService.saveCard(card);
        cardService.flush();
        Account account = accountRepository.get(userId);
        account.setCardNumber(card.getCardNumber());
        accountRepository.update(account);
        flush();
    }

    @Override
    public int getConsumption(Long userId) throws NotValidatedUserException {
        Account account = getAccount(userId);
        if(account == null){
            throw new NotValidatedUserException();
        }else {
            return account.getTotalConsumption();
        }
    }
}
