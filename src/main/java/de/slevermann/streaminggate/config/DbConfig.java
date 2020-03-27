package de.slevermann.streaminggate.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({DbConfig.WithDb.class, DbConfig.WithoutDb.class})
public class DbConfig {

    @ConditionalOnProperty(prefix = "streaminggate.config", value = "db", havingValue = "true")
    @EnableAutoConfiguration
    public static class WithDb {

    }

    @ConditionalOnProperty(prefix = "streaminggate.config", value = "db", havingValue = "false")
    @EnableAutoConfiguration(
            exclude = {DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class,
                    HibernateJpaAutoConfiguration.class})
    public static class WithoutDb {

    }
}
