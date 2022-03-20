package es.headbe.texnes.worldgen

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.block.BlockState
import net.minecraft.util.math.intprovider.IntProvider
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.stateprovider.BlockStateProvider

data class EvaporitePatchConfig(val radius: IntProvider, val block: BlockStateProvider, val targets: List<BlockState>) :
    FeatureConfig {
    companion object {
        val codec: Codec<EvaporitePatchConfig> = RecordCodecBuilder.create {
            it.group(
                IntProvider.VALUE_CODEC.fieldOf("height").forGetter(EvaporitePatchConfig::radius),
                BlockStateProvider.TYPE_CODEC.fieldOf("block").forGetter(EvaporitePatchConfig::block),
                BlockState.CODEC.listOf().fieldOf("canReplace").forGetter(EvaporitePatchConfig::targets)
            ).apply(it) { radius, block, target -> EvaporitePatchConfig(radius, block, target) } // I don't know why I can't just use the constructor there but whatever

        }
    }
}
