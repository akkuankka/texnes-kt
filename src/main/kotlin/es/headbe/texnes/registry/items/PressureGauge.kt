package es.headbe.texnes.registry.items

import es.headbe.texnes.gui.PressureGaugeGui
import es.headbe.texnes.gui.PressureGaugeScreen
import es.headbe.texnes.util.networking.NetworkingConstants
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking
import net.minecraft.client.MinecraftClient
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.network.PacketByteBuf
import net.minecraft.server.network.ServerPlayerEntity
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class PressureGauge(settings: Settings) : Item(settings) {

    override fun use(world: World, user: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        if (world.isClient()) return super.use(world, user, hand)

        // otherwise we're on the server

        // TODO get the pressure of the pipe that's being looked at
        ServerPlayNetworking.send(user as ServerPlayerEntity, NetworkingConstants.PRESSURE_GAUGE_PKID, PacketByteBufs.empty())
//        MinecraftClient.getInstance().setScreen(PressureGaugeScreen(PressureGaugeGui()))
        return TypedActionResult.success(user.getStackInHand(hand))
    }
}