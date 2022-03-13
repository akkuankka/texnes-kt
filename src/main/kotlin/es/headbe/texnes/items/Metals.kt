package es.headbe.texnes.items

import es.headbe.texnes.datagen.ResourceGenDescriptor
import es.headbe.texnes.util.Colour
import es.headbe.texnes.datagen.ResourceGenDescriptor.ColourPalette

object Metals {
    private val leadBase = Colour.fromRGB(0x6f7285u)
    val leadSettings = ResourceGenDescriptor.Builder(
        ColourPalette(
            leadBase,
            Colour.fromRGB(0x6a6e90u),
            leadBase.darken(0.1)
        )
    )
        .build()

    private val tinBase = Colour.fromHSL(75f, .76f, .92f)
    val tinSettings = ResourceGenDescriptor.Builder(
        ColourPalette(
            tinBase,
            Colour.fromHSL(75f, .74f, .95f),
            tinBase.darken(0.2)
        )
    ).ore(ColourPalette(Colour.fromHSL(23f, 0.73f, 0.23f), Colour.fromHSL(23f, .29f, .32f)))
        .build()
}