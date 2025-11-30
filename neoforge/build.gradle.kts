plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    kotlin("jvm")
}

architectury {
    platformSetupLoomIde()
    forge()
}

loom {
    silentMojangMappingsLicense()
    
    forge {
        mixinConfig("awesome.mixins.json")
    }
}

sourceSets.main {
    java {
        setSrcDirs(listOf("src/main/java", "src/main/kotlin"))
    }
}

configurations {
    create("common")
    create("shadowCommon")
    compileClasspath.get().extendsFrom(configurations["common"])
    runtimeClasspath.get().extendsFrom(configurations["common"])
    getByName("developmentForge").extendsFrom(configurations["common"])
}

dependencies {
    // Forge
    forge("net.minecraftforge:forge:${property("forge")}")
    
    // Architectury API for Forge
    modImplementation("dev.architectury:architectury-forge:${property("architectury_api")}")
    
    // Kotlin for Forge
    implementation("thedarkcolour:kotlinforforge:3.12.0")
    
    // Common module
    "common"(project(":common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionForge")) { isTransitive = false }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        inputs.property("mod_id", project.property("mod_id"))
        inputs.property("mod_name", project.property("mod_name"))
        
        filesMatching("META-INF/mods.toml") {
            expand(mutableMapOf(
                "version" to project.version,
                "mod_id" to property("mod_id"),
                "mod_name" to property("mod_name")
            ))
        }
    }
    
    jar {
        from("LICENSE") {
            rename { "${it}_${project.property("mod_id")}" }
        }
    }
    
    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        archiveClassifier.set("forge")
    }
}

val shadowJar = tasks.register<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
    archiveClassifier.set("dev-shadow")
    configurations = listOf(project.configurations["shadowCommon"])
}
