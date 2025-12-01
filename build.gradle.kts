import org.apache.commons.io.output.ByteArrayOutputStream
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://github.com/JetBrains/kotlin/releases
    kotlin("jvm") version "1.8.10"
    // https://maven.fabricmc.net/fabric-loom/fabric-loom.gradle.plugin/
    id("fabric-loom") version "1.1.10"
}

fun version(): String {
    val bytes = ByteArrayOutputStream()
    project.exec {
        commandLine = "git describe --tags HEAD".split(" ")
        standardOutput = bytes
    }
    return String(bytes.toByteArray()).trim()
}
group = "io.github.shkschneider"
version = version()

repositories {
    maven(url = "https://api.modrinth.com/maven") { name = "modrinth" }
    maven(url = "https://cursemaven.com") { name = "curseforge" }
    maven(url = "https://maven.terraformersmc.com") { name = "terraformers" }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

sourceSets.main {
    java {
        setSrcDirs(listOf("src/main/java", "src/main/kotlin"))
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${property("minecraft")}")
    mappings("net.fabricmc:yarn:${property("yarn")}:v2")
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api")}")
    // https://maven.terraformersmc.com/dev/emi/emi
    modImplementation("dev.emi:emi-fabric:1.0.5+1.19.4") { exclude(group = "net.fabricmc") }
    // Runtime only mods for testing
    listOf(
        // projectId to fileId - Updated for 1.19.4
        "appleskin-248787" to "4465516", // https://www.curseforge.com/minecraft/mc-mods/appleskin/files
        "cloth-config-348521" to "4468193", // https://www.curseforge.com/minecraft/mc-mods/cloth-config/files
        "emi-580555" to "4489556", // https://www.curseforge.com/minecraft/mc-mods/emi/files
        "jade-324717" to "4465654", // https://www.curseforge.com/minecraft/mc-mods/jade/files
        "xaeros-minimap-263420" to "4495098", // https://www.curseforge.com/minecraft/mc-mods/xaeros-minimap/files
    ).forEach { mod ->
        modRuntimeOnly("curse.maven:${mod.first}:${mod.second}") { exclude(group = "net.fabricmc") }
    }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
            expand(mutableMapOf("version" to project.version))
        }
    }
    jar {
        from("LICENSE")
    }
    withType<JavaCompile> {
        options.release.set(JavaVersion.VERSION_17.toString().toInt())
        options.encoding = "UTF-8"
    }
    withType<KotlinCompile> {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_17.toString()
    }
}
