package org.ftc.fileprocessing.app;

public record UnprocessedFileInfoRow(
        String fileName,
        String type,
        String path,
        Long fileSize
) {
}
