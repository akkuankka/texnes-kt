package es.headbe.texnes.datagen

import es.headbe.texnes.util.Colour
import es.headbe.texnes.util.*;

enum class TextureTemplateColour {
    /// == #ff00ff
    Base,
    /// == #ffff00
    Highlight,
    /// == #00ffff;
    Outline;

    fun asColour(): Colour
    = when (this) {
        Base -> baseColour
        Highlight -> highlightColour
        Outline -> outlineColour
    }

    companion object {
        val baseColour: Colour = Colour.fromRGB(0xff00ffu)
        val highlightColour = Colour.fromRGB(0xffff00u)
        val outlineColour = Colour.fromRGB(0x00ffffu)
        fun tryFromColour(colour: Colour): TextureTemplateColour? = when (colour) {
            baseColour -> Base
            highlightColour -> Highlight
            outlineColour -> Outline
            else -> null
        }
    }
}

