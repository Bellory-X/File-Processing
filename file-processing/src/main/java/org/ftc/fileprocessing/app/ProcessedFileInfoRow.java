package org.ftc.fileprocessing.app;

import java.time.ZonedDateTime;
import java.util.UUID;

public record ProcessedFileInfoRow(
        UUID fileId,
        String fileName,
        String type,
        String path,
        Long fileSize,
        ZonedDateTime processedDateTime
) {
}
