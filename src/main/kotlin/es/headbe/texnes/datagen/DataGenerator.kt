package es.headbe.texnes.datagen

import net.minecraft.util.Identifier
import java.io.File
import es.headbe.texnes.util.*

abstract class DataGenerator<T, P>(val dir: File, val namespace: String, val fallback: (T) -> DataFactory<T, P>) {
    protected val generators = HashMap<T, DataFactory<T, P>>()

    init {
        dir.mkdirs()
    }

    operator fun get(obj: T): DataFactory<T, P> = generators.getOrDefault(obj, fallback(obj))

    fun register(obj: T, factory: DataFactory<T, P>) {
        generators[obj] = factory
    }

    fun generate(ident: Identifier, obj: T): Boolean {
        val jsonFactory = this[obj]
        return generate(ident, obj, jsonFactory)
    }

    fun generate(ident: Identifier, obj: T, factory: DataFactory<T, P>): Boolean {
        val file = File(dir, "${factory.getFileName(obj, ident)}.${factory.extension}")
        file.parentFile.mkdirs()
        val output = factory.generate()
        val result = output.map { outp ->
            file.createNewFile()
//            println("DATAGEN: Writing file $file")
            factory.write(file, outp)
            true
        }
        return result ?: false
    }

    abstract fun generate(): i32
}