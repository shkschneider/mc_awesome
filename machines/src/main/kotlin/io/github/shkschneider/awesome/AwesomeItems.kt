package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.core.AwesomeRegistries
import io.github.shkschneider.awesome.custom.Minecraft
import io.github.shkschneider.awesome.custom.RedstoneFlux
import io.github.shkschneider.awesome.items.AwesomeMaterials
import io.github.shkschneider.awesome.items.AwesomeOres
import io.github.shkschneider.awesome.items.Imprisoner
import io.github.shkschneider.awesome.items.Prospector
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.Items
import net.minecraft.util.Rarity

object AwesomeItems {

    private val settings = FabricItemSettings().maxCount(Minecraft.STACK).group(Awesome.GROUP).rarity(Rarity.COMMON)

    object Coal {

        private const val ID = "coal"
        const val COLOR = 0x464646

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = AwesomeOres.Dust(ID, settings)
        val powder = AwesomeOres.Powder(ID, settings)

        operator fun invoke() = Unit

    }

    object Copper {

        private const val ID = "copper"
        const val COLOR = 0xFF6400

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = AwesomeOres.Dust(ID, settings)
        val powder = AwesomeOres.Powder(ID, settings)
        val ingot = Items.COPPER_INGOT

        val frame = AwesomeMaterials.Frame(ID, settings)
        val plate = AwesomeMaterials.Plate(ID, settings)
        val rod = AwesomeMaterials.Rod(ID, settings)

        operator fun invoke() = Unit

    }

    object Diamond {

        private const val ID = "diamond"
        const val COLOR = 0xC8FFFF

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = AwesomeOres.Dust(ID, settings)
        val powder = AwesomeOres.Powder(ID, settings)

        operator fun invoke() = Unit

    }

    object Emerald {

        private const val ID = "emerald"
        const val COLOR = 0x50FF50

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = AwesomeOres.Dust(ID, settings)
        val powder = AwesomeOres.Powder(ID, settings)

        operator fun invoke() = Unit

    }

    object Gold {

        private const val ID = "gold"
        const val COLOR = 0xFFFF1E

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = AwesomeOres.Dust(ID, settings)
        val powder = AwesomeOres.Powder(ID, settings)

        operator fun invoke() = Unit

    }

    object Iron {

        private const val ID = "iron"
        const val COLOR = 0xC8C8C8

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = AwesomeOres.Dust(ID, settings)
        val powder = AwesomeOres.Powder(ID, settings)

        operator fun invoke() = Unit

    }

    object Lapis {

        private const val ID = "lapis"
        const val COLOR = 0x4646DC

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = AwesomeOres.Dust(ID, settings)
        val powder = AwesomeOres.Powder(ID, settings)
        val ingot = AwesomeOres.Ingot(ID, settings)

        val frame = AwesomeMaterials.Frame(ID, settings)
        val plate = AwesomeMaterials.Plate(ID, settings)
        val rod = AwesomeMaterials.Rod(ID, settings)

        operator fun invoke() = Unit

    }

    object Netherite {

        private const val ID = "netherite"
        const val COLOR = 0x4B484B

    }

    object Quartz {

        private const val ID = "quartz"
        const val COLOR = 0xE6D2D2

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = AwesomeOres.Dust(ID, settings)
        val powder = AwesomeOres.Powder(ID, settings)

        operator fun invoke() = Unit

    }

    object Redstone {

        private const val ID = "redstone"
        const val COLOR = 0xC80000

        val flux = RedstoneFlux()

        val chip = AwesomeOres.Chip(ID, settings)
        val dust = Items.REDSTONE // "wire"
        val powder = AwesomeOres.Powder(ID, settings)
        val ingot = AwesomeOres.Ingot(ID, settings)

        val frame = AwesomeMaterials.Frame(ID, settings)
        val plate = AwesomeMaterials.Plate(ID, settings)
        val rod = AwesomeMaterials.Rod(ID, settings)

        operator fun invoke() {
            if (Awesome.CONFIG.machines.redstoneFluxAsVanillaFuel) {
                // AbstractFurnaceBlockEntity.createFuelTimeMap()
                AwesomeRegistries.fuel(flux, flux.time)
            }
        }

    }

    //endregion

    //endregion

    operator fun invoke() {
        Copper()
        Coal()
        Diamond()
        Emerald()
        Gold()
        Iron()
        Lapis()
        Quartz()
        Redstone()
        if (Awesome.CONFIG.machines.imprisoner) Imprisoner()
        if (Awesome.CONFIG.machines.prospector) Prospector()
    }

}
