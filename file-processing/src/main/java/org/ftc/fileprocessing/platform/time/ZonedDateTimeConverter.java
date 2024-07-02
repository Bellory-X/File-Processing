package org.ftc.fileprocessing.platform.time;


import io.micrometer.common.lang.NonNullApi;

import java.time.ZonedDateTime;

@NonNullApi
public interface ZonedDateTimeConverter {

    String convert(ZonedDateTime source);

    ZonedDateTime convert(String source);
}
