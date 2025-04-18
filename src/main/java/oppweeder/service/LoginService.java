package oppweeder.service;

import oppweeder.config.InstagramProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

import org.openqa.selenium.TimeoutException;

import java.time.Duration;

@Service
@Log4j2
public class LoginService {

    private final WebDriverService webDriverService;
    private final InstagramProperties instagramProperties;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    private final String loginAppend = "accounts/login/";

    private static final By USERNAME_FIELD = By.name("username");
    private static final By PASSWORD_FIELD = By.name("password");
    private static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");
    private static final By MAIN_CLASS = By.cssSelector("main.xvbhtw8.x78zum5.xdt5ytf.x1iyjqo2.xl56j7k");

    @Autowired
    public LoginService(WebDriverService webDriverService, InstagramProperties instagramProperties) {
        this.webDriverService = webDriverService;
        this.instagramProperties = instagramProperties;

        // Get a headless driver instance
        driver = webDriverService.getHeadlessDriver();
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebDriver login() {        
        // Navigate to the Instagram login page
        String loginUrl = instagramProperties.getUrl() + loginAppend;
        driver.get(loginUrl);
        log.info("Logging in at URL: {}", loginUrl);

        try { // Enter username field
            WebElement usernameField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
            log.info("Found username field.");
            usernameField.sendKeys(instagramProperties.getCredential().getUsername());
            log.info("Entered username: {}", instagramProperties.getCredential().getUsername());
        } catch (TimeoutException e) {
            throw new RuntimeException("Username field did not appear within the expected time.", e);
        }

        try { // Enter password field
            WebElement passwordField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
            log.info("Found password field.");
            passwordField.sendKeys(instagramProperties.getCredential().getPassword());
            log.info("Entered password.");
        } catch (TimeoutException e) {
            throw new RuntimeException("Password field did not appear within the expected time.", e);
        }

        try { // Click the login button
            WebElement loginButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
            log.info("Found login button.");
            loginButton.click();
            log.info("Clicked login button.");
        } catch (TimeoutException e) {
            throw new RuntimeException("Login button was not clickable within the expected time.", e);
        }

        try { // Successful login indicator
            webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(MAIN_CLASS));
            log.info("Login successful.");
        } catch (TimeoutException e) {
            throw new RuntimeException("Main class was not detected within the expected time.", e);
        }

        return driver;
    }

}
