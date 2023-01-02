package com.example.mtservice;

import com.example.mtservice.data.BalanceRepo;
import com.example.mtservice.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;


@Service
@CacheConfig(cacheNames = "account")
@AllArgsConstructor
public class BalanceServiceImpl implements BalanceService {

    @NonNull
    BalanceRepo balanceRepo;


    @Async
    @CachePut(value = "account")
    public CompletableFuture<Optional<Long>> getBalance(Long id) {
        return CompletableFuture.completedFuture(balanceRepo.findById(id).map(Account::getBalance));
    }

    @Async
    @CachePut(value = "account")
    public void changeBalance(Long id, Long amount) {
        Optional<Account> account = balanceRepo.findById(id);
        account.ifPresentOrElse(value -> value.setBalance(value.getBalance() + amount), () -> balanceRepo.save(new Account(id, amount)));
        account.ifPresent(value -> balanceRepo.save(value));
    }

    @Async
    @CachePut(value = "account")
    public CompletableFuture<Optional<Account>> getAccount(Long id) {
        return CompletableFuture.completedFuture(balanceRepo.findById(id));
    }


}
