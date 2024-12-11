package hcmut.spss.be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@ComponentScan(basePackages = {"hcmut"})
public class BeApplication {
	public static void main(String[] args) {
		SpringApplication.run(BeApplication.class, args);
	}
}
