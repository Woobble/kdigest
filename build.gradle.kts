import java.time.Duration

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    val kotlin_version: String by System.getProperties()
    val nexus_version: String by System.getProperties()

    kotlin("multiplatform") version kotlin_version apply false
    id("io.github.gradle-nexus.publish-plugin") version nexus_version
}

val kotlin_version: String by System.getProperties()
val multik_version: String by project
val unpublished = listOf("kdigest")

allprojects {
    repositories {
        mavenCentral()
    }

    group = "me.woobb"
    version = multik_version

}