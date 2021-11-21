package es.headbe.texnes.registry.blocks

import es.headbe.texnes.gui.ChemistsCabinetGui
import es.headbe.texnes.util.ident
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.block.entity.LootableContainerBlockEntity
import net.minecraft.entity.player.PlayerEntity
import net.minecraft.entity.player.PlayerInventory
import net.minecraft.inventory.Inventories
import net.minecraft.item.ItemPlacementContext
import net.minecraft.item.ItemStack
import net.minecraft.nbt.NbtCompound
import net.minecraft.screen.NamedScreenHandlerFactory
import net.minecraft.screen.ScreenHandler
import net.minecraft.screen.ScreenHandlerContext
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.state.StateManager
import net.minecraft.state.property.Properties
import net.minecraft.text.Text
import net.minecraft.text.TranslatableText
import net.minecraft.util.ActionResult
import net.minecraft.util.Hand
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

    override fun createScreenHandlerFactory(
        state: BlockState?,
        world: World?,
        pos: BlockPos
    ): NamedScreenHandlerFactory? {
        when (val be = world?.getBlockEntity(pos)) {
            null -> return null
            is NamedScreenHandlerFactory -> return be
            else -> return null
        }
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
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos))

        return ActionResult.SUCCESS
    }


    class Entity(pos: BlockPos, state: BlockState) :
        LootableContainerBlockEntity(type, pos, state),
        ImplementedInventory
    {
        override var items: DefaultedList<ItemStack> = DefaultedList.ofSize(NUM_SLOTS, ItemStack.EMPTY)

        override fun getContainerName(): Text = TranslatableText(cachedState.block.translationKey)
        override fun clear() {
            super<ImplementedInventory>.clear()
        }

        override fun isEmpty(): Boolean {
            return super<ImplementedInventory>.isEmpty()
        }

        override fun getStack(slot: Int): ItemStack {
            return super<ImplementedInventory>.getStack(slot)
        }

        override fun removeStack(slot: Int): ItemStack {
            return super<ImplementedInventory>.removeStack(slot)
        }

        override fun removeStack(slot: Int, count: Int): ItemStack {
            return super<ImplementedInventory>.removeStack(slot, count)
        }

        override fun setStack(slot: Int, stack: ItemStack) {
            super<ImplementedInventory>.setStack(slot, stack)
        }

        override fun getInvStackList(): DefaultedList<ItemStack> = items
        override fun setInvStackList(list: DefaultedList<ItemStack>?) {
           list?.let {items = it}
        }

        override fun createScreenHandler(syncId: Int, inventory: PlayerInventory): ScreenHandler =
            ChemistsCabinetGui(syncId, inventory, ScreenHandlerContext.create(world, pos))

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

            const val NUM_SLOTS: Int = 18

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