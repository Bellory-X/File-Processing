package org.ftc.fileprocessing.platform.time;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;

import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class LocaleResolverConfig {

    @Bean
    public LocaleResolver localeResolver() {
        HttpHeadersLocaleResolver httpHeadersLocaleResolver = new HttpHeadersLocaleResolver();
        httpHeadersLocaleResolver.setDefaultLocale(Locale.of("ru", "RU"));
        httpHeadersLocaleResolver.setDefaultTimeZone(TimeZone.getTimeZone("Asia/Yekaterinburg"));
        return httpHeadersLocaleResolver;
    }
}
