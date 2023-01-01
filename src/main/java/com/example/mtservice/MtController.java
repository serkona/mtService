package com.example.mtservice;

import com.example.mtservice.client.ClientService;
import com.example.mtservice.data.entity.Account;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@RestController
@RequiredArgsConstructor
public class MtController {

    @NonNull
    ClientService clientService;

    @NonNull
    BalanceServiceImpl balanceServiceIml;

    @NonNull
    ProfileScheduler profileScheduler;


    @GetMapping("/getBalance")
    public ResponseEntity<Optional<Long>> getBalance(@RequestParam(value = "id") Long id) throws RuntimeException, ExecutionException, InterruptedException {
        profileScheduler.addGetReq();

        CompletableFuture<Optional<Long>> balance = balanceServiceIml.getBalance(id);

        if (balance.get().isEmpty())
            return new ResponseEntity<>(balance.get(), HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(balance.get(), HttpStatus.OK);
    }


    @PutMapping("/changeBalance")
    public ResponseEntity<Optional<Account>> changeBalance(@RequestParam(value = "id") Long id,
                                                           @RequestParam(value = "amount") Long amount) throws RuntimeException, ExecutionException, InterruptedException {
        profileScheduler.addChangeReq();
        Optional<Account> account;

        boolean hadCreated = Objects.requireNonNull(getBalance(id).getBody()).isPresent();
        balanceServiceIml.changeBalance(id, amount);
        account = balanceServiceIml.getAccount(id).get();

        if (hadCreated)
            return new ResponseEntity<>(account, HttpStatus.OK);
        else
            return new ResponseEntity<>(account, HttpStatus.CREATED);

    }


    @ExceptionHandler({RuntimeException.class, ExecutionException.class, InterruptedException.class})
    public String handleException() {
        return "Exception";
    }

    /*

    Возможно стоит создать контроллер для создания аккаунта, чтобы им было откуда появляться.
    Но т.к. по условиям задания этого не сказано, я решил, что лучше будет
    задать начальные данные с помощью миграции.

    @PostMapping("/createAccount")
    public void createAccount(@RequestParam(value = "id") Long id,
                              @RequestParam(value = "amount") Long amount) {
        balanceServiceIml.createAccount(id, amount);
    }

     */


}
