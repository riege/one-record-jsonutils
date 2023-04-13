/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.riege.onerecord.jsonutils;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.GregorianCalendar;
import java.util.HashSet;

import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JacksonObjectMapperTest {

    @Test void buildMapper() throws IOException {
        assertNotNull(JacksonObjectMapper.buildMapperWithTimeZone(), "Basic mapper must be available");
        assertNotNull(JacksonObjectMapper.buildMapperWithoutTimezone(), "Basic mapper must be available");

        TestPojo test = new TestPojo();
        test.types = new HashSet<>();
        test.types.add("someType");
        test.answer = 42;
        test.name = "some text";
        test.date = new GregorianCalendar(2023, 4 -1, 1, 13, 37).getTime();
        test.offsetDateTime = OffsetDateTime.of(2023, 12, 24,
            0, 42, 0, 0,
            ZoneOffset.ofHours(2));

        // Test serializing
        String json = JacksonObjectMapper.buildMapperWithTimeZone()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(test);

        // System.err.println(json);
        // Note: Serializer writes "types" property as "@type"
        String expected = "{\n"
            + "  \"@type\" : [ \"someType\" ],\n"
            + "  \"name\" : \"some text\",\n"
            + "  \"answer\" : 42,\n"
            + "  \"date\" : \"2023-04-01T13:37:00\",\n"
            + "  \"offsetDateTime\" : \"2023-12-24T00:42:00+02:00\"\n"
            + "}";
        assertEquals(expected, json);

        // Test parsing:
        // Note: Deserializer reads "@type" property into "types"
        TestPojo result = JacksonObjectMapper.buildMapperWithTimeZone()
            .readValue(expected.getBytes(), TestPojo.class);
        assertEquals(result.answer, test.answer);
        assertEquals(result.name, test.name);
        assertEquals(result.date, test.date);
        assertEquals(1, result.types.size());
        assertEquals(result.types.toArray()[0], test.types.toArray()[0]);

        json = JacksonObjectMapper.buildMapperWithoutTimezone()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(test);
        expected = expected.replace("+02:00", "");
        assertEquals(expected, json);

    }

}
