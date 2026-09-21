package dev.hye.payledger;

import org.springframework.boot.SpringApplication;

public class TestPayLedgerApplication {

	public static void main(String[] args) {
		SpringApplication.from(PayLedgerApplication::main).with(TestcontainersConfiguration.class).run(args);
	}

}
