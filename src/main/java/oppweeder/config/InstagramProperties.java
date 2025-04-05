package oppweeder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;

@Data
@Component
@ConfigurationProperties(prefix = "application.instagram")
public class InstagramProperties {
    private String url;
    private CredentialProperties credential;
}
