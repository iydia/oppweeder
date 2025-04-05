package oppweeder;

import java.util.*;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oppweeder.config.ApplicationProperties;
import oppweeder.service.InstagramLoginService;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({
    ApplicationProperties.class
})
@Slf4j
public class UserInterface {

    private final InstagramLoginService instagramLoginService;

    public static void main(String[] args) {

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
            log.info("Shutting down application.");
            
        } catch (RuntimeException e) {
            log.error("Exception occured: ", e);
        }

    }

    private void oppweeder(Scanner scanner) {
        log.info("Weeding the opps...");

    }

    private void login() {
        log.info("Trying to login...");

        try {
            instagramLoginService.login();
        } catch (Exception e) {
            log.error("Could not login: ", e);
        }
    }

}
