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

        String json = JacksonObjectMapper.buildMapperWithoutTimezone()
            .writerWithDefaultPrettyPrinter()
            .writeValueAsString(object);
