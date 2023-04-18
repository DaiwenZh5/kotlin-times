
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



publishing {
    repositories {
        maven {
            name = "Aliyun"
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
        val publication = create<MavenPublication>("maven") {
            from(components["kotlin"])
            // 添加源码、javadoc JAR 到构件中
            artifact(tasks["sourcesJar"])
            artifact(tasks["javadocJar"])
            pom {
                name.set("kotlin-times")
                description.set("A kotlin time extension")
                url.set("https://github.com/daiwenzh5/kotlin-times")
                licenses {
                    license {
                        name.set("Apache License, Version 2.0")
                        url.set("https://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }
                developers {
                    developer {
                        name.set("daiwenzh5")
                        email.set("daiwenzh5@163.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/daiwenzh5/kotlin-times.git")
                    developerConnection.set("scm:git:ssh://github.com/daiwenzh5/kotlin-times.git")
                    url.set("https://github.com/daiwenzh5/kotlin-times")
                }
            }
        }
        signing {
            sign(publication)
            if (System.getenv("CI") == "true") {
                logger.warn("signing with ci: ${System.getenv("CI")}")
                useInMemoryPgpKeys(
                    propOf("SIGNING_KEY_ID"),
                    propOf("SIGNING_SECRET_KEY"),
                    propOf("SIGNING_PASSWORD")
                )
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
    ?: "".also { logger.error("property $name is not exist in gradle.properties or env variables") }


