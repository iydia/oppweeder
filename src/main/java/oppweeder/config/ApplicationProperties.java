package oppweeder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import lombok.Data;

@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationProperties {
    private InstagramProperties instagramProperties;
}
