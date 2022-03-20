package es.headbe.texnes.datagen

import es.headbe.texnes.items.Metals.leadSettings
import es.headbe.texnes.util.ident
import net.minecraft.client.util.ModelIdentifier
import es.headbe.texnes.datagen.ResourceGenDescriptor.ResourceModel

object MetalSpritesRegistry {
    val metalSprites: HashMap<ModelIdentifier, ResourceModel> = hashMapOf()
    init {
        put("lead_plate", leadSettings.plate())
        putOreAndIngot("lead", leadSettings)
        putBlock("lead_block", leadSettings.block())
    }

    fun put(id: String, descriptor: ResourceModel) {
        metalSprites[ModelIdentifier(ident(id), "inventory")] = descriptor
    }

    fun putBlock(id: String, descriptor: ResourceModel) {
        metalSprites[ModelIdentifier(ident(id), "inventory")] = descriptor
        metalSprites[ModelIdentifier(ident(id), "")] = descriptor
    }

    fun putOreAndIngot(name: String, descriptor: ResourceGenDescriptor) {
        put("${name}_ingot", descriptor.ingot())
        putBlock("${name}_ore", descriptor.ingot())
    }
}