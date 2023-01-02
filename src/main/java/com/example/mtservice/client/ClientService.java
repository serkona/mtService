package com.example.mtservice.client;


import com.example.mtservice.data.entity.Account;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


@Service
@RequiredArgsConstructor
public class ClientService {

    public final List<Long> readIdList = List.of(1L, 2L, 3L, 4L, 5L);
    public final List<Long> writeIdList = List.of(1L, 2L, 3L, 4L, 5L);
    public final long readQuota = 10;
    public final long writeQuota = 20;


    private final WebClient webClient = WebClient.create("http://localhost:8080");

    @Async
    public Long getRequest(long id) {
        return webClient
                .get()
                .uri(String.join("", "/getBalance?id=", String.valueOf(id)))
                .retrieve()
                .bodyToMono(Long.class)
                .retryWhen(Retry.fixedDelay(1, Duration.ofMillis(100)))
                .block();

    }

    @Async
    public Account changeRequest(long id, long amount) {
        return webClient
                .put()
                .uri(String.format("/changeBalance?id=%o&amount=%o", id, amount))
                .retrieve()
                .bodyToMono(Account.class)
                .retryWhen(Retry.fixedDelay(1, Duration.ofMillis(100)))
                .block();

    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 15)
    public void sendRequests() {
        long threadCount = 5;

        for (int i = 0; i < threadCount; i++) {

            new Thread(() -> {

                while (true) {
                    // вероятность вызова метода getBalance
                    double readProbability = (double) readQuota / (double) (readQuota + writeQuota);

                    if (ThreadLocalRandom.current().nextDouble() < readProbability) {
                        getRequest(ThreadLocalRandom.current().nextLong(readIdList.size()) % readIdList.size() + 1);
                    } else {
                        changeRequest(ThreadLocalRandom.current().nextLong(writeIdList.size()) % writeIdList.size() + 1, 1L);
                    }
                }

            }).start();

        }


    }

}
