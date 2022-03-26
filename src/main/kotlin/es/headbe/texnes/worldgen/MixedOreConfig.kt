package es.headbe.texnes.worldgen

import com.mojang.serialization.Codec
import com.mojang.serialization.codecs.RecordCodecBuilder
import net.minecraft.structure.rule.RuleTest
import net.minecraft.util.math.intprovider.IntProvider
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.stateprovider.BlockStateProvider

data class MixedOreConfig(val size: IntProvider, val targets: List<MixedOreConfig.Target>): FeatureConfig {
    companion object {
        val codec: Codec<MixedOreConfig> = RecordCodecBuilder.create {
            it.group(
                IntProvider.VALUE_CODEC.fieldOf("size").forGetter(MixedOreConfig::size),
                Codec.list(MixedOreConfig.Target.codec).fieldOf("targets").forGetter(MixedOreConfig::targets)
            ).apply(it) { size, targets -> MixedOreConfig(size, targets) }
        }
    }

    data class Target(val test: RuleTest, val blocks: BlockStateProvider) {
        companion object {
            val codec: Codec<Target> = RecordCodecBuilder.create {
                it.group(
                    RuleTest.TYPE_CODEC.fieldOf("test").forGetter(Target::test),
                    BlockStateProvider.TYPE_CODEC.fieldOf("blocks").forGetter(Target::blocks)
                ).apply(it) {size, targets -> Target(size, targets)}
            }
        }
    }
}