repositories {
    maven(url = "https://maven.terraformersmc.com") { name = "terraformers" }
}

dependencies {
    implementation(project(path = ":core", configuration = "namedElements"))
    // https://maven.terraformersmc.com/dev/emi/emi
    modImplementation("dev.emi:emi:0.6.6+1.18.2") { exclude(group = "net.fabricmc") }
}
