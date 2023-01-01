package com.example.mtservice;

import com.example.mtservice.data.BalanceRepo;
import com.example.mtservice.data.entity.Account;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
@CacheConfig(cacheNames = "accounts")
@RequiredArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    @NonNull
    BalanceRepo balanceRepo;

    @Async
    @CachePut(value = "accounts")
    public CompletableFuture<Optional<Long>> getBalance(Long id) {
        return CompletableFuture.completedFuture(balanceRepo.findById(id).map(Account::getBalance));
    }

    @Async
    @CachePut(value = "accounts")
    public void changeBalance(Long id, Long amount) {
        Optional<Account> account = balanceRepo.findById(id);
        account.ifPresentOrElse(value -> value.setBalance(value.getBalance() + amount), () -> balanceRepo.save(new Account(id, amount)));
        account.ifPresent(value -> balanceRepo.save(value));
    }

    @Async
    @CachePut(value = "accounts")
    public CompletableFuture<Optional<Account>> getAccount(Long id) {
        return CompletableFuture.completedFuture(balanceRepo.findById(id));
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
