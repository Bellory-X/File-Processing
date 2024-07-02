package org.ftc.fileprocessing.core;

import org.ftc.fileprocessing.app.ProcessedFileInfoFilter;
import org.ftc.fileprocessing.app.ProcessedFileInfoForm;
import org.ftc.fileprocessing.app.ProcessedFileInfoRow;
import org.ftc.fileprocessing.app.UnprocessedFileInfoRow;

import java.util.List;
import java.util.UUID;

public interface FileInfoService {

    void processNewFiles();

    List<UnprocessedFileInfoRow> getUnprocessedFileInfoRows();

    List<ProcessedFileInfoRow> getProcessedFileInfoRows(ProcessedFileInfoFilter filter);

    ProcessedFileInfoForm getFileInfoForm(UUID fileId);
}
