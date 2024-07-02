package org.ftc.fileprocessing.app;

import java.time.ZonedDateTime;
import java.util.Objects;

public record ProcessedFileInfoFilter(
        ZonedDateTime startDateTime,
        ZonedDateTime endDateTime
) {

    public ProcessedFileInfoFilter {
        Objects.requireNonNull(startDateTime);
        Objects.requireNonNull(endDateTime);
    }
}
