package es.headbe.texnes.registry.blocks

import es.headbe.texnes.util.ident
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.inventory.Inventories
import net.minecraft.inventory.Inventory
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
import net.minecraft.util.ItemScatterer
import net.minecraft.util.collection.DefaultedList
import net.minecraft.util.hit.BlockHitResult
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.Direction
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings as FabricBlockSettings

class ChemistsCabinet : HorizontalFacingBlock(
    FabricBlockSettings.of(Material.DECORATION)
        .strength( 0.5f )
        .sounds(BlockSoundGroup.WOOD)
), BlockEntityProvider {

    init {
        defaultState = this.stateManager.defaultState
            .with(Properties.HORIZONTAL_FACING, Direction.NORTH)
    }

    override fun appendProperties(stateManager: StateManager.Builder<Block, BlockState>) {
        stateManager.add(Properties.HORIZONTAL_FACING)
    }

    override fun getPlacementState(ctx: ItemPlacementContext): BlockState =
        this.defaultState.with(Properties.HORIZONTAL_FACING, ctx.playerFacing.opposite)

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return Entity(pos, state)
    }

    override fun getRenderType(state: BlockState): BlockRenderType = BlockRenderType.MODEL

/*
    override fun onStateReplaced(state: BlockState, world: World, pos: BlockPos, newState: BlockState, moved: Boolean) {
        if (! state.isOf(newState.block)) {
            when (val blockEntity = world.getBlockEntity(pos)) {
                is Inventory -> {
                    ItemScatterer.spawn(world, pos, blockEntity)
                    world.updateComparators(pos, this)
                }
                else -> {}
            }
            super.onStateReplaced(state, world, pos, newState, moved)
        }
    }
*/


    override fun onUse(
        state: BlockState,
        world: World,
        pos: BlockPos,
        player: PlayerEntity,
        hand: Hand,
        hit: BlockHitResult
    ): ActionResult {
        if (world.isClient) return ActionResult.SUCCESS
        val blockEntity = world.getBlockEntity(pos) as ImplementedInventory
        if (!player.getStackInHand(hand).isEmpty) {
            if (blockEntity.getStack(0).isEmpty) {
                blockEntity.setStack(0, player.getStackInHand(hand))
                player.getStackInHand(hand).count = 0
            } else if (blockEntity.getStack(1).isEmpty) {
                blockEntity.setStack(1, player.getStackInHand(hand).copy())
                player.getStackInHand(hand).count = 0
            } else {
                println("the first slot holds " +
                blockEntity.getStack(0) + " and the second slot holds " + blockEntity.getStack(0) + ".")
            }
        } else {
           if (!blockEntity.getStack(1).isEmpty) {
               player.inventory.offerOrDrop(blockEntity.getStack(1))
               blockEntity.removeStack(1)
           } else if (!blockEntity.getStack(0).isEmpty) {
               player.inventory.offerOrDrop(blockEntity.getStack(0))
               blockEntity.removeStack(0)
           }
        }


        return ActionResult.SUCCESS
    }


    class Entity(pos: BlockPos, state: BlockState) :
        BlockEntity(type, pos, state),
        ImplementedInventory

    {
        override var items: DefaultedList<ItemStack> = DefaultedList.ofSize(2, ItemStack.EMPTY)


        override fun writeNbt(nbt: NbtCompound): NbtCompound {
            Inventories.writeNbt(nbt, items)
            return super.writeNbt(nbt)
        }

        override fun readNbt(nbt: NbtCompound) {
            super.readNbt(nbt)
            Inventories.readNbt(nbt, items)
        }

        override fun markDirty() {
            super<ImplementedInventory>.markDirty()
        }

        override fun canPlayerUse(player: PlayerEntity): Boolean {
            return pos.isWithinDistance(player.blockPos, 4.5)
        }


        companion object {
            lateinit var type: BlockEntityType<Entity>

            fun initType(block: Block) {
                type = Registry.register(
                    Registry.BLOCK_ENTITY_TYPE,
                    ident("chemists_cabinet_entity"),
                    FabricBlockEntityTypeBuilder.create(::Entity, block).build(null)
                )
            }
        }

    }

}