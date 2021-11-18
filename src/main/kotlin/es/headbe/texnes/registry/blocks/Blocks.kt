package es.headbe.texnes.registry.blocks

import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.FallingBlock
import net.minecraft.block.Material
import net.minecraft.sound.BlockSoundGroup
import es.headbe.texnes.util.*

object Blocks {
    val bismuthBlock = Block(FabricBlockSettings
        .of(Material.METAL)
        .breakByTool(FabricToolTags.PICKAXES, 2)
        .requiresTool()
        .strength(5f, 6f)
        .sounds(BlockSoundGroup.AMETHYST_BLOCK)
    )

    val saltBlock = evaporiteBlock(0.5f)
    val natronBlock = evaporiteBlock(0.5f)
    val nitreBlock = evaporiteBlock(0.5f)
    val gypsumBlock = evaporiteBlock(1f)
    val boraxBlock = evaporiteBlock(1f)

    private fun evaporiteBlock(hardness: Float): FallingBlock {
        return FallingBlock(FabricBlockSettings
            .of(Material.AGGREGATE)
            .breakByTool(FabricToolTags.SHOVELS, 0)
            .strength(hardness, hardness)
            .sounds(BlockSoundGroup.SAND)
        )
    }

    fun registerAll() {
        ident("bismuth_block").block(bismuthBlock)
        ident("salt_block").block(saltBlock)
        ident("natron_block").block(natronBlock)
        ident("nitre_block").block(nitreBlock)
        ident("gypsum_block").block(gypsumBlock)
        ident("borax_block").block(boraxBlock)
    }
}