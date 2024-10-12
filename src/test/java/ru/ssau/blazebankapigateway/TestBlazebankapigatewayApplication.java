package ru.ssau.blazebankapigateway;

import org.springframework.boot.SpringApplication;

public class TestBlazebankapigatewayApplication {

	public static void main(String[] args) {
		SpringApplication.from(BlazebankapigatewayApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
