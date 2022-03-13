package es.headbe.texnes.datagen.gens

import es.headbe.texnes.datagen.DataGenerator
import es.headbe.texnes.datagen.ImageGen
import net.minecraft.util.Identifier
import java.awt.image.BufferedImage
import java.io.File

class MetalSpriteGenerator(
    val root: File,
    namespace: String,
    fallback: (Identifier) -> ImageGen<Identifier>
) : DataGenerator<Identifier, BufferedImage>(File(root, "textures"), namespace, fallback) {
    override fun generate(): Int {
        var count = 0
        generators.forEach { (tag, _) ->
            if (generate(tag, tag))
                count++
        }
        return count
    }
}
