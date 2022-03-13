package es.headbe.texnes.items

import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.ident
import net.minecraft.block.Block
import net.minecraft.util.Identifier

object LumpOres {
    init {
        put(ident("bismuth_ore"), Blocks.bismuthOre)
    }
    var lumpOresRegistry: HashMap<Identifier, Block> = hashMapOf()

    fun put(ident: Identifier, block: Block) = lumpOresRegistry.put(ident, block)
}