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

    val zincSettings = ResourceGenDescriptor.Builder(ColourPalette(
        Colour.fromRGB(0xdef8bfu),
        Colour.fromRGB(0xf7f5d4u),
        Colour.fromRGB(0xd5d0beu),
    ))
        .ore(ColourPalette(
            Colour.fromRGB(0x292521u),
            Colour.fromRGB(0x807875u),
            Colour.fromRGB(0x331911u),))
        .build()

    val arsenicSettings = ResourceGenDescriptor.Builder(ColourPalette(
        Colour.fromRGB(0xeb2f0cu),
        Colour.fromRGB(0xd13d2fu),
        Colour.fromRGB(0xd15633u)
    )).build()

    private val silverOreBase = Colour.fromRGB(0x3a394au)
    val silverSettings = ResourceGenDescriptor.Builder(ColourPalette(
        Colour.fromRGB(0xd5dee3u),
        Colour.fromRGB(0xe4edf2u),
        Colour.fromRGB(0xd15633u)
    )).ore(ColourPalette(
        silverOreBase, silverOreBase.lighten(.1), silverOreBase.darken(.15)
    )).build()
}