package oppweeder;

import java.util.*;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;

import org.openqa.selenium.WebDriver;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import oppweeder.config.ApplicationProperties;
import oppweeder.service.InstagramService;
import oppweeder.service.LoginService;

@SpringBootApplication
@RequiredArgsConstructor
@EnableConfigurationProperties({
    ApplicationProperties.class
})
@Slf4j
public class UserInterface {

    private final LoginService loginService;
    private final InstagramService instagramService;

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
                    1. Log in to Instagram
                    2. Weed opps
                    3. View disciples
                    4. View mutuals

                    [Testing functions]
                    100. Test oppweeder script

                    """);

                operation = scanner.nextLine().trim();

                switch (operation) {
                    case "0" -> System.out.println("Exiting...");
                    case "1" -> login();
                    case "2" -> oppweeder(scanner);
                    case "3" -> disciples(scanner);
                    //case "4" -> mutuals(scanner);
                    default -> System.out.println("Not a valid option.");
                }

            }
            log.info("Shutting down application.");
            
        } catch (RuntimeException e) {
            log.error("Exception occured: ", e);
            log.warn("Restarting application...");
            runApplication();
        }

    }

    private void login() {
        log.info("Trying to login...");

        try {
            loginService.login();
        } catch (Exception e) {
            log.error("Could not login: ", e);
        }
    }

    private void oppweeder(Scanner scanner) {
        log.info("Automating login...");

        WebDriver driver = loginService.login();

        System.out.println("Please type \"allow pasting\" into the console. \nPress enter when you are ready.");
        String nextStep = scanner.nextLine().trim();

        String targetUser = "";
        while (!targetUser.equalsIgnoreCase("quit") && !targetUser.equals("0")) {
            System.out.println("Which user do you wish to weed opps for? (Type \"quit\" to abort)");
            targetUser = scanner.nextLine().trim();
    
            try {
                log.info("Weeding the opps...");
                driver = instagramService.weedOpps(targetUser, driver);
            } catch (Exception e) {
                log.error("Exception occurred while weeding the opps: ", e);
            }
        }
    }

    private void disciples(Scanner scanner) {
        log.info("Automating login...");

        WebDriver driver = loginService.login();

        System.out.println("Please type \"allow pasting\" into the console. \nPress enter when you are ready.");
        String nextStep = scanner.nextLine().trim();

        String targetUser = "";
        while (!targetUser.equalsIgnoreCase("quit") && !targetUser.equals("0")) {
            System.out.println("Which user do you wish to find disciples for? (Type \"quit\" to abort)");
            targetUser = scanner.nextLine().trim();
    
            try {
                log.info("Finding the disciples...");
                driver = instagramService.weedOpps(targetUser, driver);
            } catch (Exception e) {
                log.error("Exception occurred while finding the disciples: ", e);
            }
        }
    }

}
