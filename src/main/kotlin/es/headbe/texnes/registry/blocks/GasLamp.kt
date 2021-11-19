package es.headbe.texnes.registry.blocks

import es.headbe.texnes.util.blockBenchVoxels
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.block.Material
import net.minecraft.block.ShapeContext
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.sound.SoundEvents
import net.minecraft.state.StateManager
import net.minecraft.state.property.BooleanProperty
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.shape.VoxelShape
import net.minecraft.util.shape.VoxelShapes
import net.minecraft.world.BlockView
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

    override fun getOutlineShape(
        state: BlockState,
        world: BlockView,
        pos: BlockPos,
        context: ShapeContext
    ): VoxelShape {
        return voxelShape
    }

    companion object {
        val on: BooleanProperty = BooleanProperty.of("lamp_on")
        val voxelShape = VoxelShapes.union(
            blockBenchVoxels(4.0, 3.0, 5.0, 1.0, 2.0, 1.0),
            blockBenchVoxels(6.25, 5.0, 7.5, 1.0, 1.0, 1.0),
            blockBenchVoxels(7.0, 3.0, 7.0, 2.0, 2.0, 2.0),
            blockBenchVoxels(3.5, 5.0, 4.5, 1.0, 3.0, 1.0),
            blockBenchVoxels(3.0, 8.0, 4.0, 1.0, 3.0, 1.0),
            blockBenchVoxels(11.0, 3.0, 5.0, 1.0, 2.0, 1.0),
            blockBenchVoxels(12.0, 8.0, 4.0, 1.0, 3.0, 1.0),
            blockBenchVoxels(11.5, 5.0, 4.5, 1.0, 3.0, 1.0),
            blockBenchVoxels(4.0, 3.0, 10.0, 1.0, 2.0, 1.0),
            blockBenchVoxels(3.5, 5.0, 10.5, 1.0, 3.0, 1.0),
            blockBenchVoxels(3.0, 8.0, 11.0, 1.0, 3.0, 1.0),
            blockBenchVoxels(11.0, 3.0, 10.0, 1.0, 2.0, 1.0),
            blockBenchVoxels(12.0, 8.0, 11.0, 1.0, 3.0, 1.0),
            blockBenchVoxels(11.5, 5.0, 10.5, 1.0, 3.0, 1.0),
            blockBenchVoxels(3.0, 2.0, 4.0, 10.0, 1.0, 8.0),
            blockBenchVoxels(6.0, 0.0, 6.0, 4.0, 1.0, 4.0),
            blockBenchVoxels(2.0, 11.0, 3.0, 12.0, 1.0, 10.0),
            blockBenchVoxels(7.5, 15.0, 7.5, 1.0, 1.0, 1.0),
            blockBenchVoxels(6.0, 14.0, 6.0, 4.0, 1.0, 4.0),
            blockBenchVoxels(4.0, 13.0, 5.0, 8.0, 1.0, 6.0),
            blockBenchVoxels(1.0, 12.0, 2.0, 14.0, 1.0, 12.0),

        )
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