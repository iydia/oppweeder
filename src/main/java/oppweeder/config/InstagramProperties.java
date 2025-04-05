package oppweeder.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
@Component
@ConfigurationProperties(prefix = "application.instagram")
public class InstagramProperties {
    private String username;
    private String password;
}
