package es.headbe.texnes.datagen.gens

import com.google.gson.JsonObject
import es.headbe.texnes.datagen.DataGenerator
import es.headbe.texnes.datagen.JsonFactory
import es.headbe.texnes.util.i32
import net.minecraft.block.Block
import net.minecraft.util.registry.Registry
import java.io.File


class BlockModelGen(val root: File, namespace: String, fallback: (Block) -> JsonFactory<Block>)
    : DataGenerator<Block, JsonObject?>(File(root, "models/block"), namespace, fallback) {
    override fun generate(): i32 {
        var count = 0
        generators.forEach { (block, _obj) ->
            if (generate(Registry.BLOCK.getId(block), block))
                count++
        }
        return count
    }

    companion object {
        val cubeAll: (Block) -> JsonFactory<Block> = { item ->
            object : JsonFactory<Block> {
                override fun generate(): JsonObject {
                    val id = Registry.BLOCK.getId(item)
                    val obj = JsonObject()
                    obj.addProperty("parent", "block/cube_all")
                    val texturesObj = JsonObject()
                    texturesObj.addProperty("all", "${id.namespace}:block/${id.path}")
                    obj.add("textures", texturesObj)
                    return obj
                }
            }
        }
    }

}