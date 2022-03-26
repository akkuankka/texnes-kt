package es.headbe.texnes.registry.blocks

import es.headbe.texnes.Texnes
import net.fabricmc.fabric.api.`object`.builder.v1.block.FabricBlockSettings
import net.minecraft.block.Block
import net.minecraft.block.FallingBlock
import net.minecraft.block.Material
import net.minecraft.sound.BlockSoundGroup
import es.headbe.texnes.util.*
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.OreBlock
import net.minecraft.item.BlockItem
import net.minecraft.util.Identifier

object Blocks {
    val bismuthBlock = Block(FabricBlockSettings
        .of(Material.METAL)
        .requiresTool()
        .strength(5f, 6f)
        .sounds(BlockSoundGroup.AMETHYST_BLOCK)
    )

    private fun metalOre(strength: f32 = 5f, resistance: f32 = 6f) = OreBlock(FabricBlockSettings
        .of(Material.STONE)
        .requiresTool()
        .strength(strength, resistance)
        .sounds(BlockSoundGroup.STONE)
    )

    val bismuthOre = metalOre()


    val gasLamp = GasLamp()

    val saltBlock = evaporiteBlock(0.5f)
    val natronBlock = evaporiteBlock(0.5f)
    val nitreBlock = evaporiteBlock(0.5f)
    val gypsumBlock = evaporiteBlock(1f)
    val boraxBlock = evaporiteBlock(1f)

    val chemistsCabinet = ChemistsCabinet()

    private fun evaporiteBlock(hardness: Float): FallingBlock {
        return FallingBlock(FabricBlockSettings
            .of(Material.AGGREGATE)
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

    private fun OreBlock.registerOre(name: String): String {
        ident("${name}_ore").block(this).item(this.asBlockItem())
        return name
    }


    val leadOre = metalOre()
    val tinOre = metalOre()
    val zincOre = metalOre()
    val arsenicOre = metalOre()
    val silverOre = metalOre()

    private fun registerOres() {
        ident("bismuth_ore").block(bismuthOre).item(bismuthOre.asBlockItem())
        leadOre.registerOre("lead")
        tinOre.registerOre("tin")
        zincOre.registerOre("zinc")
        arsenicOre.registerOre("arsenic")
        silverOre.registerOre("silver")
    }

    fun registerAll() {
        registerOres()
        ident("bismuth_block").block(bismuthBlock).item(bismuthBlock.asBlockItem())
        ident("gas_lamp").block(gasLamp).item(gasLamp.asBlockItem())
        ident("salt_block").block(saltBlock).item(saltBlock.asBlockItem())
        ident("natron_block").block(natronBlock).item(natronBlock.asBlockItem())
        ident("nitre_block").block(nitreBlock).item(nitreBlock.asBlockItem())
        ident("gypsum_block").block(gypsumBlock).item(gypsumBlock.asBlockItem())
        ident("borax_block").block(boraxBlock).item(boraxBlock.asBlockItem())
        ident("chemists_cabinet").block(chemistsCabinet).item(chemistsCabinet.asBlockItem())
        ChemistsCabinet.Entity.initType(chemistsCabinet)

    }
}