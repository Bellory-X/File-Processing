package org.ftc.fileprocessing.core.impl;

import com.github.f4b6a3.uuid.UuidCreator;
import org.ftc.fileprocessing.app.ProcessedFileInfoFilter;
import org.ftc.fileprocessing.platform.DbMapException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class FileInfoRepository {

    private static final String BATCH_INSERT_FILE_INFO = """
            INSERT INTO file_info (id, file_name, path, file_size, processed_time, last_modified_time) VALUES (?,?,?,?,?,?)
            """;
    private static final String SELECT_FILE_INFO_BY_FILE_NAME_AND_PATH_AND_LAST_MODIFIED_TIME = """
            SELECT * FROM file_info WHERE file_name = ? AND path = ? AND last_modified_time = ?;
            """;
    private static final String SELECT_FILE_INFO_BY_FILTER = """
            SELECT * FROM file_info WHERE processed_time BETWEEN ? AND ? ORDER BY id;
            """;
    private static final String SELECT_FILE_INFO_BY_ID = """
            SELECT * FROM file_info WHERE id = ?;
            """;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public FileInfoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void add(FileInfo domainModel) {
        jdbcTemplate.update(BATCH_INSERT_FILE_INFO, ps -> map(ps, domainModel));
    }

    public void addAll(List<FileInfo> domainModels) {
        jdbcTemplate.batchUpdate(BATCH_INSERT_FILE_INFO, domainModels, domainModels.size(), this::map);
    }

    public Optional<FileInfo> findByFileNameAndPathAndLastModifiedTime(String fileName, String path, ZonedDateTime lastModifiedTime) {
        return jdbcTemplate.query(
                SELECT_FILE_INFO_BY_FILE_NAME_AND_PATH_AND_LAST_MODIFIED_TIME,
                this::map,
                fileName,
                path,
                lastModifiedTime.toLocalDateTime()
        ).stream().findFirst();
    }

    public List<FileInfo> findAllByFilter(ProcessedFileInfoFilter filter) {
        return jdbcTemplate.query(
                SELECT_FILE_INFO_BY_FILTER,
                this::map,
                filter.startDateTime().toLocalDateTime(),
                filter.endDateTime().toLocalDateTime()
        );
    }

    public Optional<FileInfo> findById(UUID fileId) {
        return jdbcTemplate.query(SELECT_FILE_INFO_BY_ID, this::map, fileId).stream().findFirst();
    }

    private FileInfo map(ResultSet rs, Integer rowNum) {
        try {
            return new FileInfo(
                    UuidCreator.fromString(rs.getString("id")),
                    rs.getString("file_name"),
                    rs.getString("path"),
                    rs.getLong("file_size"),
                    rs.getTimestamp("processed_time").toLocalDateTime().atZone(ZoneId.systemDefault()),
                    rs.getTimestamp("last_modified_time").toLocalDateTime().atZone(ZoneId.systemDefault())
            );
        } catch (SQLException e) {
            throw new DbMapException(e);
        }
    }

    private void map(PreparedStatement ps, FileInfo domainModel) {
        try {
            ps.setObject(1, domainModel.fileId());
            ps.setString(2, domainModel.fileName());
            ps.setString(3, domainModel.path());
            ps.setLong(4, domainModel.fileSize());
            ps.setTimestamp(5, Timestamp.valueOf(domainModel.processedDateTime().toLocalDateTime()));
            ps.setTimestamp(6, Timestamp.valueOf(domainModel.lastModifiedDateTime().toLocalDateTime()));
        } catch (SQLException e) {
            throw new DbMapException(e);
        }
    }
}
