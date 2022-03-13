package es.headbe.texnes.datagen

import es.headbe.texnes.util.Colour
import es.headbe.texnes.util.and_then
import es.headbe.texnes.util.ident
import net.minecraft.util.Identifier
import java.lang.IllegalArgumentException

class ResourceGenDescriptor private constructor(val base: ColourPalette, val gens: HashMap<TransformType, ColourPalette>) {



    fun block() = ResourceModel(TransformType.Block, gens[TransformType.Block] ?: base)
    fun ore() = ResourceModel(TransformType.Ore, gens[TransformType.Ore] ?: base)
    fun ingot() = ResourceModel(TransformType.Ingot, gens[TransformType.Ingot] ?: base)
    fun bucket() = ResourceModel(TransformType.Bucket, gens[TransformType.Ingot] ?: base)
    fun plate() = ResourceModel(TransformType.Plate, gens[TransformType.Block] ?: base)

    companion object {
        val makeWorker: (ColourPalette) -> ((Colour) -> Colour?) = {

            {texColour -> when (TextureTemplateColour.tryFromColour(texColour)) {
                TextureTemplateColour.Base -> it.base
                TextureTemplateColour.Outline -> it.outline ?: it.base
                TextureTemplateColour.Highlight -> it.highlight
                null -> null
            }
                }
        }
    }


    data class ColourPalette(val base: Colour, val highlight: Colour, val outline: Colour?) {
        constructor(base: Colour, highlight: Colour) : this(base, highlight, null)
    }

    data class ResourceModel(val model: TransformType, val colours: ColourPalette) {
        fun asWorker(): (Colour) -> Colour? = makeWorker(colours)
    }

    class Builder(val basePalette: ColourPalette) {
        private var gens: HashMap<TransformType, ColourPalette> = hashMapOf()

        fun with(type: TransformType, colours: ColourPalette): Builder {
            gens[type] = colours
            return this
        }

        private fun baseOnly(colour: Colour): ColourPalette = ColourPalette(colour, basePalette.highlight, basePalette.outline)
        private fun highlightOnly(colour: Colour): ColourPalette = ColourPalette(basePalette.base, colour, basePalette.outline)
        private fun outlineOnly(colour: Colour): ColourPalette = ColourPalette(basePalette.base, basePalette.highlight, colour)

        fun ingot(pal: ColourPalette) = with(TransformType.Ingot, pal)
        fun ingotOutline(colour: Colour) = with(TransformType.Ingot, outlineOnly(colour))
        fun ingotHighlight(colour: Colour) = with(TransformType.Ingot, highlightOnly(colour))
        fun ingotBase(colour: Colour) = with(TransformType.Ingot, baseOnly(colour))
        fun ore(pal: ColourPalette) = with(TransformType.Ore, pal)
        fun oreHighlight(colour: Colour) = with(TransformType.Ore, highlightOnly(colour))
        fun oreBase(colour: Colour) = with(TransformType.Ore, baseOnly(colour))
        fun block(pal: ColourPalette) = with(TransformType.Block, pal)
        fun blockOutline(colour: Colour) = with(TransformType.Block, outlineOnly(colour))
        fun blockHighlight(colour: Colour) = with(TransformType.Block, highlightOnly(colour))
        fun blockBase(colour: Colour) = with(TransformType.Block, baseOnly(colour))

        fun build() = ResourceGenDescriptor(basePalette, gens)
    }

    enum class TransformType {
        Block {
            override fun sort() = "block"
            override fun baseTexture() = "metal_block_base"
              },
        Ore {
            override fun sort() = "block"
            override fun baseTexture() = "ore_base"
        },
        Ingot {
            override fun sort() = "item"
            override fun baseTexture() = "ingot_base"
        },
        Bucket {
            override fun sort() = "item"
            override fun baseTexture() = "bucket_base"
        },
        Plate {
            override fun sort() = "item"
            override fun baseTexture() = "plate_base"
        };

        abstract fun sort(): String;
        abstract fun baseTexture(): String;
    }
}