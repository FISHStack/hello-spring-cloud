package com.lan.example.tekton.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

/**
 * @Classname ApplicationClosedEventListener
 * @Description 监听spring容器关闭时机，进行优雅关闭
 * @Date 2020/9/7 15:59
 * @Created by yunhorn lyp
 */
@Slf4j
@Component
public class ApplicationClosedEventListener implements ApplicationListener<ContextClosedEvent> {

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        log.info("******app.closing******");
        log.info("******app.closed******");
        System.exit(-1);
    }
}
