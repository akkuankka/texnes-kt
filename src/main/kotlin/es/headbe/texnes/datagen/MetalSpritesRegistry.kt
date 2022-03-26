package es.headbe.texnes.datagen

import es.headbe.texnes.items.Metals
import es.headbe.texnes.util.ident
import net.minecraft.client.util.ModelIdentifier
import es.headbe.texnes.datagen.ResourceGenDescriptor.ResourceModel
import net.minecraft.block.Block
import net.minecraft.client.texture.Sprite

object MetalSpritesRegistry {
    val metalSprites: HashMap<ModelIdentifier, ResourceModel> = hashMapOf()
    init {
       val lead = put("lead".spriteBuilder()
            .addPlate(Metals.leadSettings)
            .addIngot(Metals.leadSettings)
            .addOreLump(Metals.leadSettings)

       )
        putBlock(lead.spriteBuilder()
            .addBlock(Metals.leadSettings)
            .addOre(Metals.leadSettings))

        val tin = put("tin".spriteBuilder()
            .addPlate(Metals.tinSettings)
            .addIngot(Metals.tinSettings)
            .addOreLump(Metals.tinSettings)
        )
        putBlock(tin.spriteBuilder()
            .addBlock(Metals.tinSettings)
            .addOre(Metals.tinSettings))

        val zinc = put("zinc".spriteBuilder()
            .addPlate(Metals.zincSettings)
            .addIngot(Metals.zincSettings)
            .addOreLump(Metals.zincSettings)
        )
        putBlock(zinc.spriteBuilder()
            .addBlock(Metals.zincSettings)
            .addOre(Metals.zincSettings))

        val silver = put("silver".spriteBuilder()
            .addPlate(Metals.silverSettings)
            .addIngot(Metals.silverSettings)
            .addOreLump(Metals.silverSettings)
        )
        putBlock(silver.spriteBuilder()
            .addBlock(Metals.silverSettings)
            .addOre(Metals.silverSettings))

        putBlock("arsenic".spriteBuilder() // raw arsenic texture is hand-drawn
            .addOre(Metals.arsenicSettings))

    }

    fun put(id: String, descriptor: ResourceModel) {
        metalSprites[ModelIdentifier(ident(id), "inventory")] = descriptor
    }
    fun put(list: SpriteList): String {
        for ((spriteName, model) in list.list) {
            put(spriteName, model)
        }
        return list.name
    }

    fun putBlock(id: String, descriptor: ResourceModel) {
        metalSprites[ModelIdentifier(ident(id), "inventory")] = descriptor
        metalSprites[ModelIdentifier(ident(id), "")] = descriptor
    }
    fun putBlock(list: SpriteList): String {
        for ((spriteName, model) in list.list) {
            putBlock(spriteName, model)
        }
        return list.name
    }


    data class SpriteList(val name: String, var list: List<Pair<String, ResourceModel>>) {
        fun addPlate(model: ResourceGenDescriptor): SpriteList = run {list += Pair("${name}_plate", model.plate()); this}
        fun addIngot(model: ResourceGenDescriptor): SpriteList = run {list += Pair("${name}_ingot", model.ingot()); this}
        fun addBlock(model: ResourceGenDescriptor): SpriteList = run {list += Pair("${name}_block", model.block()); this}
        fun addOre(model: ResourceGenDescriptor): SpriteList = run {list += Pair("${name}_ore", model.ore()); this}
        fun addOreLump(model: ResourceGenDescriptor): SpriteList = run {list += Pair("raw_$name", model.lump()); this}
        fun add(whateverElse: String, model: ResourceModel): SpriteList = run {list += Pair("${name}_${whateverElse}", model); this}
    }


    fun String.spriteBuilder(): SpriteList = SpriteList(this, listOf())
}