# one-record-jsonutils

## Introduction

Java serialization utility for official IATA Ontology on GitHub at https://github.com/IATA-Cargo/ONE-Record.

## How to use / integrate

To integrate this library in your own project, follow instructions at
https://jitpack.io/#riege/one-record-jsonutils because this library
is not published on Maven Central Repository. 

## Library usage

The following code provides a pre-configured ObjectMapper via Jackson which 
reflect the requirements and formatting rules of the IATA ONE Record project: 

        ObjectMapper mapper = JacksonObjectMapper.buildMapperWithoutTimezone();
        String json = mapper
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(object);

Reading into the right class could be done like this (assuming JSON contains the class in attribute `@type` as array, e.g. `"@type" : [ "https://some.host.domain/TestPojo" ]`):

        ObjectNode objectNode = mapper.readValue(json, ObjectNode.class);
        JsonNode typeNode = objectNode.get("@type");
        ArrayNode arrayNode = (ArrayNode) typeNode;
        String typeValue = arrayNode.get(0).textValue();
        String clazzName = typeValue.substring(typeValue.lastIndexOf('/')+1);
        String packageName = TestPojo.class.getPackage().getName();
        Class clazz = Class.forName(packageName + "." + clazzName);
        Object obj = mapper.readValue(json, clazz);
        TestPojo pojo = (TestPojo) obj;
