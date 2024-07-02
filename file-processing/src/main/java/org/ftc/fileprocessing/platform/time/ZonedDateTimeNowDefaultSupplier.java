package org.ftc.fileprocessing.platform.time;

import org.springframework.stereotype.Component;

import java.time.ZonedDateTime;

@Component
public class ZonedDateTimeNowDefaultSupplier implements ZonedDateTimeNowSupplier {

    @Override
    public ZonedDateTime get() {
        return ZonedDateTime.now();
    }
}
