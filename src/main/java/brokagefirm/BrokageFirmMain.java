package brokagefirm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan("brokagefirm.model")
public class BrokageFirmMain {

	public static void main(String[] args) {
		 SpringApplication.run(BrokageFirmMain.class, args); // Entry point of the application
	}

}
