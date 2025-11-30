package io.github.shkschneider.awesome.core

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import net.fabricmc.loader.api.FabricLoader
import java.lang.Exception
import java.nio.file.Files
import java.nio.file.Path

class AwesomeConfigFactory<T : Any>(
    private val name: String,
) {

    companion object {

        private val gson: Gson = GsonBuilder()
            .disableHtmlEscaping()
            .setLenient()
            .setPrettyPrinting()
            .create()

    }

    init {
        check(name.endsWith(".json").not())
    }

    private lateinit var data: T

    operator fun invoke(klass: Class<T>): T {
        val path: Path = FabricLoader.getInstance().configDir.resolve("$name.json")
        try {
            if (Files.exists(path).not()) {
                data = klass.getDeclaredConstructor().newInstance()
                Files.newBufferedWriter(path).use { writer ->
                    gson.toJson(data, writer)
                }
            } else {
                Files.newBufferedReader(path).use { reader ->
                    data = gson.fromJson(reader, klass)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            data = klass.getDeclaredConstructor().newInstance()
        }
        return data
    }

}
