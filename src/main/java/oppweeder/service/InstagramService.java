package oppweeder.service;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.springframework.core.io.ClassPathResource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.logging.LogEntry;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class InstagramService {

    // The following scripts are placed in src/main/resources/scripts
    private final String oppWeederPath = "scripts/oppWeeder.js";
    private final String discipleFinderPath = "scripts/discipleFinder.js"; 

    public WebDriver weedOpps(String username, WebDriver driver) {
        if (driver == null) {
            log.error("Driver is null. Ensure that you are logged in before running oppweeder.");
            return null;
        }
        
        try {
            String oppWeederScript = loadScript(oppWeederPath);

            log.info("Weeding opps for '{}'...", username);
            
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String opps = (String) jsExecutor.executeAsyncScript(oppWeederScript, username);
            
            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logs) {
                log.info("[BROWSER LOG] {}", entry.getMessage());
            }

            System.out.printf("\nThe following are %s's opps: %n", username);
            prettyPrintJSON(opps);            

        } catch (Exception e) {
            log.error("Error executing opp weeder script: ", e);
        }
        
        return driver;
    }

    public WebDriver findDisciples(String username, WebDriver driver) {
        if (driver == null) {
            log.error("Driver is null. Ensure that you are logged in before running oppweeder.");
            return null;
        }
        
        try {
            String discipleFinderScript = loadScript(discipleFinderPath);

            log.info("Finding disciples for '{}'...", username);
            
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String opps = (String) jsExecutor.executeAsyncScript(discipleFinderScript, username);
            
            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logs) {
                log.info("[BROWSER LOG] {}", entry.getMessage());
            }

            System.out.printf("\nThe following are %s's disciples: %n", username);
            prettyPrintJSON(opps);            

        } catch (Exception e) {
            log.error("Error executing disciple finder script: ", e);
        }
        
        return driver;
    }

    public String loadScript(String path) {
        log.info("Loading script at path: {}", path);

        try {
            ClassPathResource resource = new ClassPathResource(path);
            InputStream inputStream = resource.getInputStream();
            String script = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            log.info("Found script: \n\n{}\n\n", script);
            return script;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load script from: " + path, e);
        }
    }

    public void prettyPrintJSON(String json) {
        try {
            JsonArray jsonArray = JsonParser.parseString(json).getAsJsonArray();
            for (JsonElement element : jsonArray) {
                String username = element.getAsJsonObject().get("username").getAsString();
                String fullName = element.getAsJsonObject().get("full_name").getAsString();
                System.out.printf("Username: %s, Name: %s%n", username, fullName);
            }
        } catch (Exception e) {
            System.err.println("Error parsing JSON: " + e.getMessage());
        }
    }

}
