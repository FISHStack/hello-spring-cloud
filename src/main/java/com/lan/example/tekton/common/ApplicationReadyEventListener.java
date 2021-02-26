package com.lan.example.tekton.common;

import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

@Component
@Slf4j
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${spring.application.write}")
    private boolean write;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        if (write) {
            CompletableFuture.runAsync(() -> {
                Map map = Maps.newHashMap();
                while (true) {
                    map.put(System.currentTimeMillis(),System.currentTimeMillis());
                    try {
                        TimeUnit.MILLISECONDS.sleep(RandomUtils.nextInt(0,3000));
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        CompletableFuture.runAsync(()->{
            while(true){
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("hi springboot printer");
            }
        });
        log.info("app.started");
    }

}