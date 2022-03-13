package es.headbe.texnes.util

import net.minecraft.util.Identifier
import es.headbe.texnes.Texnes
import net.minecraft.block.Block
import net.minecraft.block.entity.BlockEntityType
import net.minecraft.fluid.Fluid
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import net.minecraft.util.shape.VoxelShape


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

fun blockBenchVoxels(x: Double, y:Double, z: Double, dx: Double, dy: Double, dz: Double): VoxelShape {
    return Block.createCuboidShape(
        x, y, z, x + dx, y + dy, z + dz
    )

}

fun<T, U> T?.map(f: (T) -> U): U? = if (this == null) {
    null
} else {
    f(this)
}

fun<T, U> T?.and_then(f: (T) -> U?): U? = if (this == null) {
    null
} else { f(this) }

/// Try all functions in a collection in order until one of them succeeds, and return that one's result.
fun<T> Iterable<(T) -> T?>.join(): (T) -> T? = {
    var result: T? = null
    for (f in this) {
        val stepres = f(it)
        if (stepres == null) {
            continue;
        } else {
            result = stepres
            break
        }
    }
    result
}