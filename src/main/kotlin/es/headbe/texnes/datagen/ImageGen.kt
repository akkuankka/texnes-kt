package es.headbe.texnes.datagen

import es.headbe.texnes.util.Colour
import es.headbe.texnes.util.i32
import es.headbe.texnes.util.ident
import net.minecraft.client.MinecraftClient
import net.minecraft.client.util.ModelIdentifier
import java.awt.image.BufferedImage
import java.io.File
import javax.imageio.ImageIO

interface ImageGen<T> : DataFactory<T, BufferedImage> {
    override val extension: String get() = "png"

    override fun write(file: File, t: BufferedImage) {
        ImageIO.write(t, extension, file)
    }



    companion object {
        fun <T> simpleFactory(): (ModelIdentifier) -> ImageGen<T> = { id ->
            object : ImageGen<T> {
                override fun generate(): BufferedImage {
                    val img = BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB)
                    val baseGraphics = img.createGraphics()
                    val modelDescriptor = MetalSpritesRegistry.metalSprites[id]!! // if this is null it means something hasn't been registered
                    val colourWorker = modelDescriptor.asWorker()
                    val transform = modelDescriptor.model
                    val resourceId = ident("textures/${transform.sort()}/${transform.baseTexture()}.png")
                    val inputStream = MinecraftClient.getInstance().resourceManager.getResource(resourceId).inputStream
                    val template = ImageIO.read(inputStream)
                    for (x in 0 until 16) {
                        for (y in 0 until 16) {
                            val templateValue = template.getRGB(x, y)
                            val alpha = ((templateValue shr 24) and 255)
                            val templateColour = Colour.fromRGB(templateValue.toUInt())
                            val outputColour = colourWorker(templateColour) ?: templateColour
                            val newColour = toRGBA(alpha, outputColour)
                            template.setRGB(x, y, newColour)
                        }
                    }
                    baseGraphics.drawImage(template, 0, 0, null)
                    baseGraphics.dispose()
                    return img
                }
            }
        }

        private fun toRGBA(alpha: i32, colour: Colour) =
            (alpha and 0xff shl 24 or
                    (colour.red.toInt() and 0xFF shl 16) or
                    (colour.green.toInt() shl 8) or
                    (colour.blue.toInt()))

        fun <T> nullFactory(): ImageGen<T> = object : ImageGen<T> {
            override fun generate(): BufferedImage? = null
        }
    }

}