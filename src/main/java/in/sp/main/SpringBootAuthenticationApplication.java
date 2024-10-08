package in.sp.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringBootAuthenticationApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootAuthenticationApplication.class, args);
		System.out.println("Server started..");
	}

}
