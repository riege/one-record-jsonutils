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

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test void readLogisticObject() throws IOException, ClassNotFoundException {
        String json = "{\n"
            + "  \"@type\" : [ \"https://some.host.domain/TestPojo\" ],\n"
            + "  \"name\" : \"the name\",\n"
            + "  \"answer\" : 42\n"
            + "}";

        ObjectMapper mapper = JacksonObjectMapper.buildMapperWithoutTimezone();
        ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
        assertTrue(objectNode.has("@type"));
        JsonNode typeNode = objectNode.get("@type");
        assertTrue(typeNode instanceof ArrayNode);
        ArrayNode arrayNode = (ArrayNode) typeNode;
        assertEquals(1, arrayNode.size());
        String typeValue = arrayNode.get(0).textValue();
        String clazzName = typeValue.substring(typeValue.lastIndexOf('/')+1);

        String packageName = this.getClass().getPackage().getName();
        packageName = JacksonObjectMapperTest.class.getPackage().getName();
        packageName = TestPojo.class.getPackage().getName();
        Class clazz = Class.forName(packageName + "." + clazzName);
        Object obj = mapper.readValue(json, clazz);
        assertTrue(obj instanceof TestPojo);
        TestPojo pojo = (TestPojo) obj;
        assertEquals("the name", pojo.getName());
        assertEquals(42, pojo.getAnswer());
    }

}
