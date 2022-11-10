package io.github.shkschneider.awesome.items

import io.github.shkschneider.awesome.Awesome
import io.github.shkschneider.awesome.AwesomeUtils
import io.github.shkschneider.awesome.core.AwesomeItem
import io.github.shkschneider.awesome.custom.AwesomeSounds
import io.github.shkschneider.awesome.effects.AwesomeEffects
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.client.item.TooltipContext
import net.minecraft.entity.Entity
import net.minecraft.entity.EntityType
import net.minecraft.entity.LivingEntity
import net.minecraft.entity.SpawnReason
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.item.ItemUsageContext
import net.minecraft.nbt.NbtCompound
import net.minecraft.server.world.ServerWorld
import net.minecraft.text.Text
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.Rarity
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World
import net.minecraft.world.event.GameEvent

class Imprisoner : AwesomeItem(
    id = AwesomeUtils.identifier("imprisoner"),
    settings = FabricItemSettings().group(Awesome.GROUP).maxCount(1).rarity(Rarity.UNCOMMON),
) {

    companion object {

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
        stack.nbt?.get(IMPRISONED)?.asString()?.let {
            tooltip.add(Text.of(it))
        }
    }

    //region capture

    override fun useOnEntity(stack: ItemStack, user: PlayerEntity, entity: LivingEntity, hand: Hand): ActionResult {
        if (user.world.isClient) return ActionResult.PASS
        if (isEmpty(stack).not()) return ActionResult.FAIL
        if (user.isCreative.not() && user.experienceLevel < EXPERIENCE) return ActionResult.FAIL
        if (entity.isDead || entity.isUndead) return ActionResult.FAIL
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

    private fun release(world: ServerWorld, player: PlayerEntity?, pos: BlockPos, stack: ItemStack): Boolean {
        val id = stack.nbt?.getString(IMPRISONED) ?: return false
        val entityType = EntityType.get(id).takeIf { it.isPresent }?.get() ?: return false
        // FIXME loosing any CustomName
        val entity = entityType.spawn(world, stack.nbt, null, player, pos, SpawnReason.SPAWN_EGG, true, false)?.apply {
            (this as? LivingEntity)?.apply {
                addStatusEffect(StatusEffectInstance(AwesomeEffects.paralysis, 1))
            }
        }
        return if (entity != null) {
            player?.mainHandStack?.nbt = NbtCompound().also { player?.inventory?.markDirty() }
            AwesomeSounds(world to entity.blockPos, AwesomeSounds.teleport)
            world.emitGameEvent(player, GameEvent.ENTITY_PLACE, pos)
            true
        } else {
            false
        }
    }

    //endregion

}
