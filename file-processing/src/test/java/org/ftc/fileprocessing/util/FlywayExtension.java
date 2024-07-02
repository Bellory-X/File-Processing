package org.ftc.fileprocessing.util;

import org.flywaydb.core.Flyway;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

public class FlywayExtension implements AfterEachCallback {

    @Override
    public void afterEach(ExtensionContext context) {
        ApplicationContext applicationContext = SpringExtension.getApplicationContext(context);
        remigrateWithFlyway(applicationContext);
    }

    private void remigrateWithFlyway(ApplicationContext applicationContext) {
        Flyway flyway = applicationContext.getBean(Flyway.class);
        flyway.clean();
        flyway.migrate();
    }
}
