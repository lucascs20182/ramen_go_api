package br.com.redventures.ramen_go;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "br.com.redventures.ramen_go")
public class RamenGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RamenGoApplication.class, args);
	}

}
