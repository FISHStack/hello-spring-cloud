package com.lan.example.tekton.common;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.AvailabilityState;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Classname AvailabilityListener
 * @Description TODO
 * @Date 2020/11/25 14:47
 * @Created by yunhorn lyp
 */
@Component
@Slf4j
public class AvailabilityListener {

    @EventListener
    public void onStateChange(AvailabilityChangeEvent<? extends AvailabilityState> event) {
        log.info(event.getState().getClass().getSimpleName() + " : " + event.getState());
    }
}
