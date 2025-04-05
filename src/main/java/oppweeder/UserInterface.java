package oppweeder;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import oppweeder.config.ApplicationProperties;
import oppweeder.service.InstagramLoginService;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({
    ApplicationProperties.class
})
public class UserInterface {

    private final InstagramLoginService instagramLoginService;

    public static void main(String[] args) {
        SpringApplication.run(UserInterface.class, args);
    }

    @Bean
    @Profile("development")
    public void runApplication() throws InterruptedException {
        
        try {
            Scanner scanner = new Scanner(System.in);
            String operation = "";

            while (!operation.equals("0")) {

                System.out.println("""

                    Select an operation to run:

                    0. Exit
                    1. Check Profile Views
                    2. Weed opps
                    3. View disciples
                    4. View mutuals

                    Testing operations:
                    100. Login to Instagram

                    """);

                operation = scanner.nextLine().trim();

                switch (operation) {
                    case "0" -> System.out.println("Exiting...");
                    //case "1" -> checkProfileViews(scanner);
                    case "2" -> oppweeder(scanner);
                    //case "3" -> disciples(scanner);
                    //case "4" -> mutuals(scanner);
                    case "100" -> login();
                    default -> System.out.println("Not a valid option.");
                }

            }
            System.out.println("Shutting down application.");
            
        } catch (RuntimeException e) {
            System.out.printf("Exception occured: %s%n", e);
            System.out.println("Restarting application...");
            runApplication();
        }

    }

    private void oppweeder(Scanner scanner) {
        System.out.println("Weeding the opps...");

    }

    private void login() {
        System.out.println("Trying to login...");

        try {
            instagramLoginService.login();
        } catch (Exception e) {
            System.out.printf("Could not login: %s%n", e);
        }
    }

}
