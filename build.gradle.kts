import org.apache.commons.io.output.ByteArrayOutputStream
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // https://github.com/JetBrains/kotlin/releases
    kotlin("jvm") version "1.8.10"
    // https://maven.fabricmc.net/fabric-loom/fabric-loom.gradle.plugin/
    id("fabric-loom") version "1.0.17"
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
    maven(url = "https://www.cursemaven.com") { name = "curse" }
    maven(url = "https://jm.gserv.me/repository/maven-public/") { name = "journeymap" }
}

configurations.all {
    resolutionStrategy.cacheChangingModulesFor(0, TimeUnit.SECONDS)
}

dependencies {
    subprojects.forEach { subproject ->
        implementation(project(path = ":${subproject.name}", configuration = "namedElements"))
    }
    // runtime mods (for development only)
    listOf(
        // projectId to fileId
        "emi-580555" to "4406948", // https://www.curseforge.com/minecraft/mc-mods/emi/files
        "jade-324717" to "4160646", // https://www.curseforge.com/minecraft/mc-mods/jade/files
        "journeymap-32274" to "4385875", // https://www.curseforge.com/minecraft/mc-mods/journeymap/files
        "modmenu-308702" to "4145213", // https://www.curseforge.com/minecraft/mc-mods/modmenu/files
    ).forEachIndexed { i, mod ->
        if (i == 0) {
            // https://jm.gserv.me/repository/maven-public/info%2Fjourneymap%2Fjourneymap-api%2Fmaven-metadata.xml
            modCompileOnly("info.journeymap:journeymap-api:1.18.2-1.9-fabric-SNAPSHOT")
        }
        modRuntimeOnly("curse.maven:${mod.first}:${mod.second}") { exclude(group = "net.fabricmc") }
    }
}

allprojects {
    apply(plugin = "org.jetbrains.kotlin.jvm")
    apply(plugin = "fabric-loom")
    group = rootProject.group
    version = rootProject.version
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
    }
    tasks {
        processResources {
            inputs.property("version", rootProject.version)
            filesMatching("fabric.mod.json") {
                expand(mutableMapOf("version" to rootProject.version))
            }
        }
        build {
            doLast {
                if (project.name == rootProject.name) {
                    delete("${rootProject.buildDir}/libs/${rootProject.name}-${rootProject.version}.jar")
                } else {
                    val oldJar = "${project.name}-${rootProject.version}.jar"
                    if (File("${project.buildDir}/libs/$oldJar").exists()) {
                        val newJar = "${rootProject.name}-${project.name}-${rootProject.version}.jar"
                        copy {
                            from("${project.buildDir}/libs/")
                            include(oldJar)
                            into("${rootProject.buildDir}/libs/")
                            rename(oldJar, newJar)
                        }
                        println("Output: $newJar")
                    } else {
                        throw IllegalStateException("${project.name}/libs/$oldJar not found!")
                    }
                }
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
}
