package org.ftc.fileprocessing.core.impl;

import java.time.ZonedDateTime;
import java.util.Objects;
import java.util.UUID;

public record FileInfo(
        UUID fileId,
        String fileName,
        String path,
        Long fileSize,
        ZonedDateTime processedDateTime,
        ZonedDateTime lastModifiedDateTime
) {

    public FileInfo {
        Objects.requireNonNull(fileId);
        Objects.requireNonNull(fileName);
        Objects.requireNonNull(path);
        Objects.requireNonNull(fileSize);
        Objects.requireNonNull(processedDateTime);
        Objects.requireNonNull(lastModifiedDateTime);
    }
}
