package es.headbe.texnes.registry.items

import es.headbe.texnes.Texnes
import net.fabricmc.fabric.api.item.v1.FabricItemSettings
import net.minecraft.block.Block
import net.minecraft.item.BlockItem
import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.*

object Items {
// normal items
    val bismuth: CrystalsItem = CrystalsItem(
        FabricItemSettings().group(Texnes.ITEM_GROUP)
    )

    val pressureGauge = PressureGauge(
        FabricItemSettings().group(Texnes.ITEM_GROUP)
    )



// block items
    private fun Block.asBlockItem(): BlockItem {
        return BlockItem(
            this,
            FabricItemSettings().group(Texnes.ITEM_GROUP)
        )
    }

    val bismuthBlock = Blocks.bismuthBlock.asBlockItem()
    val natronBlock = Blocks.natronBlock.asBlockItem()
    val saltBlock = Blocks.saltBlock.asBlockItem()
    val gypsumBlock = Blocks.gypsumBlock.asBlockItem()
    val nitreBlock = Blocks.nitreBlock.asBlockItem()
    val boraxBlock = Blocks.boraxBlock.asBlockItem()

    fun registerAll() {
        // item items
        ident("bismuth").item(bismuth)
        ident("pressure_gauge").item(pressureGauge)

        // block items
        ident("bismuth_block").item(bismuthBlock)
        ident("natron_block").item(natronBlock)
        ident("salt_block").item(saltBlock)
        ident("gypsum_block").item(gypsumBlock)
        ident("nitre_block").item(nitreBlock)
        ident("borax_block").item(boraxBlock)
    }
}

