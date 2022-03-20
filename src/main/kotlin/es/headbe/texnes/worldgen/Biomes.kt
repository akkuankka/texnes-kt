package es.headbe.texnes.worldgen

import com.mojang.datafixers.util.Pair
import es.headbe.texnes.registry.generation.Generation
import es.headbe.texnes.util.*
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.*
import net.minecraft.world.biome.source.util.MultiNoiseUtil
import terrablender.api.Region
import terrablender.api.RegionType
import java.util.function.Consumer
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import terrablender.api.ParameterUtils

class Biomes : Region(ident("texnes_biome_provider"), RegionType.OVERWORLD , 4) {
    override fun addBiomes(
        registry: Registry<Biome>,
        mapper: Consumer<Pair<MultiNoiseUtil.NoiseHypercube, RegistryKey<Biome>>>
    ) {
        addBiome(mapper, ParameterUtils.Temperature.FULL_RANGE, ParameterUtils.Humidity.DRY, ParameterUtils.Continentalness.INLAND, ParameterUtils.Erosion.EROSION_6, ParameterUtils.Weirdness.VALLEY, ParameterUtils.Depth.SURFACE, 0.4f, Generation.saltFlatsKey)
        addBiome(mapper, ParameterUtils.Temperature.COOL, ParameterUtils.Humidity.DRY, ParameterUtils.Continentalness.FAR_INLAND, ParameterUtils.Erosion.EROSION_1, ParameterUtils.Weirdness.VALLEY, ParameterUtils.Depth.SURFACE, 0.4f, Generation.playaKey)
    }

    companion object {
        val saltFlats: Biome = run {
            //setting the mob spawns
            val spawnSettings = SpawnSettings.Builder()
            DefaultBiomeFeatures.addFarmAnimals(spawnSettings)
            DefaultBiomeFeatures.addMonsters(spawnSettings, 40, 30, 120, false)
            //generation
            var generationSettings = GenerationSettings.Builder() // it's mutable because we are clearly mutating it
//                .surfaceBuilder(evaporiteSurface)
            //setting the mob spawns

            //generation
            Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.DESERT)
//                .depth(0.003f)
//                .scale(0.0003f)
                .temperature(0.2f)
                .downfall(0.0f)
                .effects(
                    BiomeEffects.Builder()
                        .waterColor(0x4b_86_db)
                        .waterFogColor(0x4b_86_db)
                        .fogColor(0xbf_a6_5c)
                        .skyColor(0x45_69_fa)
                        .build()
                )
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build()
        }
        val playa: Biome = run {
            // what kind of mobs can spawn


            // what kind of mobs can spawn
            val spawnSettings = SpawnSettings.Builder()
            DefaultBiomeFeatures.addFarmAnimals(spawnSettings)
            DefaultBiomeFeatures.addMonsters(spawnSettings, 40, 30, 120, false)


            // generation &c.
            val generationSettings = GenerationSettings.Builder()
//        generationSettings.surfaceBuilder(evaporiteSurface)
//        DefaultBiomeFeatures.addDefaultLakes(generationSettings)
            DefaultBiomeFeatures.addDungeons(generationSettings)
            DefaultBiomeFeatures.addMineables(generationSettings)
            DefaultBiomeFeatures.addDefaultOres(generationSettings)
//        DefaultBiomeFeatures.addSprings(generatonSettings)
            DefaultBiomeFeatures.addFrozenTopLayer(generationSettings)

            Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.EXTREME_HILLS)
//            .depth(2.3f)
//            .scale(0.00003f)
                .temperature(0.2f)
                .downfall(0.0f)
                .effects(
                    BiomeEffects.Builder()
                    .waterColor(0x4b_96_db)
                    .waterFogColor(0x4b_96_db)
                    .fogColor(0xbf_a6_5c)
                    .skyColor(0x45_69_fa)
                    .build())
                .spawnSettings(spawnSettings.build())
                .generationSettings(generationSettings.build())
                .build()
        }
    }

}