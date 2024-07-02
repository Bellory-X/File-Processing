package org.ftc.fileprocessing.app;

public interface FileInfoUrl {

    String FILE_INFO = "/file-info";
    String FILE_INFO_ID = FILE_INFO + "/{fileId}";
    String FILE_INFO_PROCESS = FILE_INFO + "/process";
    String FILE_INFO_PROCESSED = FILE_INFO + "/processed";
    String FILE_INFO_UNPROCESSED = FILE_INFO + "/unprocessed";
}
