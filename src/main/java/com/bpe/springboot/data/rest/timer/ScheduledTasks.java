package com.bpe.springboot.data.rest.timer;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
    
    private final Logger logger = Logger.getLogger(ScheduledTasks.class.getName());

    @Scheduled(fixedRate = 5000)
    public void reportCurrentTime() {       
        logger.info("The time is now " + dateFormat.format(new Date()));
    }
}
