package oppweeder.service;

import java.util.logging.Level;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.springframework.stereotype.Service;

@Service
public class WebDriverService {
    
    public WebDriver getHeadlessDriver() {
        ChromeOptions options = new ChromeOptions();
        // Enable headless mode and other arguments
        options.addArguments(
            //"--headless", 
            "--disable-gpu", 
            "--window-size=1920,1200", 
            "--ignore-certificate-errors", 
            "--remote-allow-origins=*"
            );
        
        // Set up logging preferences
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logPrefs);
        
        return new ChromeDriver(options);
    }
}
