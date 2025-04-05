package oppweeder.config;

import lombok.Data;
import lombok.ToString;

@Data
@ToString(exclude = "password")
public class CredentialProperties {
    private String username;
    private String password;
}
