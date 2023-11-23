package ssf.workshop3.addressbook;

import java.io.File;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AddressbookApplication implements ApplicationRunner {

	public static void main(String[] args) {
		SpringApplication.run(AddressbookApplication.class, args);
	}

	@Override
	public void run(ApplicationArguments args) throws Exception {

		if (args.containsOption("dataDir")) {
			final String dataDir = args.getOptionValues("dataDir").get(0);
			File fileDir = new File("C://" + dataDir);

			if (!fileDir.exists()) {
				fileDir.mkdirs();
			}

		} else {			
			System.out.println("--dataDir not specified, please try again");
			System.exit(0);
		}
	}

}
