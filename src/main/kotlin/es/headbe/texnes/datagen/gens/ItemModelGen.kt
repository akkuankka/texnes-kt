package es.headbe.texnes.datagen.gens

import com.google.gson.JsonObject
import es.headbe.texnes.datagen.JsonFactory
import es.headbe.texnes.datagen.DataGenerator
import net.minecraft.item.BlockItem
import net.minecraft.item.Item
import net.minecraft.util.registry.Registry
import java.io.File

class ItemModelGen(val root: File, namespace: String, fallback: (Item) -> JsonFactory<Item>)
    : DataGenerator<Item, JsonObject?>(File(root, "models/item"), namespace, fallback) {
    override fun generate(): Int {
        var count = 0
        Registry.ITEM.ids.filter {id -> id.namespace == namespace}.forEach {
            val item = Registry.ITEM.get(it)
            if (item.asItem() != null && generate(it, item)) {
                count++
            }
        }
        return count
    }

    companion object {
        val default: (Item) -> JsonFactory<Item> = { item ->
            object : JsonFactory<Item> {
                override fun generate(): JsonObject {
                    val id = Registry.ITEM.getId(item)
                    val obj = JsonObject()
                    if (item is BlockItem)
                        obj.addProperty("parent", "${id.namespace}:block/${id.path}")
                    else {
                        obj.addProperty("parent", "item/generated")
                        val texturesObj = JsonObject()
                        texturesObj.addProperty("layer0", "${"id.namespace"}:item/${id.path}")
                        obj.add("textures", texturesObj)
                    }
                    return obj
                }
            }
        }
    }

}