package org.ftc.fileprocessing.platform.time;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.time.ZonedDateTime;

@Configuration
public class JacksonZonedDateTimeConfig {

    @Bean
    public JavaTimeModule module(
            StdSerializer<ZonedDateTime> zonedDateTimeStdSerializer,
            StdDeserializer<ZonedDateTime> zonedDateTimeStdDeserializer
    ) {
        JavaTimeModule simpleModule = new JavaTimeModule();
        simpleModule.addDeserializer(ZonedDateTime.class, zonedDateTimeStdDeserializer);
        simpleModule.addSerializer(ZonedDateTime.class, zonedDateTimeStdSerializer);
        return simpleModule;
    }

    @Bean
    public StdDeserializer<ZonedDateTime> zonedDateTimeJsonDeserializer(ZonedDateTimeConverter zonedDateTimeConverter) {
        return new StdDeserializer<>(ZonedDateTime.class) {
            @Override
            public ZonedDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                String source = jsonParser.getText();
                return zonedDateTimeConverter.convert(source);
            }
        };
    }

    @Bean
    public StdSerializer<ZonedDateTime> zonedDateTimeStdSerializer(ZonedDateTimeConverter zonedDateTimeConverter) {
        return new StdSerializer<>(ZonedDateTime.class) {
            @Override
            public void serialize(ZonedDateTime zonedDateTime, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
                String target = zonedDateTimeConverter.convert(zonedDateTime);
                jsonGenerator.writeString(target);
            }
        };
    }
}
