package co.edu.eci.ecoappetite.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "co.edu.eci.ecoappetite.server")
public class ServerApplication {

	public static void main(String[] args) {

		System.setProperty("MONGO_USER", ConfigLoader.get("MONGO_USER"));
		System.setProperty("MONGO_PASSWORD", ConfigLoader.get("MONGO_PASSWORD"));
		System.setProperty("MONGO_CLUSTER", ConfigLoader.get("MONGO_CLUSTER"));
		System.setProperty("MONGO_DB", ConfigLoader.get("MONGO_DB"));

		SpringApplication.run(ServerApplication.class, args);
	}

}
