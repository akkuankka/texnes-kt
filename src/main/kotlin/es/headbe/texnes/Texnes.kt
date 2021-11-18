package es.headbe.texnes

// For support join https://discord.gg/v6v4pMv

@Suppress("unused")
class Texnes {
    fun init() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.

        println("Hello Fabric world!")
    }

    companion object {
        const val MOD_ID = "texnes"
    }
}

