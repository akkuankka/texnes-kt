package es.headbe.texnes.registry.blocks

import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.world.World

class GasLamp : Block(Settings.of(Material.DECORATION).strength(0.5f).sounds(BlockSoundGroup.METAL).luminance {
    when (it.get(on)) {
        true -> 15
        false -> 0
    }
}) {
    init {
        defaultState = getStateManager().defaultState.with(on, false)
    }

    protected override fun appendProperties(stateManager: StateManager.Builder<Block, BlockState>) {
        stateManager.add(on)
    }

    companion object {
        val on: BooleanProperty = BooleanProperty.of("lamp_on")
    }

    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        player.playSound(SoundEvents.BLOCK_BEEHIVE_ENTER, 0.5f, 2f)
        this.toggleLight(state, world, pos)
        return ActionResult.SUCCESS
    }

    private fun toggleLight(state: BlockState, world: World, pos: BlockPos) {
        if (! state.get(on)) {
            world.setBlockState(pos, state.with(on, true))
        } else {
            world.setBlockState(pos, state.with(on, false))
        }
    }
}