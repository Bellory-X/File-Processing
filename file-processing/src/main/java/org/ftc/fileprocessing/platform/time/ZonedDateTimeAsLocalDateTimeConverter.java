package org.ftc.fileprocessing.platform.time;

import io.micrometer.common.lang.NonNullApi;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Optional;
import java.util.TimeZone;

@Component
@NonNullApi
public class ZonedDateTimeAsLocalDateTimeConverter implements ZonedDateTimeConverter {

    private final TimeZoneRequestSupplier timeZoneRequestSupplier;

    public ZonedDateTimeAsLocalDateTimeConverter(TimeZoneRequestSupplier timeZoneRequestSupplier) {
        this.timeZoneRequestSupplier = timeZoneRequestSupplier;
    }


    @Override
    public String convert(ZonedDateTime source) {
        return timeZoneRequestSupplier.get()
                .or(() -> Optional.of(TimeZone.getDefault()))
                .map(timeZone -> withTimeZone(source, timeZone))
                .map(TimeUtils::toStringAsLocalDateTime)
                .orElseThrow();
    }

    @Override
    public ZonedDateTime convert(String source) {
        Optional<LocalDateTime> localDateTimeWithoutTimezone = parseFromLocalDateTime(source);
        return timeZoneRequestSupplier.get()
                .flatMap(timeZone -> localDateTimeWithoutTimezone.map(it -> withTimeZone(it, timeZone)))
                .or(() -> parseFromZonedDateTime(source))
                .orElseThrow();
    }

    private Optional<LocalDateTime> parseFromLocalDateTime(String source) {
        try {
            return Optional.of(LocalDateTime.parse(source));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    private Optional<ZonedDateTime> parseFromZonedDateTime(String source) {
        try {
            return Optional.of(ZonedDateTime.parse(source.replace(' ', '+'), DateTimeFormatter.ISO_ZONED_DATE_TIME));
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    private ZonedDateTime withTimeZone(ZonedDateTime zonedDateTime, TimeZone newTimeZone) {
        return zonedDateTime.withZoneSameInstant(newTimeZone.toZoneId());
    }

    private ZonedDateTime withTimeZone(LocalDateTime localDateTime, TimeZone newTimeZone) {
        return ZonedDateTime.of(localDateTime, newTimeZone.toZoneId());
    }
}
