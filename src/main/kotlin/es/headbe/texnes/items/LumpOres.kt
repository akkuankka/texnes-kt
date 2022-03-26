package es.headbe.texnes.items

import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.ident
import net.minecraft.block.Block
import net.minecraft.util.Identifier

object LumpOres {
    val lumpOresRegistry: HashMap<Identifier, Block> = hashMapOf()
    init {
        put(ident("arsenic_ore"), Blocks.arsenicOre)
        put(ident("lead_ore"), Blocks.leadOre)
        put(ident("tin_ore"), Blocks.tinOre)
        put(ident("zinc_ore"), Blocks.zincOre)
        put(ident("silver_ore"), Blocks.silverOre)
    }

    fun put(ident: Identifier, block: Block) = lumpOresRegistry.put(ident, block)

}