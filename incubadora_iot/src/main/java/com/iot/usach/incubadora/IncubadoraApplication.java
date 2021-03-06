package com.iot.usach.incubadora;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan("com")
public class IncubadoraApplication {

	public static void main(String[] args) {
		SpringApplication.run(IncubadoraApplication.class, args);
	}
}
