package com.example.mtservice;

import com.example.mtservice.data.BalanceRepo;
import com.example.mtservice.data.entity.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
@CacheConfig(cacheNames="accounts")
public class BalanceServiceImpl implements BalanceService {

    @Autowired
    BalanceRepo balanceRepo;

    @Async
    @CachePut(value="accounts")
    public CompletableFuture<Optional<Long>> getBalance(Long id) {
        return CompletableFuture.completedFuture(balanceRepo.findById(id).map(Account::getBalance));
    }

    @Async
    @CachePut(value="accounts")
    public void changeBalance(Long id, Long amount) {
        Optional<Account> account = balanceRepo.findById(id);
        account.ifPresent(value -> value.setBalance(value.getBalance() + amount));
        account.ifPresent(value -> balanceRepo.save(value));
    }

    /*

    Возможно стоит создать сервис для создания аккаунта, чтобы им было откуда появляться.
    Но т.к. по условиям задания этого не сказано, я решил, что лучше будет
    задать начальные данные с помощью миграции.

    public void createAccount(Long id, Long amount){
        Account account = new Account();
        account.setId(id);
        account.setBalance(amount);
        balanceRepo.save(account);
    }


     */





}
