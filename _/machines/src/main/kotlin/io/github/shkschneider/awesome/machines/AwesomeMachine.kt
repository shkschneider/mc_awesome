package io.github.shkschneider.awesome.machines

import io.github.shkschneider.awesome.core.AwesomeBlockEntity
import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.custom.InputOutput
import io.github.shkschneider.awesome.custom.Minecraft
import net.minecraft.block.BlockState
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.text.Text
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

abstract class AwesomeMachine<BE : AwesomeBlockEntity.WithInventory, SH : AwesomeMachineScreenHandler<BE>>(
    val id: String,
    val io: InputOutput,
    val properties: Int = PROPERTIES,
) {

    companion object {

        const val PROPERTIES = 3

    }

    private lateinit var _block: AwesomeMachineBlock<BE, SH>
    val block get() = _block

    private lateinit var _screen: ScreenHandlerType<SH>
    val screen get() = _screen

    init {
        init()
    }

    private fun init() {
        _block = block()
        _screen = AwesomeRegistries.screen(id) { syncId, playerInventory ->
            screenHandler(syncId, playerInventory)
        }
        if (Minecraft.isClient) AwesomeRegistries.screenHandler(screen) { handler, playerInventory, title ->
            screen(handler, playerInventory, title)
        }
    }

    abstract fun block(): AwesomeMachineBlock<BE, SH>

    abstract fun screenHandler(syncId: Int, playerInventory: PlayerInventory): SH

    abstract fun screen(handler: SH, playerInventory: PlayerInventory, title: Text): AwesomeMachineScreen<BE, SH>

    abstract fun tick(world: World, pos: BlockPos, state: BlockState, blockEntity: BE)


}
