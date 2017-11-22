package com.iot.usach.incubadora.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledTasks {
    private static final Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);

    @Autowired
    private SpringSerialPortConnector springSerialPortConnector;

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    //@Scheduled(fixedRate = schedTime)
    @Scheduled(fixedRateString = "${incubadora.schedTime}")
    public void reportCurrentTime() {
        try {
            springSerialPortConnector.sendMessage("00\n");
            logger.info("Enviado readAll a los {}", dateFormat.format(new Date()));
        } catch (IOException e) {
            logger.info("Falló envio a los {}", dateFormat.format(new Date()));
        }
        //arreglar envio a telegram
    }
}
