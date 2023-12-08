package com.digitalfemsa.tit.lending.job.template;

import com.digitalfemsa.tit.lending.job.template.command.ExampleCommand;
import com.digitalfemsa.tit.lending.job.template.configuration.JobSchedulingProperties;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * Starter application.
 */
@SpringBootApplication
@EnableConfigurationProperties(JobSchedulingProperties.class)
@AllArgsConstructor
public class TemplateApplication implements CommandLineRunner {

	/**
	 * Command.
	 */
	private ExampleCommand exampleCommand;

	/**
	 * main method.
	 * @param args string array.
	 */
	public static void main(String[] args) {
		SpringApplication.run(TemplateApplication.class, args);
	}

	/**
	 * run method.
	 */
	@Override
	public void run(final String[] args) throws Exception {
		exampleCommand.execute();
	}

}
