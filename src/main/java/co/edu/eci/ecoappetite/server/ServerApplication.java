package co.edu.eci.ecoappetite.server;

import com.ngrok.Session;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;

@SpringBootApplication
@ComponentScan(basePackages = "co.edu.eci.ecoappetite.server")
public class ServerApplication {

	public static void main(String[] args) {
		// Cargar configuración de MongoDB desde variables de entorno
		System.setProperty("MONGO_USER", ConfigLoader.get("MONGO_USER"));
		System.setProperty("MONGO_PASSWORD", ConfigLoader.get("MONGO_PASSWORD"));
		System.setProperty("MONGO_CLUSTER", ConfigLoader.get("MONGO_CLUSTER"));
		System.setProperty("MONGO_DB", ConfigLoader.get("MONGO_DB"));

		// Ejecutar ngrok en un hilo separado para no bloquear el inicio del servidor
		//new Thread(ServerApplication::startNgrok).start();

		// Iniciar la aplicación Spring Boot
		SpringApplication.run(ServerApplication.class, args);
	}

	private static void startNgrok() {
		try {
			// Crear una sesión con el token de autenticación de ngrok
			final var session = Session.withAuthtokenFromEnv().metadata("EcoAppetite Session").connect();

			// Crear un listener HTTP en ngrok
			final var listener = session.httpEndpoint().metadata("EcoAppetite Listener").listen();

			// Imprimir la URL pública de ngrok
			System.out.println("ngrok URL: " + listener.getUrl());
		} catch (IOException e) {
			System.err.println("Error al iniciar ngrok: " + e.getMessage());
		}
	}
}
