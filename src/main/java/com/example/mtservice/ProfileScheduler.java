package com.example.mtservice;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class ProfileScheduler {
    private long getCnt = 0;
    private long changeCnt = 0;


    public void addGetReq() {
        getCnt++;
    }


    public void addChangeReq() {
        changeCnt++;
    }

    @Scheduled(timeUnit = TimeUnit.SECONDS, fixedRate = 1)
    public void logMetrics() {
        log.info(String.format("%o Get requests per second", getCnt));
        log.info(String.format("%o Change requests per second", changeCnt));
        getCnt = 0;
        changeCnt = 0;
    }

}
