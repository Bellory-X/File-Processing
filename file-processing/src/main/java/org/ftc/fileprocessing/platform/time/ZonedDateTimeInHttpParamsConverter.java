package org.ftc.fileprocessing.platform.time;

import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class ZonedDateTimeInHttpParamsConverter implements Converter<String, ZonedDateTime> {

    private final ZonedDateTimeConverter zonedDateTimeConverter;

    public ZonedDateTimeInHttpParamsConverter(ZonedDateTimeConverter zonedDateTimeConverter) {
        this.zonedDateTimeConverter = zonedDateTimeConverter;
    }

    @Override
    @Nullable
    public ZonedDateTime convert(@NonNull String source) {
        if (source.isEmpty()) {
            return null;
        }

        return zonedDateTimeConverter.convert(source);
    }
}
