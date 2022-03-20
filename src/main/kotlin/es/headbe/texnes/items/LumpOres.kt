package es.headbe.texnes.items

import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.ident
import net.minecraft.block.Block
import net.minecraft.util.Identifier

object LumpOres {
    val lumpOresRegistry: HashMap<Identifier, Block> = hashMapOf()
    init {
        put(ident("bismuth_ore"), Blocks.bismuthOre)
    }

    fun put(ident: Identifier, block: Block) = lumpOresRegistry?.put(ident, block)
}