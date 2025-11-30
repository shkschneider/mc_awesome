package io.github.shkschneider.awesome.core

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.custom.Minecraft
import org.slf4j.LoggerFactory

object AwesomeLogger {

    private val logger = LoggerFactory.getLogger(Awesome::class.java)

    @Deprecated("Not printing using logger.debug().")
    fun debug(msg: String) {
        if (Minecraft.isDevelopment) println(msg)
    }

    fun info(msg: String) {
        logger.info(msg)
    }

    fun warn(msg: String) {
        logger.warn(msg)
    }

    fun error(msg: String) {
        logger.error(msg)
    }

    fun trace(throwable: Throwable) {
        logger.trace(throwable.toString())
        throwable.printStackTrace()
    }


}
