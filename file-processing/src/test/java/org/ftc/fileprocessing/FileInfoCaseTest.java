package org.ftc.fileprocessing;

import org.ftc.fileprocessing.app.FileInfoUrl;
import org.ftc.fileprocessing.platform.CommonUrl;
import org.ftc.fileprocessing.util.CaseTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@CaseTest
public class FileInfoCaseTest {

    @Autowired
    private WebTestClient webTestClient;

    @Test
    @Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "classpath:db/insert-default-file-info.sql")
    public void should_filtered_all_processed_FileInfoRows() {
        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_PROCESSED)
                        .queryParam("startDateTime", LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0))
                        .queryParam("endDateTime", LocalDateTime.now())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(2)
                .jsonPath("$.[0].fileId").isEqualTo("291a485f-a56a-4938-8f1a-bbbbbbbbbbb1")
                .jsonPath("$.[0].fileName").isEqualTo("first.txt")
                .jsonPath("$.[0].type").isEqualTo("ONE")
                .jsonPath("$.[0].fileSize").isEqualTo(1)
                .jsonPath("$.[0].processedDateTime").isEqualTo("29.06.2023, 22:00")

                .jsonPath("$.[1].fileId").isEqualTo("a711bbc5-337b-4d85-a383-6d46cc424981")
                .jsonPath("$.[1].fileName").isEqualTo("second.txt")
                .jsonPath("$.[1].type").isEqualTo("ERROR")
                .jsonPath("$.[1].fileSize").isEqualTo(2)
                .jsonPath("$.[1].processedDateTime").isEqualTo("29.06.2024, 22:00");
    }

    @Test
    @Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "classpath:db/insert-default-file-info.sql")
    public void should_filtered_by_upper_line_processed_FileInfoRows() {
        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_PROCESSED)
                        .queryParam("startDateTime", LocalDateTime.of(2023, 7, 1, 0, 0, 0, 0))
                        .queryParam("endDateTime", LocalDateTime.now())
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(1)
                .jsonPath("$.[0].fileId").isEqualTo("a711bbc5-337b-4d85-a383-6d46cc424981")
                .jsonPath("$.[0].fileName").isEqualTo("second.txt")
                .jsonPath("$.[0].type").isEqualTo("ERROR")
                .jsonPath("$.[0].fileSize").isEqualTo(2)
                .jsonPath("$.[0].processedDateTime").isEqualTo("29.06.2024, 22:00");
    }

    @Test
    @Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "classpath:db/insert-default-file-info.sql")
    public void should_filtered_by_bottom_line_processed_FileInfoRows() {
        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_PROCESSED)
                        .queryParam("startDateTime", LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0))
                        .queryParam("endDateTime", LocalDateTime.of(2024, 5, 1, 0, 0, 0, 0))
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(1)
                .jsonPath("$.[0].fileId").isEqualTo("291a485f-a56a-4938-8f1a-bbbbbbbbbbb1")
                .jsonPath("$.[0].fileName").isEqualTo("first.txt")
                .jsonPath("$.[0].type").isEqualTo("ONE")
                .jsonPath("$.[0].fileSize").isEqualTo(1)
                .jsonPath("$.[0].processedDateTime").isEqualTo("29.06.2023, 22:00");
    }

    @Test
    @Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "classpath:db/insert-default-file-info.sql")
    public void should_filtered_by_upper_and_bottom_lines_processed_FileInfoRows() {
        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_PROCESSED)
                        .queryParam("startDateTime", LocalDateTime.of(2023, 7, 1, 0, 0, 0, 0))
                        .queryParam("endDateTime", LocalDateTime.of(2024, 5, 1, 0, 0, 0, 0))
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.size()").isEqualTo(0);
    }

    @Test
    @Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "classpath:db/insert-default-file-info.sql")
    public void should_get_FileInfoForm() {
        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_ID)
                        .build("291a485f-a56a-4938-8f1a-bbbbbbbbbbb1"))
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.fileId").isEqualTo("291a485f-a56a-4938-8f1a-bbbbbbbbbbb1")
                .jsonPath("$.fileName").isEqualTo("first.txt")
                .jsonPath("$.type").isEqualTo("ONE")
                .jsonPath("$.path").isEqualTo("one")
                .jsonPath("$.fileSize").isEqualTo(1)
                .jsonPath("$.processedDateTime").isEqualTo("29.06.2023, 22:00")
                .jsonPath("$.lastModifiedDateTime").isEqualTo("28.06.2023, 22:00");
    }

    @Test
    @Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "classpath:db/insert-default-file-info.sql")
    public void should_not_get_FileInfoForm() {
        webTestClient.method(HttpMethod.GET)
                .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_ID)
                        .build("291a485f-a56a-4938-8f1a-bbbbbbbbbbb2"))
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody();
    }

    @Test
    @Sql(config = @SqlConfig(encoding = "UTF-8"), scripts = "classpath:db/insert-default-file-info.sql")
    public void should_get_unprocessed_FileInfoForm() {
        File testFile1 = new File("/storage/test1.txt");
        File testFile2 = new File("/storage/one/test2.txt");
        File testFile3 = new File("/storage/one/two/test3.txt");
        try {
            testFile1.createNewFile();
            testFile2.createNewFile();
            testFile3.createNewFile();
            assert testFile1.exists() && testFile2.exists() && testFile3.exists();
            webTestClient.method(HttpMethod.GET)
                    .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_UNPROCESSED)
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.size()").isEqualTo(3)
                    .jsonPath("$.[0].fileName").isEqualTo("test2.txt")
                    .jsonPath("$.[0].type").isEqualTo("ONE")
                    .jsonPath("$.[0].path").isEqualTo("one")
                    .jsonPath("$.[0].fileSize").isEqualTo(0)

                    .jsonPath("$.[1].fileName").isEqualTo("test3.txt")
                    .jsonPath("$.[1].type").isEqualTo("ONE")
                    .jsonPath("$.[1].path").isEqualTo("one\\two")
                    .jsonPath("$.[1].fileSize").isEqualTo(0)

                    .jsonPath("$.[2].fileName").isEqualTo("test1.txt")
                    .jsonPath("$.[2].type").isEqualTo("ERROR")
                    .jsonPath("$.[2].path").isEqualTo("")
                    .jsonPath("$.[2].fileSize").isEqualTo(0);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            testFile1.delete();
            testFile2.delete();
            testFile3.delete();
        }
    }

    @Test
    public void should_processed_FileInfos() {
        File testFile1 = new File("/storage/test1.txt");
        File testFile2 = new File("/storage/one/test2.txt");
        File testFile3 = new File("/storage/one/two/test3.txt");
        try {
            testFile1.createNewFile();
            testFile2.createNewFile();
            testFile3.createNewFile();
            assert testFile1.exists() && testFile2.exists() && testFile3.exists();
            webTestClient.method(HttpMethod.POST)
                    .uri(CommonUrl.API + FileInfoUrl.FILE_INFO_PROCESS)
                    .exchange()
                    .expectStatus().isOk();

            Thread.sleep(3000);

            webTestClient.method(HttpMethod.GET)
                    .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_UNPROCESSED)
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.size()").isEqualTo(0);

            webTestClient.method(HttpMethod.GET)
                    .uri(uriBuilder -> uriBuilder.path(CommonUrl.API + FileInfoUrl.FILE_INFO_PROCESSED)
                            .queryParam("startDateTime", LocalDateTime.of(2023, 1, 1, 0, 0, 0, 0))
                            .queryParam("endDateTime", LocalDateTime.now())
                            .build())
                    .exchange()
                    .expectStatus().isOk()
                    .expectBody()
                    .jsonPath("$.size()").isEqualTo(3)
                    .jsonPath("$.[0].fileName").isEqualTo("test2.txt")
                    .jsonPath("$.[0].type").isEqualTo("ONE")
                    .jsonPath("$.[0].path").isEqualTo("one")
                    .jsonPath("$.[0].fileSize").isEqualTo(0)

                    .jsonPath("$.[1].fileName").isEqualTo("test3.txt")
                    .jsonPath("$.[1].type").isEqualTo("ONE")
                    .jsonPath("$.[1].path").isEqualTo("one\\two")
                    .jsonPath("$.[1].fileSize").isEqualTo(0)

                    .jsonPath("$.[2].fileName").isEqualTo("test1.txt")
                    .jsonPath("$.[2].type").isEqualTo("ERROR")
                    .jsonPath("$.[2].path").isEqualTo("")
                    .jsonPath("$.[2].fileSize").isEqualTo(0);

        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            testFile1.delete();
            testFile2.delete();
            testFile3.delete();
        }
    }
}
