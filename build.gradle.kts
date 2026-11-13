import java.io.ByteArrayOutputStream

plugins {
    // Apply the java-library plugin for API and implementation separation.
    `java-library`
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

group = "com.riege"

/*
 * Gets the version name from the latest Git tag, omit leading "v" - yeah!
 * Note: a plugin way to do this would be via https://plugins.gradle.org/plugin/net.nemerosa.versioning
 */
fun getVersionName(): String {
    val output = providers.exec {
        commandLine("git", "describe", "--tags", "--always")
        isIgnoreExitValue = true
    }
    val tag = output.standardOutput.asText.get().trim()
    return tag.removePrefix("v")
}

/*
 * Gets the commit hash from the latest Git
 * Note: a plugin way to do this would be via https://plugins.gradle.org/plugin/net.nemerosa.versioning
 */
fun getGitCommitHash(): String {
    val output = providers.exec {
        commandLine("git", "log", "-1", "--format=%H")
        isIgnoreExitValue = true
    }
    return output.standardOutput.asText.get().trim().replace("\"", "")
}

val configuredVersion = project.version.toString()
project.version = if (configuredVersion != "unspecified") configuredVersion else getVersionName()

java {
    sourceCompatibility = JavaVersion.VERSION_25
    targetCompatibility = JavaVersion.VERSION_25
    withSourcesJar()
}

repositories {
    // Use Maven Central for resolving dependencies.
    mavenCentral()
    mavenLocal()
}

dependencies {
    // The API needs databind because JacksonObjectMapper.java
    // exposes a com.fasterxml.jackson.databind.ObjectMapper
    // on its public APi.
    api("com.fasterxml.jackson.core:jackson-databind:2.23.0")

    implementation("com.fasterxml.jackson.core:jackson-annotations:2.23")
    // Java 8 date/time type java.time.OffsetDateTime not supported by default,
    // so we need to add com.fasterxml.jackson.datatype:jackson-datatype-jsr310
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.23")

    // Use JUnit Jupiter for testing.
    testImplementation("org.junit.jupiter:junit-jupiter:5.9.2")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
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
            url = uri(System.getenv("MAVEN_PUBLISH_URL") ?: " ")
            credentials {
                username = System.getenv("MAVEN_PUBLISH_USERNAME")
                password = System.getenv("MAVEN_PUBLISH_PASSWORD")
            }
        }
    }
}
