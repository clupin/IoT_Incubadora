package com.iot.usach.incubadora.repository;

import com.iot.usach.incubadora.entity.Dato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DatoRepository extends JpaRepository<Dato, Long> {

    @Query("SELECT d.heat_lamp FROM Dato d")
    List<Dato> findLamp();

    @Query("SELECT d.fan_state FROM Dato d")
    List<Dato> findFan();

    @Query("SELECT d.servo_angle FROM Dato d")
    List<Dato> findServo();

    @Query("SELECT d.temperature FROM Dato d")
    List<Dato> findTemp();
}
