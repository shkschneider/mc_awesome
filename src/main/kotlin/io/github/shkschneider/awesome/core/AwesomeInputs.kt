package io.github.shkschneider.awesome.core

import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper
import net.minecraft.client.MinecraftClient
import net.minecraft.client.gui.screen.Screen
import net.minecraft.client.option.KeyBinding
import net.minecraft.client.util.InputUtil
import org.lwjgl.glfw.GLFW

object AwesomeInputs {

    val keyboard = InputUtil.Type.KEYSYM
    val mouse = InputUtil.Type.MOUSE

    fun shift(): Boolean =
        Screen.hasShiftDown()

    fun control(): Boolean =
        Screen.hasControlDown()

    fun alt(): Boolean =
        shift() || control() || Screen.hasAltDown()

    val enter = GLFW.GLFW_KEY_ENTER
    val delete = GLFW.GLFW_KEY_DELETE
    // ...

    operator fun invoke(name: String, key: Int): KeyBinding =
        KeyBindingHelper.registerKeyBinding(get(name, key))

    fun get(name: String, key: Int): KeyBinding =
        KeyBinding(AwesomeUtils.translatable("key", name), keyboard, key, AwesomeUtils.translatable("key"))

    @Environment(EnvType.CLIENT)
    operator fun invoke(key: Int): Boolean =
        InputUtil.isKeyPressed(MinecraftClient.getInstance().window.handle, key)

}

