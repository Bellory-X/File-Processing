package org.ftc.fileprocessing.core.impl.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

@ConfigurationProperties(prefix = "file.processing")
public record FileInfoConfigurationProperties(
        String pathToMainDir,
        List<String> dirs
) {
}
