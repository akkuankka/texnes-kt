package es.headbe.texnes.registry.items

import es.headbe.texnes.gui.PressureGaugeGui
import es.headbe.texnes.gui.PressureGaugeScreen
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class PressureGauge(settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        MinecraftClient.getInstance().setScreen(PressureGaugeScreen(PressureGaugeGui()))
        return super.use(world, user, hand)
    }
}