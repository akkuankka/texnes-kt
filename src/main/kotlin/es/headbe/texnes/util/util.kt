package es.headbe.texnes.util

import net.minecraft.util.Identifier
import es.headbe.texnes.Texnes
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry


// copied from https://github.com/GabrielOlvH/Industrial-Revolution
fun ident(id: String): Identifier {
    return Identifier(Texnes.MOD_ID, id)
}

fun Identifier.block(block: Block): Identifier {
    Registry.register(Registry.BLOCK, this, block)
    return this
}

fun Identifier.fluid(fluid: Fluid): Identifier {
    Registry.register(Registry.FLUID, this, fluid)
    return this
}

fun Identifier.item(item: Item): Identifier {
    Registry.register(Registry.ITEM, this, item)
    return this
}

fun Identifier.blockEntityType(entityType: BlockEntityType<*>): Identifier {
    Registry.register(Registry.BLOCK_ENTITY_TYPE, this, entityType)
    return this
}
