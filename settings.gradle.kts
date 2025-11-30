rootProject.name = "Awesome"

// Unified mod - all modules merged into single project
// Machines module can be optionally included if needed
// include(":machines")

pluginManagement {
    repositories {
        maven(url = "https://plugins.gradle.org/m2") { name = "gradle" }
        maven(url = "https://libraries.minecraft.net") { name = "minecraft" }
        maven(url = "https://maven.fabricmc.net") { name = "fabric" }
        mavenCentral()
        gradlePluginPortal()
    }
}
