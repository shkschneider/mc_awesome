rootProject.name = "Awesome"

pluginManagement {
    repositories {
        maven(url = "https://maven.architectury.dev") { name = "architectury" }
        maven(url = "https://maven.neoforged.net/releases") { name = "neoforge" }
        maven(url = "https://plugins.gradle.org/m2") { name = "gradle" }
        maven(url = "https://libraries.minecraft.net") { name = "minecraft" }
        maven(url = "https://maven.fabricmc.net") { name = "fabric" }
        mavenCentral()
        gradlePluginPortal()
    }
}

// Multi-loader architecture: common + fabric + neoforge
include("common")
include("fabric")
include("neoforge")
