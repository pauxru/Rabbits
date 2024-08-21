package one.pawadtech.Rabbits;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RabbitsApplication {

	private static final Logger logger = LoggerFactory.getLogger(RabbitsApplication.class);

	public static void main(String[] args) {
		logger.info("Starting the RabbitsApplication...");
		SpringApplication.run(RabbitsApplication.class, args);
		logger.info("RabbitsApplication started successfully.");
	}
}
