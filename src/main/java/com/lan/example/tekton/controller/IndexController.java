package com.lan.example.tekton.controller;

import com.lan.example.tekton.common.ApplicationReadyEventListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.availability.ApplicationAvailability;
import org.springframework.boot.availability.AvailabilityChangeEvent;
import org.springframework.boot.availability.LivenessState;
import org.springframework.boot.availability.ReadinessState;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * @Classname IndexController
 * @Description TODO
 * @Date 2021/2/25 18:20
 * @Created by yunhorn lyp
 */
@RestController
@RequestMapping("")
public class IndexController {

    @Resource
    ApplicationAvailability applicationAvailability;

    @Resource
    ApplicationEventPublisher applicationEventPublisher;

    @Value("${spring.application.version}")
    private String version;

    @Value("${spring.cloud.client.ip-address}")
    private String ip;

    @RequestMapping(value="/startup")
    public String startup(){
        return "working "+(System.currentTimeMillis()-ApplicationReadyEventListener.starttime)/1000+"s";
    }

    @RequestMapping(value="/version")
    public String version(){
        return version;
    }

    @RequestMapping(value="/ip")
    public String ip(){
        return ip;
    }

    @RequestMapping(value="/vaip")
    public String vaip(){
        return version+"#"+ip;
    }

    @RequestMapping(value="/get")
    public String state() {
        return "livenessState : " + applicationAvailability.getLivenessState()
                + "<br>readinessState : " + applicationAvailability.getReadinessState()
                + "<br>" + new Date();
    }

    /**
     * 将存活状态改为BROKEN（会导致kubernetes杀死pod）
     * @return
     */
    @RequestMapping(value="/broken")
    public String broken(){
        AvailabilityChangeEvent.publish(applicationEventPublisher, IndexController.this, LivenessState.BROKEN);
        return "success broken, " + new Date();
    }

    /**
     * 将存活状态改为CORRECT
     * @return
     */
    @RequestMapping(value="/correct")
    public String correct(){
        AvailabilityChangeEvent.publish(applicationEventPublisher, IndexController.this, LivenessState.CORRECT);
        return "success correct, " + new Date();
    }

    /**
     * 将就绪状态改为REFUSING_TRAFFIC（导致kubernetes不再把外部请求转发到此pod）
     * @return
     */
    @RequestMapping(value="/refuse")
    public String refuse(){
        AvailabilityChangeEvent.publish(applicationEventPublisher, IndexController.this, ReadinessState.REFUSING_TRAFFIC);
        return "success refuse, " + new Date();
    }

    /**
     * 将就绪状态改为ACCEPTING_TRAFFIC（导致kubernetes会把外部请求转发到此pod）
     * @return
     */
    @RequestMapping(value="/accept")
    public String accept(){
        AvailabilityChangeEvent.publish(applicationEventPublisher, IndexController.this, ReadinessState.ACCEPTING_TRAFFIC);
        return "success accept, " + new Date();
    }

    @ResponseBody
    public String index(){
        return "index";
    }

}
