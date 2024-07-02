package org.ftc.fileprocessing.platform.time;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import java.util.Optional;
import java.util.TimeZone;

@Component
public class TimeZoneHttpServletRequestSupplier implements TimeZoneRequestSupplier {

    @Override
    public Optional<TimeZone> get() {
        return Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .map(ServletRequestAttributes::getRequest)
                .map(RequestContextUtils::getTimeZone);
    }
}
