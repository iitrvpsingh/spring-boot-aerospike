package com.demo.aerospike.springbootaerospike;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.demo")
public class SpringBootAerospikeApplication {

	private static final Logger log = LoggerFactory.getLogger(SpringBootAerospikeApplication.class);

	public static void main(String[] args) {

		SpringApplication.run(SpringBootAerospikeApplication.class, args);

	}

}
