package org.ftc.fileprocessing.util;

import org.testcontainers.containers.PostgreSQLContainer;

import java.util.Map;

public class DefaultPostgreSqlContainer extends PostgreSQLContainer<DefaultPostgreSqlContainer> {

    public static final String POSTGRES_IMAGE_NAME = "postgres:15.2";
    public static final String DB_NAME = "test_db";
    public static final String USERNAME = "test_db";
    public static final String PASSWORD = "test_db";
    public static final Integer POSTGRES_PORT = 5432;

    private static DefaultPostgreSqlContainer postgres;

    public static DefaultPostgreSqlContainer getInstance() {
        if (postgres == null) {
            postgres = new DefaultPostgreSqlContainer();
            postgres.start();
        }

        return postgres;
    }

    public DefaultPostgreSqlContainer() {
        super(POSTGRES_IMAGE_NAME);
        super.withDatabaseName(DB_NAME)
                .withUsername(USERNAME)
                .withPassword(PASSWORD)
                .withExposedPorts(POSTGRES_PORT)
                .withTmpFs(Map.of("/var", "rw"));
    }
}
