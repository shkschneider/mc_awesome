package io.github.shkschneider.awesome

import com.google.gson.annotations.SerializedName

data class AwesomeConfig(
    @SerializedName("commands") val commands: Commands = Commands(),
    @SerializedName("crystals") val crystals: Crystals = Crystals(),
    @SerializedName("enchantments") val enchantments: Enchantments = Enchantments(),
    @SerializedName("experience") val experience: Experience = Experience(),
    @SerializedName("extras") val extras: Extras = Extras(),
    @SerializedName("machines") val machines: Machines = Machines(),
) {

    @SerializedName("dimensions") val dimensions: Boolean = false

    data class Commands(
        @SerializedName("back") val back: Boolean = true,
        @SerializedName("backUponDeath") val backUponDeath: Boolean = false,
        @SerializedName("broadcast") val broadcast: Boolean = true,
        @SerializedName("enderChest") val enderChest: Boolean = true,
        @SerializedName("fly") val fly: Boolean = true,
        @SerializedName("heal") val heal: Boolean = true,
        @SerializedName("home") val home: Boolean = true,
        @SerializedName("inventory") val inventory: Boolean = false,
        @SerializedName("invulnerable") val invulnerable: Boolean = true,
        @SerializedName("repair") val repair: Boolean = true,
        @SerializedName("spawn") val spawn: Boolean = true,
        @SerializedName("top") val top: Boolean = true,
    )

    data class Crystals(
        @SerializedName("coal") val coal: Int = 4,
        @SerializedName("copper") val copper: Int = 2,
        @SerializedName("diamond") val diamond: Int = 1,
        @SerializedName("emerald") val emerald: Int = 1,
        @SerializedName("ender") val ender: Int = 1,
        @SerializedName("glowstone") val glowstone: Int = 2,
        @SerializedName("gold") val gold: Int = 2,
        @SerializedName("iron") val iron: Int = 2,
        @SerializedName("lapis") val lapis: Int = 4,
        @SerializedName("netherite") val netherite: Int = 1,
        @SerializedName("quartz") val quartz: Int = 2,
        @SerializedName("redstone") val redstone: Int = 4,
    )

    data class Enchantments(
        @SerializedName("critical") val critical: Boolean = true,
        @SerializedName("infinity") val infinity: Boolean = true,
        @SerializedName("lastStand") val lastStand: Boolean = true,
        @SerializedName("magnetism") val magnetism: Boolean = true,
        @SerializedName("sixthSense") val sixthSense: Boolean = true,
        @SerializedName("unbreakable") val unbreakable: Boolean = true,
        @SerializedName("vampirism") val vampirism: Boolean = true,
        @SerializedName("veinMining") val veinMining: Boolean = true,
    )

    data class Experience(
        @SerializedName("obelisk") val obelisk: Boolean = true,
        @SerializedName("oneXpPerBlock") val oneXpPerBlock: Boolean = true,
        @SerializedName("potions") val potions: Boolean = true,
    )

    data class Extras(
        @SerializedName("allInOneTools") val allInOneTools: Boolean = true,
        @SerializedName("baguette") val baguette: Boolean = true,
        @SerializedName("crates") val crates: Boolean = true,
        @SerializedName("elevator") val elevator: Boolean = true,
        @SerializedName("entities") val entities: Boolean = false,
        @SerializedName("lilypad") val lilypad: Boolean = true,
        @SerializedName("nadirLengthInDays") val nadirLengthInDays: Float = 0F,
        @SerializedName("omelette") val omelette: Boolean = true,
        @SerializedName("pvp") val pvp: Boolean = true,
        @SerializedName("randomium") val randomium: Boolean = true,
        @SerializedName("rope") val rope: Boolean = true,
        @SerializedName("scythe") val scythe: Boolean = true,
        @SerializedName("sleepingHeals") val sleepingHeals: Boolean = true,
        @SerializedName("spongesInLava") val spongesInLava: Boolean = true,
        @SerializedName("totemFromInventory") val totemFromInventory: Boolean = true,
        @SerializedName("trashSlot") val trashSlot: Boolean = true,
        @SerializedName("unlockRecipes") val unlockRecipes: Boolean = true,
        @SerializedName("villagersFollowEmeraldBlock") val villagersFollowEmeraldBlock: Boolean = true,
        @SerializedName("villagersInfiniteTrading") val villagersInfiniteTrading: Boolean = true,
        @SerializedName("void") val void: Boolean = true,
        @SerializedName("zenithLengthInDays") val zenithLengthInDays: Float = 0.5F,
    )

    data class Machines(
        @SerializedName("crafter") val crafter: Boolean = true,
        @SerializedName("duplicator") val duplicator: Boolean = true,
        @SerializedName("factory") val factory: Boolean = true,
        @SerializedName("imprisoner") val imprisoner: Boolean = true,
        @SerializedName("prospector") val prospector: Boolean = true,
        @SerializedName("quarry") val quarry: Boolean = true,
        @SerializedName("recycler") val recycler: Boolean = true,
    )

}
