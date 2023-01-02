package com.example.mtservice;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface BalanceService {
    /**
     * Получение баланса
     *
     * @param id идентификатор банковского счёта
     * @return сумма денег на банковском счёте
     */
    CompletableFuture<Optional<Long>> getBalance(Long id);

    /**
     * Изменение баланса на определённое значение
     *
     * @param id     идентификатор банковского счёта
     * @param amount сумма денег, которую нужно добавить к банковскому счёту
     */

    void changeBalance(Long id, Long amount);
}
