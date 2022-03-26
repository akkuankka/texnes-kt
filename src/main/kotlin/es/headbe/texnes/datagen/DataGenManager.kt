package es.headbe.texnes.datagen

/* This approach is largely nicked from https://github.com/GabrielOlvH/Industrial-Revolution/blob/24f11011c8a7f2fbfac4d731d12cabc1e5e07206/src/main/kotlin/me/steven/indrev/datagen/DataGeneratorManager.kt
*  I will rejig it in the future so that this is more automatic and can be done from a distance
* -- i.e. such that an item can be defined in the same place its texture and model are
*/
import es.headbe.texnes.Texnes
import es.headbe.texnes.datagen.gens.BlockModelGen
import es.headbe.texnes.datagen.gens.ItemModelGen
import es.headbe.texnes.datagen.gens.LootTableGenerator
import java.io.File
import es.headbe.texnes.datagen.gens.MetalSpriteGenerator
import es.headbe.texnes.items.LumpOres
import net.minecraft.item.BlockItem
import net.minecraft.item.Items
import net.minecraft.util.Identifier
import net.minecraft.util.registry.Registry

/**
 * Entry point for data generation management.
 * As of currently, you need to register all the datagen through here. It can't be done in other places
 * Because it's not known whether they will run after this has already created the files.
 */
class DataGenManager {
    val root = File("generated")

    val lootTableGenerator = LootTableGenerator(root, Texnes.MOD_ID, LootTableGenerator.selfDrop)
    val itemModelGen = ItemModelGen(root, Texnes.MOD_ID, ItemModelGen.default)
    val blockModelGen = BlockModelGen(root, Texnes.MOD_ID) { JsonFactory.nullFactory() }
    val metalSpriteGen = MetalSpriteGenerator(root, Texnes.MOD_ID) { ImageGen.nullFactory() }

    init {
//        println("dataGenManager initialising")
        LumpOres.lumpOresRegistry.forEach { (_, block) ->  lootTableGenerator.register(block, LootTableGenerator.oreLumpDrop(block))}

        MetalSpritesRegistry.metalSprites.forEach { (id, _) ->
            val realId = Identifier(id.namespace, id.path)
            val item = Registry.ITEM.get(realId)
            if (item != Items.AIR) {
                if (item is BlockItem) {
                     blockModelGen.register(item.block, BlockModelGen.cubeAll(item.block))
                }
//                println("DATAGEN: Generating item $item")
                metalSpriteGen.register(id, ImageGen.simpleFactory<Identifier>()(id))
                itemModelGen.register(item, ItemModelGen.default(item))


            }

        }
    }

    fun generate() {
        println("generating items and stuff")
        val itemModelsGenerated = itemModelGen.generate()
        val blockModelsGenerated = blockModelGen.generate()
        val spritesGenerated = metalSpriteGen.generate()
        val lootTablesGenerated = lootTableGenerator.generate()
    }
}