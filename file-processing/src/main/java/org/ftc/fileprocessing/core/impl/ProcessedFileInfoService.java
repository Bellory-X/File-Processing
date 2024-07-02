package org.ftc.fileprocessing.core.impl;

import org.ftc.fileprocessing.app.ProcessedFileInfoFilter;
import org.ftc.fileprocessing.app.ProcessedFileInfoForm;
import org.ftc.fileprocessing.app.ProcessedFileInfoRow;
import org.ftc.fileprocessing.core.impl.config.FileInfoConfigurationProperties;
import org.ftc.fileprocessing.core.impl.exception.FindProcessedFileInfoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ProcessedFileInfoService {

    private final FileInfoConfigurationProperties properties;
    private final FileInfoRepository repository;

    @Autowired
    public ProcessedFileInfoService(FileInfoConfigurationProperties properties, FileInfoRepository repository) {
        this.properties = properties;
        this.repository = repository;
    }

    public List<ProcessedFileInfoRow> getFileInfoRows(ProcessedFileInfoFilter filter) {
        return repository.findAllByFilter(filter).stream()
                .map(it -> FileInfoMapper.toProcessedFileInfoRow(it, properties))
                .toList();
    }

    public ProcessedFileInfoForm getFileInfoForm(UUID fileId) {
        return repository.findById(fileId)
                .map(it -> FileInfoMapper.toFileInfoForm(it, properties))
                .orElseThrow(() -> new FindProcessedFileInfoException(fileId));
    }
}
