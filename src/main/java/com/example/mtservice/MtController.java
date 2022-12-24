package com.example.mtservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RestController
public class MtController {

    @Autowired
    BalanceServiceImpl balanceServiceIml;

    @GetMapping("/getBalance")
    public CompletableFuture<Optional<Long>> getBalance(@RequestParam(value = "id") Long id) {
        return balanceServiceIml.getBalance(id);
    }


    @PutMapping("/changeBalance")
    public void changeBalance(@RequestParam(value = "id") Long id,
                              @RequestParam(value = "amount") Long amount) {
        balanceServiceIml.changeBalance(id, amount);
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
