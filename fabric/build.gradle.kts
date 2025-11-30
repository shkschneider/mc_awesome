plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    kotlin("jvm")
}

architectury {
    platformSetupLoomIde()
    fabric()
}

loom {
    silentMojangMappingsLicense()
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
    getByName("developmentFabric").extendsFrom(configurations["common"])
}

dependencies {
    // Fabric
    modImplementation("net.fabricmc:fabric-loader:${property("fabric_loader")}")
    modImplementation("net.fabricmc.fabric-api:fabric-api:${property("fabric_api")}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin")}")
    
    // Architectury API for Fabric
    modImplementation("dev.architectury:architectury-fabric:${property("architectury_api")}")
    
    // Common module
    "common"(project(":common", "namedElements")) { isTransitive = false }
    "shadowCommon"(project(":common", "transformProductionFabric")) { isTransitive = false }
    
    // EMI (Fabric)
    modImplementation("dev.emi:emi-fabric:1.1.12+1.20.6") { exclude(group = "net.fabricmc") }
}

tasks {
    processResources {
        inputs.property("version", project.version)
        filesMatching("fabric.mod.json") {
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
    
    shadowJar {
        archiveClassifier.set("dev-shadow")
        configurations = listOf(project.configurations["shadowCommon"])
    }
    
    remapJar {
        injectAccessWidener.set(true)
        inputFile.set(shadowJar.get().archiveFile)
        dependsOn(shadowJar)
        archiveClassifier.set("fabric")
    }
}
