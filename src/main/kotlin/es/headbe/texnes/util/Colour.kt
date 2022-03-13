package es.headbe.texnes.util

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

data class Colour(val red: u8, val green: u8, val blue: u8) {
    /**
     * Darkens a colour by a given amount
     * e.g. darken(0.1) darkens by 10%
     * @param amount An amount to darken ranging from 0.0 (same colour) to 1.0 (black)
     * Will crash if used outside this range
     */
    fun darken(amount: f64): Colour = scale(max(0.0, 1.0 - amount) )

    /**
     * Lightens a colour by a given amount
     * e.g. lighten(0.1) lightens by 10%
     * @param amount An amount to lighten ranging from 0.0 (same colour) to 1.0 (white)
     * Will crash if used outside this range
     */
    fun lighten(amount: f64): Colour = scale(min(1.0, 1.0 + amount))

    fun scale(amount: f64): Colour {
        val red = this.red.toDouble() * amount ; assert(red in 0.0 .. 255.0)
        val green = this.green.toDouble() * amount ; assert(green in 0.0 .. 255.0)
        val blue = this.blue.toDouble() * amount ; assert(blue in 0.0 .. 255.0)
        return Colour(red.toUInt().toUByte(), green.toUInt().toUByte(), blue.toUInt().toUByte())
    }


    companion object {
        /** Generates a colour from an rgb literal like 0x3fa09e_u
         *  the first byte is ignored.
         *
         */
        fun fromRGB(num: u32): Colour = Colour(
            ((num and 0x00ff0000u) shr 16).toUByte(),
            ((num and 0x0000ff00u) shr 8).toUByte(),
            (num and 0x000000ffu).toUByte()
        )

        private fun normaliseFloat(x: f32): u8 = (x * u8.MAX_VALUE.toFloat()).toUInt().toUByte()

        fun fromFloats(red: f32, green: f32, blue: f32): Colour =
            Colour(normaliseFloat(red), normaliseFloat(green), normaliseFloat(blue))


        /** Generates a colours from HSV.
         *
         *  @param hue A hue in degrees, ideally between 0° and 360°, although it will be modded if it isn't.
         *  @param sat The saturation in the range 0..1
         *  @param lum The luminance in the range 0..1
         */

        fun fromHSL(hue: f32, sat: f32, lum: f32): Colour {
            // approach from Wikipedia https://en.wikipedia.org/wiki/HSL_and_HSV#HSL_to_RGB
            val hue = hue % 360
            val chroma = (1 - abs(2*lum - 1)) * sat
            val nHue = hue / 60
            val x = chroma * (1 - abs((nHue % 2 - 1)))
            val (r, g, b) = when (nHue) {
                in 0f until 1f -> Triple(chroma, x, 0f)
                in 1f until 2f -> Triple(x, chroma, 0f)
                in 2f until 3f -> Triple(0f, chroma, x)
                in 3f until 4f -> Triple(0f, x, chroma)
                in 4f until 5f -> Triple(x, 0f, chroma)
                in 5f until 6f -> Triple(chroma, 0f, x)
                else -> Triple(0f, 0f, 0f)
            }
            val scale = lum - chroma/2
            return fromFloats(r + scale, g + scale, b + scale)
        }


    }
}
