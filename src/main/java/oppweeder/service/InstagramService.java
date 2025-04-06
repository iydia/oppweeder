package oppweeder.service;

import oppweeder.config.InstagramProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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
            // Load Java Script from the resource file:
            String oppweederScript = loadScript(oppweederPath);

            // Execute script, injecting target username
            JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
            Object opps = jsExecutor.executeScript(oppweederScript, username);
            log.info("Opps: {}", opps);

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

    public String loadScript(String path) {
        try {
            ClassPathResource resource = new ClassPathResource(path);
            InputStream inputStream = resource.getInputStream();
            String script = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                    .lines()
                    .collect(Collectors.joining("\n"));
            return script;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load script from: " + path, e);
        }
    }

}
