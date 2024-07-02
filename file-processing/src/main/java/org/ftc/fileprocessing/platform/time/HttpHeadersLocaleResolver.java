package org.ftc.fileprocessing.platform.time;

import io.micrometer.common.lang.NonNullApi;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.i18n.LocaleContext;
import org.springframework.context.i18n.TimeZoneAwareLocaleContext;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.i18n.AbstractLocaleContextResolver;

import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

@NonNullApi
public class HttpHeadersLocaleResolver extends AbstractLocaleContextResolver {

    /**
     * Заголовок "Accept-Language" описан в спецификации. Вследствие сделан неизменяемым.
     */
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

    /**
     * Заголовок "Time-Zone" является кастомным заголовком.
     * Вследствие чего он используется через переменную timeZoneHeader и его можно переопределить.
     */
    private static final String DEFAULT_TIME_ZONE_HEADER = "Time-Zone";

    private String timeZoneHeader = DEFAULT_TIME_ZONE_HEADER;


    @Override
    public LocaleContext resolveLocaleContext(HttpServletRequest request) {
        return new TimeZoneAwareLocaleContext() {
            @Override
            public Locale getLocale() {
                Locale defaultLocale = getDefaultLocale();
                if (defaultLocale != null && request.getHeader(ACCEPT_LANGUAGE_HEADER) == null) {
                    return defaultLocale;
                }

                return request.getLocale();
            }

            @Override
            @Nullable
            public TimeZone getTimeZone() {
                return parseTimeZoneHeader(request)
                        .orElseGet(() -> getDefaultTimeZone());
            }
        };
    }

    @Override
    public void setLocaleContext(HttpServletRequest request, @Nullable HttpServletResponse response, @Nullable LocaleContext localeContext) {
        throw new UnsupportedOperationException("Cannot change HTTP Accept-Language header and custom HTTP - use a different locale resolution strategy");
    }

    public void setTimeZoneHeader(String timeZoneHeader) {
        this.timeZoneHeader = timeZoneHeader;
    }

    private Optional<TimeZone> parseTimeZoneHeader(HttpServletRequest request) {
        String timeZoneHeaderContent = request.getHeader(timeZoneHeader);
        if (timeZoneHeaderContent == null) {
            return Optional.empty();
        }

        try {
            return Optional.of(TimeZone.getTimeZone(ZoneId.of(timeZoneHeaderContent)));
        } catch (DateTimeException e) {
            return Optional.empty();
        }
    }
}
