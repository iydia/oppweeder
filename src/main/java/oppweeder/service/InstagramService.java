package oppweeder.service;

import oppweeder.config.InstagramProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.springframework.core.io.ClassPathResource;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.logging.LogEntries;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.TimeoutException;

import java.time.Duration;

@Service
@Slf4j
public class InstagramService {

    private final WebDriverService webDriverService;
    private final InstagramProperties instagramProperties;

    private final String oppweederPath = "scripts/oppweeder.js"; // Placed in src/main/resources/scripts

    @Autowired
    public InstagramService(WebDriverService webDriverService, InstagramProperties instagramProperties) {
        this.webDriverService = webDriverService;
        this.instagramProperties = instagramProperties;
    }

    public WebDriver weedOpps(String username, WebDriver driver) {
        // Assumes driver session is logged in prior to running the script
        if (driver == null) {
            log.error("Driver is null. Ensure that you are logged in before running oppweeder.");
            return null;
        }
        
        try {
            // Load script from the resource file, casting the driver to execute it
            String oppweederScript = loadScript(oppweederPath);
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            // Inject target username and execute the script
            log.info("Injecting username '{}' into script...", username);
            Object opps = jsExecutor.executeScript(oppweederScript, username);

            log.info("Opps: {}", opps);

            // Retrieve and log browser console logs after executing the script
            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logs) {
                log.info("[BROWSER LOG] {}", entry.getMessage());
            }

        } catch (Exception e) {
            log.error("Error executing oppweeder script: ", e);
        }

        return driver;
    }

    public WebDriver weedOppsTest(String username, WebDriver driver) {
        // Assumes driver session is logged in prior to running the script
        if (driver == null) {
            log.error("Driver is null. Ensure that you are logged in before running oppweeder.");
            return null;
        }
        
        try {
            String oppweederScript = loadScript(oppweederPath);
            String personalizedScript = String.format(oppweederScript, username);
            log.info("Running script: \n\n{}\n\n", personalizedScript);

            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;

            log.info("Injecting username '{}' into script...", username);
            Object opps = jsExecutor.executeScript(personalizedScript);

            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logs) {
                log.info("[BROWSER LOG] {}", entry.getMessage());
            }

            log.info("OPPS: {}", opps);

        } catch (Exception e) {
            log.error("Error executing oppweeder script: ", e);
        }

        return driver;
    }

    public void testJSScript(String username) {
        String testOppweederScriptPath = "scripts/testOppweederScript.js";

        try {
            String oppweederScript = loadScript(testOppweederScriptPath);
            String injectedScript = String.format(oppweederScript, username);
            System.out.printf("Injected JS Script: %s%n%n", injectedScript);
        } catch (Exception e) {
            log.error("Error testing oppweeder script: ", e);
        }
    }

    public WebDriver weedOppsTest2(String username, WebDriver driver) {
        if (driver == null) {
            log.error("Driver is null. Ensure that you are logged in before running oppweeder.");
            return null;
        }
        
        try {
            String oppweederPath2 = "scripts/oppweeder2.js";
            String oppweederScript = loadScript(oppweederPath2);

            log.info("Weeding opps for '{}'...", username);
            
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            String oppsJson = (String) jsExecutor.executeAsyncScript(oppweederScript, username);
            
            LogEntries logs = driver.manage().logs().get(LogType.BROWSER);
            for (LogEntry entry : logs) {
                log.info("[BROWSER LOG] {}", entry.getMessage());
            }

            System.out.printf("\nThe following are %s's opps: %n", username);
            prettyPrintJSON(oppsJson);            

        } catch (Exception e) {
            log.error("Error executing oppweeder script: ", e);
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
