package com.bustravelagent.masterdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MasterdataApplication {

	public static void main(String[] args) {

		SpringApplication application = new SpringApplication(MasterdataApplication.class);

		// Set the active profile programmatically
		application.setAdditionalProfiles("dev");

		application.run(args);
	}

}
