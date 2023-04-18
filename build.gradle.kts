import org.jetbrains.kotlin.konan.properties.Properties
import java.net.URI

plugins {
    kotlin("jvm") version "1.8.20"
    `maven-publish`
}

group = "com.daiwenzh5.kt"
version = "1.0-SNAPSHOT"

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
}

val sourceJar by tasks.register("sourceJar", Jar::class) {
    archiveClassifier.set("sources")
    from(sourceSets.main.get().allSource)
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

publishing {
    repositories {
        maven {
            url = repositoryUri(
                "2046382-release-Kot3ui",
                "2046382-snapshot-sZVDXF/",
                "https://packages.aliyun.com/maven/repository/",
            )
            credentials {
                println("ext is ${ext.properties}")
                username = propOf("ALIYUN_USERNAME")
                password = propOf("ALIYUN_TOKEN")
            }
        }
    }

    publications {
        create<MavenPublication>("maven") {
            from(components["kotlin"])
            // 添加源码 JAR 到构件中
            artifact(sourceJar) {
                classifier = "sources"
            }
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


