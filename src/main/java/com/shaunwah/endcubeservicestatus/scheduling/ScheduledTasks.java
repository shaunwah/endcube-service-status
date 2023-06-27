package com.shaunwah.endcubeservicestatus.scheduling;

import com.shaunwah.endcubeservicestatus.service.VelorenAuthServerService;
import com.shaunwah.endcubeservicestatus.service.VelorenGameServerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    @Autowired
    VelorenGameServerService velorenGameServerService;
    @Autowired
    VelorenAuthServerService velorenAuthServerService;

    private static final Logger log = LoggerFactory.getLogger(ScheduledTasks.class);
    @Scheduled(fixedDelay = 300000)
    public void storeDataInRedis() {
        velorenGameServerService.storeObjectInRedis(velorenGameServerService.ping());
        log.info("Saved velorenGameServerService to Redis.");
        velorenAuthServerService.storeObjectInRedis(velorenAuthServerService.ping());
        log.info("Saved velorenAuthServerService to Redis.");
    }
}
