package org.ftc.fileprocessing.core.impl;

import org.ftc.fileprocessing.app.ProcessedFileInfoForm;
import org.ftc.fileprocessing.app.ProcessedFileInfoRow;
import org.ftc.fileprocessing.app.UnprocessedFileInfoRow;
import org.ftc.fileprocessing.core.impl.config.FileInfoConfigurationProperties;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public interface FileInfoMapper {

    static ProcessedFileInfoForm toFileInfoForm(FileInfo domainModel, FileInfoConfigurationProperties properties) {
        Matcher typeMatcher = Pattern.compile("^[^/\\\\]+").matcher(domainModel.path());
        return new ProcessedFileInfoForm(
                domainModel.fileId(),
                domainModel.fileName(),
                typeMatcher.find() && properties.dirs().contains(typeMatcher.group())
                        ? typeMatcher.group().toUpperCase()
                        : "ERROR",
                domainModel.path(),
                domainModel.fileSize(),
                domainModel.processedDateTime(),
                domainModel.lastModifiedDateTime()
        );
    }

    static ProcessedFileInfoRow toProcessedFileInfoRow(FileInfo domainModel, FileInfoConfigurationProperties properties) {
        Matcher typeMatcher = Pattern.compile("([^/\\\\]+)").matcher(domainModel.path());
        return new ProcessedFileInfoRow(
                domainModel.fileId(),
                domainModel.fileName(),
                typeMatcher.find() && properties.dirs().contains(typeMatcher.group())
                        ? typeMatcher.group(1).toUpperCase()
                        : "ERROR",
                domainModel.path(),
                domainModel.fileSize(),
                domainModel.processedDateTime()
        );
    }

    static UnprocessedFileInfoRow toUnprocessedFileInfoRow(FileInfo domainModel, FileInfoConfigurationProperties properties) {
        Matcher typeMatcher = Pattern.compile("([^/\\\\]+)").matcher(domainModel.path());
        return new UnprocessedFileInfoRow(
                domainModel.fileName(),
                typeMatcher.find() && properties.dirs().contains(typeMatcher.group())
                        ? typeMatcher.group(1).toUpperCase()
                        : "ERROR",
                domainModel.path(),
                domainModel.fileSize()
        );
    }
}
