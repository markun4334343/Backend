package Webtech.Backend;  // This must match!

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "Webtech.Backend")
public class WebsiteApplication {
	public static void main(String[] args) {
		SpringApplication.run(WebsiteApplication.class, args);
	}
}