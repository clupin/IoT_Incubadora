package com.iot.usach.incubadora.controller;

import com.iot.usach.incubadora.bot.SpringSerialPortConnector;
import com.iot.usach.incubadora.entity.Dato;
import com.iot.usach.incubadora.entity.request.AddDatoRequest;
import com.iot.usach.incubadora.repository.DatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;

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
    public List<Dato> findAll() throws IOException {
        return datoRepository.findAll();
    }

    @RequestMapping(value = "/heatLamp", method = RequestMethod.GET)
    public List<Dato> findLamp() {
        return datoRepository.findLamp();
    }

    @RequestMapping(value = "/fanState", method = RequestMethod.GET)
    public List<Dato> findFan() {
        return datoRepository.findFan();
    }

    @RequestMapping(value = "/servoAngle", method = RequestMethod.GET)
    public List<Dato> findServo() {
        return datoRepository.findServo();
    }

    @RequestMapping(value = "/temperature", method = RequestMethod.GET)
    public List<Dato> findTemp() {
        return datoRepository.findTemp();
    }



    @RequestMapping(method = RequestMethod.POST)
    public void addDato(@RequestBody AddDatoRequest addDatoRequest) {
        Dato dato = new Dato();
        Date date = new Date();
        dato.setHeat_lamp(addDatoRequest.getHeat_lamp());
        dato.setFan_state(addDatoRequest.getFan_state());
        dato.setServo_angle(addDatoRequest.getServo_angle());
        dato.setTemperature(addDatoRequest.getTemperature());
        dato.setDate(date);
        datoRepository.save(dato);
    }

}
