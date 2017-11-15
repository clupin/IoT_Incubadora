package com.iot.usach.incubadora.bot;

import com.adventurer.springserialport.connector.AbstractSpringSerialPortConnector;
import gnu.io.NoSuchPortException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.TooManyListenersException;

@Component
public class SpringSerialPortConnector extends AbstractSpringSerialPortConnector {

    @Autowired
    private IncubadoraBot incubadoraBot;

    @PostConstruct
    public void init() throws NoSuchPortException, TooManyListenersException {
        System.out.println("Conectando con Arduino...");
        //super.connect();
    }

    @Override
    public void processData(String s) {
        System.out.println(s);
        incubadoraBot.enviarMensaje(s);
    }

}
