package com.iot.usach.incubadora.controller;

import com.iot.usach.incubadora.bot.SpringSerialPortConnector;
import com.iot.usach.incubadora.entity.Dato;
import com.iot.usach.incubadora.entity.request.AddDatoRequest;
import com.iot.usach.incubadora.repository.DatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/dato")
public class DatoController {

    @Autowired
    private DatoRepository datoRepository;

    @Autowired
    private SpringSerialPortConnector springSerialPortConnector;

    public DatoController(DatoRepository datoRepository){
        this.datoRepository = datoRepository;
    }

    @RequestMapping("/{id}")
    public Dato findDato(@PathVariable("id") long id) {
        return datoRepository.findOne(id);
    }

    @RequestMapping(method = RequestMethod.GET)
    public int getActualHeatLamp() throws IOException {
        //falta exception

        //considerado inicialmente con hex
        try {
            springSerialPortConnector.sendMessage("00");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    @RequestMapping(method = RequestMethod.POST)
    public void addDato(@RequestBody AddDatoRequest addDatoRequest) {
        Dato dato = new Dato();
        dato.setHeat_lamp(addDatoRequest.getHeat_lamp());
        dato.setFan_state(addDatoRequest.getFan_state());
        dato.setHumidity(addDatoRequest.getHumidity());
        dato.setServo_angle(addDatoRequest.getServo_angle());
        dato.setTemperature(addDatoRequest.getTemperature());
        datoRepository.save(dato);
    }

}
