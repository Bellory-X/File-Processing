package org.ftc.fileprocessing.core.impl.exception;

import java.util.UUID;

public class FindProcessedFileInfoException extends FileInfoException {
    public FindProcessedFileInfoException(UUID uuid) {
        super("Processed file info with fileId=%s not found".formatted(uuid.toString()));
    }
}
