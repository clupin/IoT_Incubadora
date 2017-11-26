package com.iot.usach.incubadora.entity.request;

import java.util.Date;

public class AddDatoRequest {
    private int heat_lamp;
    private boolean fan_state;
    private int servo_angle;
    private float temperature;
    private Date date;

    public int getHeat_lamp() {
        return heat_lamp;
    }

    public void setHeat_lamp(int heat_lamp) {
        this.heat_lamp = heat_lamp;
    }

    public boolean getFan_state() {
        return fan_state;
    }

    public void setFan_state(boolean fan_state) {
        this.fan_state = fan_state;
    }

    public int getServo_angle() {
        return servo_angle;
    }

    public void setServo_angle(int servo_angle) {
        this.servo_angle = servo_angle;
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public void setDate(Date date){
        this.date = date;
    }

    public Date getDate(){
        return date;
    }
}
