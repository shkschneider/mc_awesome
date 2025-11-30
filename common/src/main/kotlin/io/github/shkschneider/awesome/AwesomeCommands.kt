package io.github.shkschneider.awesome

import io.github.shkschneider.awesome.commands.BackCommand
import io.github.shkschneider.awesome.commands.BroadcastCommand
import io.github.shkschneider.awesome.commands.EnderChestCommand
import io.github.shkschneider.awesome.commands.FlyCommand
import io.github.shkschneider.awesome.commands.HealCommand
import io.github.shkschneider.awesome.commands.HomeCommand
import io.github.shkschneider.awesome.commands.InventoryCommand
import io.github.shkschneider.awesome.commands.InvulnerableCommand
import io.github.shkschneider.awesome.commands.MooCommand
import io.github.shkschneider.awesome.commands.RegionCommand
import io.github.shkschneider.awesome.commands.RepairCommand
import io.github.shkschneider.awesome.commands.SetHomeCommand
import io.github.shkschneider.awesome.commands.SpawnCommand
import io.github.shkschneider.awesome.commands.TopCommand

object AwesomeCommands {

    operator fun invoke() {
        if (Awesome.CONFIG.commands.back) BackCommand()
        if (Awesome.CONFIG.commands.broadcast) BroadcastCommand()
        if (Awesome.CONFIG.commands.enderChest) EnderChestCommand()
        if (Awesome.CONFIG.commands.fly) FlyCommand()
        if (Awesome.CONFIG.commands.heal) HealCommand()
        if (Awesome.CONFIG.commands.home) {
            HomeCommand()
            SetHomeCommand()
        }
        if (Awesome.CONFIG.commands.inventory) InventoryCommand()
        if (Awesome.CONFIG.commands.invulnerable) InvulnerableCommand()
        if (Awesome.CONFIG.commands.repair) RepairCommand()
        if (Awesome.CONFIG.commands.spawn) SpawnCommand()
        if (Awesome.CONFIG.commands.top) TopCommand()
        MooCommand()
        RegionCommand()
    }

}
