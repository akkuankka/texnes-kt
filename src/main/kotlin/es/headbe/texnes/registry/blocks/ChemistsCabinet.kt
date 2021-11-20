package es.headbe.texnes.registry.blocks

import es.headbe.texnes.util.blockEntityType
import es.headbe.texnes.util.ident
import net.fabricmc.fabric.api.`object`.builder.v1.block.entity.FabricBlockEntityTypeBuilder
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.*
import net.minecraft.block.entity.BlockEntity
import net.minecraft.block.entity.BlockEntityTicker
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.nbt.NbtCompound
import net.minecraft.sound.BlockSoundGroup
import net.minecraft.util.math.BlockPos
import net.minecraft.util.registry.Registry
import net.minecraft.world.World
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings as FabricBlockSettings

class ChemistsCabinet() : BlockWithEntity(
    FabricBlockSettings.of(Material.DECORATION)
        .strength( 0.5f )
        .sounds(BlockSoundGroup.WOOD)
), BlockEntityProvider {

    override fun createBlockEntity(pos: BlockPos, state: BlockState): BlockEntity {
        return Entity(pos, state)
    }

    override fun getRenderType(state: BlockState): BlockRenderType {
        return BlockRenderType.MODEL
    }

    override fun <T : BlockEntity> getTicker(
        world: World,
        state: BlockState,
        eType: BlockEntityType<T>
    ): BlockEntityTicker<T>? {
        return checkType(eType, Entity.type) { w, pos, s, be ->
            Entity.tick(w, pos, state, be)
        }
    }

    class Entity(pos: BlockPos, state: BlockState) : BlockEntity(type, pos, state) {
        private var number = 7


        override fun writeNbt(tag: NbtCompound): NbtCompound {
            super.writeNbt(tag)
            tag.putInt("number", number)
            return tag
        }

        override fun readNbt(nbt: NbtCompound) {
            super.readNbt(nbt)
            number = nbt.getInt("number")
        }

        companion object {
            lateinit var type: BlockEntityType<Entity>

            fun tick(world: World, pos: BlockPos, state: BlockState, be: Entity) {
                ++be.number
            }

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