package es.headbe.texnes.registry.blocks

import es.headbe.texnes.Texnes
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.fabricmc.fabric.api.tool.attribute.v1.FabricToolTags
import net.minecraft.block.Block
import net.minecraft.block.FallingBlock
import net.minecraft.block.Material
import net.minecraft.sound.BlockSoundGroup
import es.headbe.texnes.util.*
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.item.BlockItem

object Blocks {
    val bismuthBlock = Block(FabricBlockSettings
        .of(Material.METAL)
        .breakByTool(FabricToolTags.PICKAXES, 2)
        .requiresTool()
        .strength(5f, 6f)
        .sounds(BlockSoundGroup.AMETHYST_BLOCK)
    )
    val gasLamp = GasLamp()

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

    private fun Block.asBlockItem(): BlockItem {
        return BlockItem(
            this,
            FabricItemSettings().group(Texnes.ITEM_GROUP)
        )
    }

    fun registerAll() {
        ident("bismuth_block").block(bismuthBlock).item(bismuthBlock.asBlockItem())
        ident("gas_lamp").block(gasLamp).item(gasLamp.asBlockItem())
        ident("salt_block").block(saltBlock).item(saltBlock.asBlockItem())
        ident("natron_block").block(natronBlock).item(natronBlock.asBlockItem())
        ident("nitre_block").block(nitreBlock).item(nitreBlock.asBlockItem())
        ident("gypsum_block").block(gypsumBlock).item(gypsumBlock.asBlockItem())
        ident("borax_block").block(boraxBlock).item(boraxBlock.asBlockItem())
    }
}