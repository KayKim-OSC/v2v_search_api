package com.samsung.v2v.v2v_search_api;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@Slf4j
public class V2vSearchApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(V2vSearchApiApplication.class, args);
	}

}
