import org.apache.commons.io.output.ByteArrayOutputStream
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://github.com/JetBrains/kotlin/releases
    kotlin("jvm") version "1.8.10" apply false
    // https://github.com/architectury/architectury-loom
    id("dev.architectury.loom") version "1.0-SNAPSHOT" apply false
    // https://github.com/architectury/architectury-plugin
    id("architectury-plugin") version "3.4-SNAPSHOT"
}

fun version(): String {
    val bytes = ByteArrayOutputStream()
    project.exec {
        commandLine = "git describe --tags HEAD".split(" ")
        standardOutput = bytes
    }
    return String(bytes.toByteArray()).trim()
}

architectury {
    minecraft = property("minecraft").toString()
}

allprojects {
    group = property("mod_group").toString()
    version = version()
}

subprojects {
    apply(plugin = "dev.architectury.loom")
    apply(plugin = "org.jetbrains.kotlin.jvm")

    repositories {
        maven(url = "https://maven.architectury.dev") { name = "architectury" }
        maven(url = "https://maven.neoforged.net/releases") { name = "neoforge" }
        maven(url = "https://api.modrinth.com/maven") { name = "modrinth" }
        maven(url = "https://cursemaven.com") { name = "curseforge" }
        maven(url = "https://maven.terraformersmc.com") { name = "terraformers" }
        mavenCentral()
    }

    dependencies {
        "minecraft"("com.mojang:minecraft:${property("minecraft")}")
        "mappings"("net.fabricmc:yarn:${property("yarn")}:v2")
    }

    tasks {
        withType<JavaCompile> {
            options.release.set(17)
            options.encoding = "UTF-8"
        }
        withType<KotlinCompile> {
            kotlinOptions.jvmTarget = "17"
        }
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
        withSourcesJar()
    }
}
