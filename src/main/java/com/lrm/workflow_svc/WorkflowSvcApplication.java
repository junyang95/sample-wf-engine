package com.lrm.workflow_svc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class WorkflowSvcApplication {

	public static void main(String[] args) {
		SpringApplication.run(WorkflowSvcApplication.class, args);
	}

}
