package es.headbe.texnes

import es.headbe.texnes.registry.generation.Generation
import net.fabricmc.fabric.api.client.itemgroup.FabricItemGroupBuilder
import net.minecraft.item.ItemGroup
import net.minecraft.item.ItemStack
import net.minecraft.util.Identifier
import es.headbe.texnes.registry.items.Items as ModItems
import es.headbe.texnes.registry.blocks.Blocks as ModBlocks

// For support join https://discord.gg/v6v4pMv

@Suppress("unused")
class Texnes {
    fun init() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        ModBlocks.registerAll()
        ModItems.registerAll()
        Generation.registerAll()

//        println("Hello Fabric world!")
    }

    companion object {
        const val MOD_ID = "texnes"

        val ITEM_GROUP: ItemGroup = FabricItemGroupBuilder.build(
            Identifier(MOD_ID, "texnes-tab")
        ) { ItemStack(ModItems.bismuth) }
    }

}

