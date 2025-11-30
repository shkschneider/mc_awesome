plugins {
    id("dev.architectury.loom")
    id("architectury-plugin")
    kotlin("jvm")
}

architectury {
    common(project.property("enabled_platforms").toString().split(","))
}

loom {
    silentMojangMappingsLicense()
}

sourceSets.main {
    java {
        setSrcDirs(listOf("src/main/java", "src/main/kotlin"))
    }
}

dependencies {
    // Architectury API
    modImplementation("dev.architectury:architectury:${property("architectury_api")}")
    // Kotlin support
    modImplementation("net.fabricmc:fabric-language-kotlin:${property("fabric_kotlin")}")
}
