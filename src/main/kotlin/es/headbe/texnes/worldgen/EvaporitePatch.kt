package es.headbe.texnes.worldgen

import com.google.common.collect.ImmutableList
import com.mojang.serialization.Codec
import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.iteratorUtil.with
import es.headbe.texnes.util.iteratorUtil.zipAll
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import net.minecraft.world.gen.stateprovider.BlockStateProvider

class EvaporitePatch(configCodec: Codec<EvaporitePatchConfig>) : Feature<EvaporitePatchConfig>(configCodec) {

    override fun generate(context: FeatureContext<EvaporitePatchConfig>): Boolean {
        val pos = context.origin
        val config = context.config

        val radius = config.radius.get(context.random)
        val x_origin = pos.x - radius
        val x_max = pos.x + radius
        val z_origin = pos.z - radius
        val z_max = pos.z + radius
        val y_origin = pos.y
        val y_min = pos.y - radius;

        val swapTo = config.block.getBlockState(context.random, pos)

        // iterator over a matrix of all the absolute coordinates as tuples
        val coordinateMatrixIterator = (y_origin downTo y_min)
            .flatMap { y -> (x_origin..x_max).zipAll(y) }
            .flatMap { xy -> (z_origin..z_max).map { xy.with(it) } }

        for ((x, y, z) in coordinateMatrixIterator) {
            // coordinates relative to the origin of the structure
            val x_rel = x - pos.x
            val y_rel = y - pos.y
            val z_rel = z - pos.z

            val currentPos = BlockPos(x, y, z)
            val currentBlock = context.world
                .getBlockState(currentPos)

            val inRange = (x_rel * x_rel + y_rel * y_rel + z_rel * z_rel) < radius * radius
            if (inRange && config.targets.contains(currentBlock)) {
                context.world.setBlockState(currentPos, swapTo, 3)
            }
        }
        return true
    }
    companion object {
        val evaporitePatch = EvaporitePatch(EvaporitePatchConfig.codec)
        fun <FC : FeatureConfig> Feature<FC>.configure(config: FC): ConfiguredFeature<FC, Feature<FC>> = ConfiguredFeature(this, config)

        fun configureWith(block: BlockState): ConfiguredFeature<*, *>
            = evaporitePatch.configure(
                EvaporitePatchConfig(
                    UniformIntProvider.create(2, 12),
                    BlockStateProvider.of(block),
                    ImmutableList.of(Blocks.saltBlock.defaultState, Blocks.gypsumBlock.defaultState)
                )
            )/*.decorate(Decorator.HEIGHTMAP.configure(HeightmapDecoratorConfig(Heightmap.Type.OCEAN_FLOOR_WG)))
                .spreadHorizontally()
                .applyChance(chance)*/
    }

}