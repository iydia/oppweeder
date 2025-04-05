package oppweeder.service;

import oppweeder.config.InstagramProperties;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import io.netty.handler.timeout.TimeoutException;
import lombok.extern.slf4j.Slf4j;

import java.time.Duration;

@Service
@Slf4j
public class InstagramLoginService {

    private final WebDriverService webDriverService;
    private final InstagramProperties instagramProperties;

    private WebDriver driver;
    private WebDriverWait webDriverWait;

    private final String loginAppend = "accounts/login/";

    public static final By USERNAME_FIELD = By.name("username");
    public static final By PASSWORD_FIELD = By.name("password");
    public static final By LOGIN_BUTTON = By.cssSelector("button[type='submit']");

    @Autowired
    public InstagramLoginService(WebDriverService webDriverService, InstagramProperties instagramProperties) {
        this.webDriverService = webDriverService;
        this.instagramProperties = instagramProperties;

        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public WebDriver login() {
        // Get a headless driver instance
        driver = webDriverService.getHeadlessDriver();
        
        // Navigate to the Instagram login page
        String loginUrl = instagramProperties.getUrl() + loginAppend;
        driver.get(loginUrl);
        log.info("Logging in at URL: {}", loginUrl);

        // Wait for and interact with the username field
        try {
            WebElement usernameField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME_FIELD));
            usernameField.sendKeys(instagramProperties.getCredentials().getUsername());
        } catch (TimeoutException e) {
            throw new RuntimeException("Username field did not appear within the expected time.", e);
        }

        // Wait for and interact with the password field
        try {
            WebElement passwordField = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD_FIELD));
            passwordField.sendKeys(instagramProperties.getCredentials().getPassword());
        } catch (TimeoutException e) {
            throw new RuntimeException("Password field did not appear within the expected time.", e);
        }

        // Wait for and click the login button
        try {
            WebElement loginButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(LOGIN_BUTTON));
            loginButton.click();
        } catch (TimeoutException e) {
            throw new RuntimeException("Login button was not clickable within the expected time.", e);
        }

        // Optionally, wait for a successful login indicator (like a change in URL or a particular element)
        try {
            webDriverWait.until(ExpectedConditions.urlContains("instagram.com"));
        } catch (TimeoutException e) {
            throw new RuntimeException("Login did not complete successfully within the expected time.", e);
        }

        return driver;
    }

}
