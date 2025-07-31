package com.moger.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"com.moger.springboot.util","com.moger.springboot.mycoolapp"})
public class MycoolappApplication {

	public static void main(String[] args) {

		SpringApplication.run(MycoolappApplication.class, args);
	}

}
