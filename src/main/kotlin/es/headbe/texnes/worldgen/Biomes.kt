package es.headbe.texnes.worldgen

import com.mojang.datafixers.util.Pair
import es.headbe.texnes.registry.generation.Generation
import es.headbe.texnes.util.*
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.Biome
import net.minecraft.world.biome.source.util.MultiNoiseUtil
import terrablender.api.Region
import terrablender.api.RegionType
import java.util.function.Consumer
import net.minecraft.world.biome.OverworldBiomeCreator
import terrablender.api.ParameterUtils

class Biomes : Region(ident("texnes_biome_provider"), RegionType.OVERWORLD , 4) {
    override fun addBiomes(
        registry: Registry<Biome>,
        mapper: Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>>
    ) {
        addBiome(mapper, ParameterUtils.Temperature.FULL_RANGE, ParameterUtils.Humidity.DRY, ParameterUtils.Continentalness.INLAND, ParameterUtils.Erosion.EROSION_6, ParameterUtils.Weirdness.VALLEY, ParameterUtils.Depth.SURFACE, 0.4f, Generation.saltFlatsKey)
        addBiome(mapper, ParameterUtils.Temperature.COOL, ParameterUtils.Humidity.DRY, ParameterUtils.Continentalness.FAR_INLAND, ParameterUtils.Erosion.EROSION_1, ParameterUtils.Weirdness.VALLEY, ParameterUtils.Depth.SURFACE, 0.4f, Generation.playaKey)
    }

}