package org.ftc.fileprocessing.core.impl;

import org.ftc.fileprocessing.app.ProcessedFileInfoFilter;
import org.ftc.fileprocessing.app.ProcessedFileInfoForm;
import org.ftc.fileprocessing.app.ProcessedFileInfoRow;
import org.ftc.fileprocessing.app.UnprocessedFileInfoRow;
import org.ftc.fileprocessing.core.FileInfoService;
import org.ftc.fileprocessing.platform.LogBefore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FileInfoServiceImpl implements FileInfoService {

    private final UnprocessedFileInfoService unprocessedFileInfoService;
    private final ProcessedFileInfoService processedFileInfoService;

    @Autowired
    public FileInfoServiceImpl(
            UnprocessedFileInfoService unprocessedFileInfoService,
            ProcessedFileInfoService processedFileInfoService
    ) {
        this.unprocessedFileInfoService = unprocessedFileInfoService;
        this.processedFileInfoService = processedFileInfoService;
    }

    @Async
    @Override
    @LogBefore
    public void processNewFiles() {
        unprocessedFileInfoService.process();
    }

    @Override
    @LogBefore
    public List<UnprocessedFileInfoRow> getUnprocessedFileInfoRows() {
        return unprocessedFileInfoService.getFileInfoRows();
    }

    @Override
    @LogBefore
    public List<ProcessedFileInfoRow> getProcessedFileInfoRows(ProcessedFileInfoFilter filter) {
        return processedFileInfoService.getFileInfoRows(filter);
    }

    @Override
    @LogBefore
    public ProcessedFileInfoForm getFileInfoForm(UUID fileId) {
        return processedFileInfoService.getFileInfoForm(fileId);
    }
}
