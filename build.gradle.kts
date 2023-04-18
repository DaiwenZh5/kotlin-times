
import org.jetbrains.kotlin.konan.properties.Properties
import java.net.URI

plugins {
    kotlin("jvm") version "1.8.20"
    `maven-publish`
    signing
}

group = "io.github.daiwenzh5.kt"
version = "1.0.0"

repositories {
    mavenCentral()
    maven {
        url = uri("https://maven.aliyun.com/nexus/content/groups/public/")
    }
}

dependencies {
    api("com.fasterxml.jackson.module:jackson-module-kotlin:2.14.2")
    testImplementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")
    testImplementation("org.jetbrains.kotlin:kotlin-test:1.8.20")
}

tasks.test {
    useJUnitPlatform()
}


kotlin {
    jvmToolchain(8)
}

java {
    withSourcesJar()
    withJavadocJar()
}

ext {
    Properties().apply {
        File("gradle-local.properties")
            .takeIf { it.exists() }
            ?.reader()
            ?.let { this.load(it) }
    }.forEach {
        this[it.key as String] = it.value
    }
}

signing {
    sign("publishing.publications.mavenJava")
}

publishing {
    repositories {
        maven {
            url = repositoryUri(
                "2046382-release-Kot3ui",
                "2046382-snapshot-sZVDXF/",
                "https://packages.aliyun.com/maven/repository/",
            )
            credentials {
                username = propOf("ALIYUN_USERNAME")
                password = propOf("ALIYUN_TOKEN")
            }
        }
        maven {
            name = "OSSRH"
            url = repositoryUri(
                "service/local/staging/deploy/maven2/",
                "content/repositories/snapshots/",
                "https://s01.oss.sonatype.org/"
            )
            credentials {
                username = propOf("OSSRH_USERNAME")
                password = propOf("OSSRH_PASSWORD")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            // 添加源码、javadoc JAR 到构件中
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
        }
    }
}

fun repositoryUri(release: String, snapshot: String = release, prefix: String = ""): URI {
    val versionLocal = version as String
    return uri(prefix + if (!versionLocal.endsWith("SNAPSHOT")) release else snapshot)
}

fun propOf(name: String): String = (ext.properties[name] as String?)
    ?: providers.gradleProperty(name).orNull
    ?: System.getenv(name)
    ?: "".also { logger.error("property $name is not exist in gradle.properties or env variables")}


