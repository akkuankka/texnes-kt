package es.headbe.texnes.registry.items

import net.minecraft.entity.player.PlayerEntity
import net.minecraft.item.Item
import net.minecraft.item.ItemStack
import net.minecraft.sound.SoundEvents
import net.minecraft.util.Hand
import net.minecraft.util.TypedActionResult
import net.minecraft.world.World

class CrystalsItem(settings: Settings) : Item(settings) {


    override fun use(world: World, playerEntity: PlayerEntity, hand: Hand): TypedActionResult<ItemStack> {
        playerEntity.playSound(SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, 0.9f, 1.0f)
        return TypedActionResult.success(playerEntity.getStackInHand(hand))
    }
}