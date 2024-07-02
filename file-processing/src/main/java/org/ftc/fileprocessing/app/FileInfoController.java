package org.ftc.fileprocessing.app;

import org.ftc.fileprocessing.core.FileInfoService;
import org.ftc.fileprocessing.platform.CommonUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(CommonUrl.API)
public class FileInfoController {

    private final FileInfoService service;

    @Autowired
    public FileInfoController(FileInfoService service) {
        this.service = service;
    }

    @PostMapping(FileInfoUrl.FILE_INFO_PROCESS)
    public void processNewFiles() {
        service.processNewFiles();
    }

    @GetMapping(FileInfoUrl.FILE_INFO_PROCESSED)
    public List<ProcessedFileInfoRow> getProcessedFileInfoRows(
            @RequestParam(name = "startDateTime") ZonedDateTime startDate,
            @RequestParam(name = "endDateTime") ZonedDateTime endDate
    ) {
        return service.getProcessedFileInfoRows(new ProcessedFileInfoFilter(startDate, endDate));
    }

    @GetMapping(FileInfoUrl.FILE_INFO_UNPROCESSED)
    public List<UnprocessedFileInfoRow> getUnprocessedFileInfoRows() {
        return service.getUnprocessedFileInfoRows();
    }

    @GetMapping(FileInfoUrl.FILE_INFO_ID)
    public ProcessedFileInfoForm getFileInfoForm(@PathVariable UUID fileId) {
        return service.getFileInfoForm(fileId);
    }
}
