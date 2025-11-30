package io.github.shkschneider.awesome.extras.entities

import io.github.shkschneider.awesome.core.AwesomeSounds
import io.github.shkschneider.awesome.core.ext.isBeingLookedAt
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.extras.entities.goals.AngerGoal
import io.github.shkschneider.awesome.extras.entities.goals.StareAtPlayerGoal
import io.github.shkschneider.awesome.extras.entities.goals.SwiftAttackAndVanishGoal
import net.minecraft.command.argument.EntityAnchorArgumentType
import net.minecraft.entity.EntityDimensions
import net.minecraft.entity.EntityPose
import net.minecraft.entity.EntityType
import net.minecraft.entity.ai.TargetPredicate
import net.minecraft.entity.attribute.DefaultAttributeContainer
import net.minecraft.entity.damage.DamageSource
import net.minecraft.entity.effect.StatusEffectInstance
import net.minecraft.entity.effect.StatusEffects
import net.minecraft.entity.mob.HostileEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundCategory
import net.minecraft.sound.SoundEvent
import net.minecraft.sound.SoundEvents
import net.minecraft.world.World

class Herobrine(entityType: EntityType<out HostileEntity>, world: World) : HostileEntity(entityType, world) {

    companion object {

        const val ID = "herobrine"
        val SIZE = 1.0F to 2.0F
        val RANGE = Minecraft.CHUNK

        fun attributes(): DefaultAttributeContainer.Builder = createHostileAttributes()

        fun spawnRules(entityType: EntityType<Herobrine>) {
            /*BiomeModifications.addSpawn(BiomeSelectors.foundInOverworld(), SpawnGroup.MONSTER, entityType, 50, 1, 1)
            SpawnRestriction.register(entityType, SpawnRestriction.Location.ON_GROUND, Heightmap.Type.WORLD_SURFACE) { type, world, spawnReason, pos, random -> // 1.19
                world.toServerWorld().isRaining && canSpawnIgnoreLightLevel(type, world, spawnReason, pos, random)
            }*/
        }

    }

    override fun initGoals() {
        goalSelector.apply {
            add(1, SwiftAttackAndVanishGoal(this@Herobrine, RANGE))
            add(2, AngerGoal(this@Herobrine, RANGE * 2))
            add(3, StareAtPlayerGoal(this@Herobrine/*, RANGE * 3 */))
        }
    }

    override fun canAvoidTraps(): Boolean = true
    override fun canBeLeashedBy(player: PlayerEntity): Boolean = false
    override fun canBeRiddenInWater(): Boolean = false
    override fun canBreatheInWater(): Boolean = true
    override fun canFreeze(): Boolean = false
    override fun canHaveStatusEffect(effect: StatusEffectInstance): Boolean = false
    // 1.19 override fun canHit(): Boolean = true
    override fun canPickupItem(stack: ItemStack): Boolean = false
    override fun canTarget(type: EntityType<*>): Boolean = type == EntityType.PLAYER
    override fun canUsePortals(): Boolean = false
    override fun getActiveEyeHeight(pose: EntityPose, dimensions: EntityDimensions): Float = 1.62F
    override fun getDeathSound(): SoundEvent = SoundEvents.ENTITY_WITHER_SPAWN
    override fun getHurtSound(source: DamageSource): SoundEvent = SoundEvents.ENTITY_WITHER_HURT
    override fun getMaxHeadRotation(): Int = 90
    override fun getMaxLookPitchChange(): Int = 90
    override fun getMaxLookYawChange(): Int = 90
    // 1.19 override fun getXpToDrop(): Int = 0
    override fun isAttackable(): Boolean = false
    override fun isCollidable(): Boolean = true
    override fun isDisallowedInPeaceful(): Boolean = true
    override fun isFireImmune(): Boolean = true
    override fun isImmuneToExplosion(): Boolean = true
    override fun hurtByWater(): Boolean = false

    override fun tick() {
        target = world.getClosestPlayer(TargetPredicate.createAttackable(), this, x, eyeY, z)?.takeIf { it.isAlive }
        super.tick()
    }

    override fun onPlayerCollision(player: PlayerEntity) {
        super.onPlayerCollision(player)
        player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, this.eyePos)
        player.addStatusEffect(StatusEffectInstance(StatusEffects.LEVITATION, Minecraft.TICKS), this)
        AwesomeSounds(this.world to player.blockPos, SoundEvents.ENTITY_WITHER_SPAWN to SoundCategory.HOSTILE)
        // 1.19 player.addStatusEffect(StatusEffectInstance(StatusEffects.DARKNESS, Minecraft.TICKS * 15), this)
        player.addStatusEffect(StatusEffectInstance(StatusEffects.POISON, Minecraft.TICKS * 15), this)
        kill()
    }

    override fun cannotDespawn(): Boolean {
        return target?.let { isBeingLookedAt(it) } ?: false
    }

}
