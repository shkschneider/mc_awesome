package io.github.shkschneider.awesome.core

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.arguments.StringArgumentType.string
import com.mojang.brigadier.context.CommandContext
import io.github.shkschneider.awesome.custom.Permissions
import io.github.shkschneider.awesome.mixins.IBrewingRecipesMixin
import net.fabricmc.api.EnvType
import net.fabricmc.api.Environment
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback
import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.`object`.builder.v1.entity.FabricEntityTypeBuilder
import net.fabricmc.fabric.api.registry.FuelRegistry
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.client.gui.screen.ingame.HandledScreen
import net.minecraft.client.gui.screen.ingame.HandledScreens
import net.minecraft.client.render.RenderLayer
import net.minecraft.command.CommandRegistryAccess
import net.minecraft.command.argument.EntityArgumentType
import net.minecraft.enchantment.Enchantment
import net.minecraft.entity.EntityType
import net.minecraft.entity.effect.StatusEffect
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.item.ItemConvertible
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.potion.Potion
import net.minecraft.registry.Registries
import net.minecraft.registry.Registry
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerType
import net.minecraft.server.command.CommandManager
import net.minecraft.server.command.ServerCommandSource
import net.minecraft.text.Text
import net.minecraft.util.Identifier
import net.minecraft.util.math.BlockPos
import net.minecraft.world.GameRules

object AwesomeRegistries {

    fun block(id: Identifier, block: Block): Block =
        Registry.register(Registries.BLOCK, id, block)

    fun blockItem(id: Identifier, blockItem: BlockItem, group: ItemGroup?): Item =
        item(id, blockItem as Item, group)

    fun blockWithItem(id: Identifier, block: Block, group: ItemGroup): BlockItem =
        BlockItem(block(id, block), FabricItemSettings()).also { blockItem ->
            blockItem(id, blockItem, group)
        }

    @Environment(EnvType.CLIENT)
    fun blockRenderer(block: Block, renderLayer: RenderLayer) {
        BlockRenderLayerMap.INSTANCE.putBlock(block, renderLayer)
    }

    fun blockEntityType(id: Identifier, block: Block, createBlockEntity: (BlockPos, BlockState) -> BlockEntity): BlockEntityType<BlockEntity> =
        Registry.register(Registries.BLOCK_ENTITY_TYPE, id, FabricBlockEntityTypeBuilder.create({ pos, state -> createBlockEntity(pos, state) }, block).build(null))

    fun command(name: String, permission: Permissions = Permissions.Anyone, run: (CommandContext<ServerCommandSource>) -> Int) {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>, _: CommandRegistryAccess, _: CommandManager.RegistrationEnvironment ->
            dispatcher.register(CommandManager.literal(name).requires { it.hasPermissionLevel(permission.level) }.executes(run))
        })
    }

    fun commandWithText(name: String, permission: Permissions = Permissions.Anyone, run: (CommandContext<ServerCommandSource>) -> Int) {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>, _: CommandRegistryAccess, _: CommandManager.RegistrationEnvironment ->
            dispatcher.register(CommandManager.literal(name).requires { it.hasPermissionLevel(permission.level) }
                .then(CommandManager.argument("text", string()).executes(run)))
        })
    }

    fun commandForPlayer(name: String, permission: Permissions = Permissions.Anyone, run: (CommandContext<ServerCommandSource>) -> Int) {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>, _: CommandRegistryAccess, _: CommandManager.RegistrationEnvironment ->
            dispatcher.register(CommandManager.literal(name).requires { it.hasPermissionLevel(permission.level) }
                .then(CommandManager.argument("player", EntityArgumentType.player()).executes(run)))
        })
    }

    fun commandForPlayers(name: String, permission: Permissions = Permissions.Anyone, run: (CommandContext<ServerCommandSource>) -> Int) {
        CommandRegistrationCallback.EVENT.register(CommandRegistrationCallback { dispatcher: CommandDispatcher<ServerCommandSource?>, _: CommandRegistryAccess, _: CommandManager.RegistrationEnvironment ->
            dispatcher.register(CommandManager.literal(name).requires { it.hasPermissionLevel(permission.level) }
                .then(CommandManager.argument("players", EntityArgumentType.players()).executes(run)))
        })
    }

    fun enchantment(id: Identifier, enchantment: Enchantment): Enchantment =
        Registry.register(Registries.ENCHANTMENT, id, enchantment)

    fun fuel(item: Item, time: Int) {
        FuelRegistry.INSTANCE.add(item, time)
    }

    fun gameRule/*Boolean*/(name: String, category: GameRules.Category, default: Boolean): GameRules.Key<GameRules.BooleanRule> =
        GameRuleRegistry.register(name, category, GameRuleFactory.createBooleanRule(default))

    fun group(id: Identifier, item: Item): ItemGroup =
        FabricItemGroup.builder(id).displayName(Text.translatable(AwesomeUtils.translatable("itemGroup", id.path))).icon { ItemStack(item, 1) }.build()

    fun group(group: ItemGroup, item: ItemConvertible) {
        ItemGroupEvents.modifyEntriesEvent(group).register { entries -> entries.add(item) }
    }

    fun <T : HostileEntity> hostileEntity(id: Identifier, builder: FabricEntityTypeBuilder<T>): EntityType<T> =
        Registry.register(Registries.ENTITY_TYPE, id, builder.build())

    fun item(id: Identifier, item: Item, group: ItemGroup?): Item =
        Registry.register(Registries.ITEM, id, item).also {
            if (group != null) group(group, it)
        }

    fun potion(name: String, effectInstance: StatusEffectInstance, recipe: Pair<Potion, Item>?): Potion =
        Registry.register(Registries.POTION, name, Potion(effectInstance)).also {
            if (recipe != null) {
                IBrewingRecipesMixin.registerPotionRecipe(recipe.first, recipe.second, it)
            }
        }

    fun <T : ScreenHandler> screen(name: String, factory: (Int, PlayerInventory) -> T): ScreenHandlerType<T> =
        Registry.register(Registries.SCREEN_HANDLER, name, ScreenHandlerType(factory, net.minecraft.resource.featuretoggle.FeatureFlags.VANILLA_FEATURES))

    fun <T : ScreenHandler> screenHandler(screen: ScreenHandlerType<T>, factory: (T, PlayerInventory, Text) -> HandledScreen<T>) {
        HandledScreens.register(screen) { handler, playerInventory, title -> factory(handler, playerInventory, title) }
    }

    fun statusEffect(name: String, statusEffect: StatusEffect): StatusEffect =
        Registry.register(Registries.STATUS_EFFECT, name, statusEffect)

}
