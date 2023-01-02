package com.example.mtservice.data;


import com.example.mtservice.data.entity.Account;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader {

    @NonNull
    BalanceRepo balanceRepo;

    @EventListener(ApplicationReadyEvent.class)
    public void loadData() {
        balanceRepo.deleteAll();
        balanceRepo.save(new Account(1L, 0L));
        balanceRepo.save(new Account(2L, 100L));
        balanceRepo.save(new Account(3L, 5000L));
        balanceRepo.save(new Account(4L, 50010L));
        balanceRepo.save(new Account(5L, 60051L));
    }

}