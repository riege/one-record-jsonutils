plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group "com.riege"
java {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
}

dependencies {
    // api("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    api("com.fasterxml.jackson.core:jackson-annotations:2.14.2")
    // Java 8 date/time type `java.time.OffsetDateTime` not supported by default
    // so we need to add com.fasterxml.jackson.datatype:jackson-datatype-jsr310:
    api("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.1")
}

tasks.named<Test>("test") {
    // Use JUnit Platform for unit tests.
    useJUnitPlatform()
}

publishing {
    publications {
        create<MavenPublication>("thisLibrary") {
            groupId = "com.riege"
            artifactId = "one-record-jsonutils"
            from(components["java"])
            pom {
                name.set("ONE Record JSON Utils Java Library")
                description.set("Riege Software ONE Record JSON Utils Java Library")
                url.set(System.getenv("PROJECT_URL"))
            }
        }
    }
    repositories {
        maven {
            name = "remote"
            url = uri(System.getenv("MAVEN_PUBLISH_URL") ?: "")
            credentials {
                username = System.getenv("MAVEN_PUBLISH_USERNAME")
                password = System.getenv("MAVEN_PUBLISH_PASSWORD")
            }
        }
    }
}