package es.headbe.texnes.worldgen

import net.minecraft.block.Block
import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.registry.generation.Generation;
import net.minecraft.world.gen.surfacebuilder.MaterialRules;
import terrablender.worldgen.TBSurfaceRuleData

object EvaporiteSurfaceRules {
    val dirt = makeBlockstateRule(Blocks.saltBlock)

    fun makeRules(): MaterialRules.MaterialRule {

        return MaterialRules.sequence(
            MaterialRules.condition(MaterialRules.biome(Generation.saltFlatsKey, Generation.playaKey), makeBlockstateRule(Blocks.saltBlock)),
        )
    }

    private fun makeBlockstateRule(block: Block): MaterialRules.MaterialRule
     = MaterialRules.block(block.defaultState)
}