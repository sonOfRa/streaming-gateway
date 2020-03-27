package de.slevermann.streaminggate.config;

import lombok.Data;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import java.util.Map;

@Configuration
@ConfigurationProperties(prefix = "streaminggate.users")
@ConditionalOnProperty(prefix = "streaminggate.config", value = "db", havingValue = "false")
@Data
@Validated
public class UserConfig {

    @NotEmpty
    private Map<@NotBlank String, @NotBlank String> passKeys;
}
