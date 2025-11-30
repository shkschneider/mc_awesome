package io.github.shkschneider.awesome.items

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.core.AwesomeItem
import io.github.shkschneider.awesome.core.AwesomeSounds
import io.github.shkschneider.awesome.core.AwesomeUtils
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.mob.Monster
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Formatting
import net.minecraft.util.Hand
import net.minecraft.util.Rarity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

class Imprisoner : AwesomeItem(
    id = AwesomeUtils.identifier(ID),
    settings = FabricItemSettings().group(Awesome.GROUP).maxCount(1).rarity(Rarity.UNCOMMON),
    group = Awesome.GROUP,
) {

    companion object {

        const val ID = "imprisoner"
        const val IMPRISONED = "Imprisoned"
        private val EXPERIENCE = 1
        val COOLDOWN = AwesomeUtils.secondsToTicks(1)

    }

    private fun isEmpty(stack: ItemStack): Boolean =
        stack.nbt?.get(IMPRISONED)?.asString() == null

    override fun hasGlint(stack: ItemStack): Boolean {
        return isEmpty(stack).not()
    }

    override fun appendTooltip(stack: ItemStack, world: World?, tooltip: MutableList<Text>, context: TooltipContext) {
        super.appendTooltip(stack, world, tooltip, context)
        stack.nbt?.get(IMPRISONED)?.asString()?.let { prisoner ->
            tooltip.add(Text.of(prisoner))
        } ?: run {
            tooltip.add(TranslatableText(AwesomeUtils.translatable("item", ID, "hint")).formatted(Formatting.GRAY))
        }
    }

    //region capture

    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        if (user.world.isClient) return ActionResult.PASS
        if (isEmpty(stack).not()) return ActionResult.FAIL
        if (user.isCreative.not() && user.experienceLevel < EXPERIENCE) return ActionResult.FAIL
        if (entity is Monster || entity.isBaby || entity.isDead || entity.isUndead) return ActionResult.FAIL
        if (entity is PlayerEntity || entity.isAttackable.not()) return ActionResult.FAIL
        return if (capture(user, hand, stack, entity)) {
            user.itemCooldownManager.set(this, COOLDOWN)
            ActionResult.CONSUME
        } else {
            ActionResult.FAIL
        }
    }

    private fun capture(player: PlayerEntity, hand: Hand, stack: ItemStack, entity: LivingEntity): Boolean {
        player.swingHand(hand)
        stack.nbt = entity.writeNbt(NbtCompound()).apply {
            putString(IMPRISONED, entity.type.registryEntry.registryKey().value.toString())
            remove("Pos")
        }
        // I probably got something wrong about server<>client sync
        player.mainHandStack.nbt = stack.nbt.also { player.inventory.markDirty() }
        player.addExperienceLevels(-EXPERIENCE)
        entity.remove(Entity.RemovalReason.KILLED)
        AwesomeSounds(player.world to entity.blockPos, AwesomeSounds.teleport)
        return true
    }

    //endregion

    //region release

    override fun useOnBlock(context: ItemUsageContext): ActionResult {
        if (context.world.isClient) return ActionResult.PASS
        context.player?.swingHand(context.hand) ?: return ActionResult.PASS
        if (isEmpty(context.stack)) return ActionResult.FAIL
        return when {
            context.world !is ServerWorld -> ActionResult.PASS
            release(context.world as ServerWorld, context.player, context.blockPos, context.stack) -> {
                context.player?.itemCooldownManager?.set(this, COOLDOWN)
                ActionResult.CONSUME
            }
            else -> ActionResult.FAIL
        }
    }

    fun spawn(world: ServerWorld, pos: BlockPos, nbt: NbtCompound?): Boolean {
        val id = nbt?.getString(IMPRISONED) ?: return false
        val entityType = EntityType.get(id).takeIf { it.isPresent }?.get() ?: return false
        // FIXME loosing any CustomName
        entityType.spawn(world, nbt, null, null, pos, SpawnReason.SPAWN_EGG, true, false)?.apply {
            (this as? LivingEntity)?.apply {
                setVelocity(0.toDouble(), 0.toDouble(), 0.toDouble())
            }
        }.also {
            world.emitGameEvent(null, GameEvent.ENTITY_PLACE, pos)
        }
        return true
    }

    private fun release(world: ServerWorld, player: PlayerEntity?, pos: BlockPos, stack: ItemStack): Boolean {
        return if (spawn(world, pos, stack.nbt)) {
            player?.mainHandStack?.nbt = NbtCompound().also { player?.inventory?.markDirty() }
            AwesomeSounds(world to pos, AwesomeSounds.teleport)
            true
        } else {
            false
        }
    }

    //endregion

}
