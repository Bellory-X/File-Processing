package org.ftc.fileprocessing.core.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import org.ftc.fileprocessing.app.UnprocessedFileInfoRow;
import org.ftc.fileprocessing.core.impl.config.FileInfoConfigurationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UnprocessedFileInfoService {

    private static final String pathRegex = "[/\\\\](.*)[/\\\\].+";
    private final FileInfoConfigurationProperties properties;
    private final FileInfoRepository repository;
    private final List<FileInfo> unprocessedFiles = new CopyOnWriteArrayList<>();
    private Long lastModifiedStartDir = 0L;

    @Autowired
    public UnprocessedFileInfoService(
            FileInfoConfigurationProperties properties,
            FileInfoRepository repository
    ) {
        this.properties = properties;
        this.repository = repository;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void process() {
        repository.addAll(findUnprocessedFileInfos());
        unprocessedFiles.clear();
    }

    public List<UnprocessedFileInfoRow> getFileInfoRows() {
        return findUnprocessedFileInfos().stream()
                .map(it -> FileInfoMapper.toUnprocessedFileInfoRow(it, properties))
                .toList();
    }

    private synchronized List<FileInfo> findUnprocessedFileInfos() {
        try {
            Path startPath = Paths.get("/" + properties.pathToMainDir());
            File startDir = startPath.toFile();
            if (startDir.exists() && startDir.isDirectory() && (startDir.lastModified() != lastModifiedStartDir)) {
                lastModifiedStartDir = startDir.lastModified();
                unprocessedFiles.clear();
                Files.walkFileTree(startPath, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult visitFile(Path filePath, BasicFileAttributes attrs) {
                        addUnprocessedFileInfo(unprocessedFiles, filePath.toFile(), attrs);
                        return FileVisitResult.CONTINUE;
                    }
                });
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return unprocessedFiles;
    }

    private void addUnprocessedFileInfo(List<FileInfo> unprocessedFiles, File file, BasicFileAttributes attrs) {
        Matcher pathMatcher = Pattern.compile(Pattern.quote(properties.pathToMainDir()) + pathRegex).matcher(file.getPath());
        String path = pathMatcher.find() ? pathMatcher.group(1) : "";
        ZonedDateTime lastModifiedTime = attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault());
        Optional<FileInfo> fileInfo = repository.findByFileNameAndPathAndLastModifiedTime(file.getName(), path, lastModifiedTime);
        if (fileInfo.isEmpty() || !fileInfo.get().lastModifiedDateTime().equals(lastModifiedTime)) {
            unprocessedFiles.add(new FileInfo(
                    UuidCreator.getTimeOrderedEpoch(),
                    file.getName(),
                    path,
                    file.length(),
                    ZonedDateTime.now(),
                    attrs.lastModifiedTime().toInstant().atZone(ZoneId.systemDefault())
            ));
        }
    }
}
