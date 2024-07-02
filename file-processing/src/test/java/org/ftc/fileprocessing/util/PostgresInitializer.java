package org.ftc.fileprocessing.util;

import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;

public class PostgresInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
        TestPropertyValues.of(
                "spring.datasource.url=" + DefaultPostgreSqlContainer.getInstance().getJdbcUrl(),
                "spring.datasource.username=" + DefaultPostgreSqlContainer.USERNAME,
                "spring.datasource.password=" + DefaultPostgreSqlContainer.PASSWORD
        ).applyTo(applicationContext.getEnvironment());
    }
}
