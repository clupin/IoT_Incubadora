package com.iot.usach.incubadora.repository;

import com.iot.usach.incubadora.entity.Dato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DatoRepository extends JpaRepository<Dato, Long> {
}
