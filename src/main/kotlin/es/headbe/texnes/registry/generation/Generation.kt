package es.headbe.texnes.registry.generation

import com.google.common.collect.ImmutableList
import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.*
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.fabricmc.fabric.api.biome.v1.OverworldBiomes
import net.fabricmc.fabric.api.biome.v1.OverworldClimate
import net.fabricmc.fabric.impl.biome.modification.BiomeSelectionContextImpl
import net.minecraft.block.BlockState
import net.minecraft.client.render.SkyProperties
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.*
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.stateprovider.SimpleBlockStateProvider
import net.minecraft.world.gen.surfacebuilder.SurfaceBuilder
import net.minecraft.world.gen.surfacebuilder.TernarySurfaceConfig

object Generation {
    private val evaporiteSurface = SurfaceBuilder.DEFAULT
        .withConfig(TernarySurfaceConfig(
            Blocks.saltBlock.defaultState,
            Blocks.saltBlock.defaultState,
            Blocks.natronBlock.defaultState
        ))

    private val saltFlats: Biome = run {
            //setting the mob spawns
            val spawnSettings = SpawnSettings.Builder()
            DefaultBiomeFeatures.addFarmAnimals(spawnSettings)
            DefaultBiomeFeatures.addMonsters(spawnSettings, 40, 30, 120)
            //generation
            var generationSettings = GenerationSettings.Builder()
                .surfaceBuilder(evaporiteSurface)
            //setting the mob spawns

            //generation
            Biome.Builder()
                .precipitation(Biome.Precipitation.NONE)
                .category(Biome.Category.DESERT)
                .depth(0.003f)
                .scale(0.0003f)
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
    private val playa: Biome = run {
        // what kind of mobs can spawn


        // what kind of mobs can spawn
        val spawnSettings = SpawnSettings.Builder()
        DefaultBiomeFeatures.addFarmAnimals(spawnSettings)
        DefaultBiomeFeatures.addMonsters(spawnSettings, 40, 30, 120)

        // generation &c.

        // generation &c.
        val generationSettings = GenerationSettings.Builder()
        generationSettings.surfaceBuilder(evaporiteSurface)
        DefaultBiomeFeatures.addDefaultLakes(generationSettings)
        DefaultBiomeFeatures.addDungeons(generationSettings)
        DefaultBiomeFeatures.addMineables(generationSettings)
        DefaultBiomeFeatures.addDefaultOres(generationSettings)
        DefaultBiomeFeatures.addSprings(generationSettings)
        DefaultBiomeFeatures.addFrozenTopLayer(generationSettings)

        Biome.Builder()
            .precipitation(Biome.Precipitation.NONE)
            .category(Biome.Category.EXTREME_HILLS)
            .depth(2.3f)
            .scale(0.00003f)
            .temperature(0.2f)
            .downfall(0.0f)
            .effects(BiomeEffects.Builder()
                .waterColor(0x4b_96_db)
                .waterFogColor(0x4b_96_db)
                .fogColor(0xbf_a6_5c)
                .skyColor(0x45_69_fa)
                .build())
            .spawnSettings(spawnSettings.build())
            .generationSettings(generationSettings.build())
            .build()
    }

    private fun configureEvaporitePatch(block: BlockState, chance: Int): ConfiguredFeature<EvaporitePatchConfig, *> {
        return EvaporitePatch.evaporitePatch.configure(
            EvaporitePatchConfig(
            UniformIntProvider.create(2, 12),
            SimpleBlockStateProvider(block),
                ImmutableList.of(Blocks.saltBlock.defaultState, Blocks.gypsumBlock.defaultState)
        )
        )
    }
    private fun configuredFeatureKey(name: String): RegistryKey<ConfiguredFeature<*, *>> {
       return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, ident(name))
    }
     private fun registerConfiguredFeature(key: RegistryKey<ConfiguredFeature<*, *>>, feature: ConfiguredFeature<*, *>) {
         Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.value, feature)
     }

    fun registerAll() {
        // features
        Registry.register(Registry.FEATURE, ident("evaporite_patch"), EvaporitePatch.evaporitePatch)

        // -- + configurations
        val boraxPatch = configuredFeatureKey("borax_patch")
        registerConfiguredFeature(boraxPatch, EvaporitePatch.configureWith(Blocks.boraxBlock.defaultState))
        val gypsumPatch = configuredFeatureKey("gypsum_patch")
        registerConfiguredFeature(gypsumPatch, EvaporitePatch.configureWith(Blocks.gypsumBlock.defaultState))
        val natronPatch = configuredFeatureKey("natron_patch")
        registerConfiguredFeature(natronPatch, EvaporitePatch.configureWith(Blocks.natronBlock.defaultState))
        val nitrePatch = configuredFeatureKey("nitre_patch")
        registerConfiguredFeature(nitrePatch, EvaporitePatch.configureWith(Blocks.nitreBlock.defaultState))

        // surface builders
        Registry.register(BuiltinRegistries.CONFIGURED_SURFACE_BUILDER, ident("evaporite"), evaporiteSurface)

        // biomes
       val saltFlatsKey = RegistryKey.of(Registry.BIOME_KEY, ident("salt_flats"))
       Registry.register(BuiltinRegistries.BIOME, saltFlatsKey.value, saltFlats)
        val playaKey = RegistryKey.of(Registry.BIOME_KEY, ident("playa"))
        Registry.register(BuiltinRegistries.BIOME, playaKey.value, playa)

        // add biomes to climate zones
        OverworldBiomes.addContinentalBiome(saltFlatsKey, OverworldClimate.DRY, 0.5)
        OverworldBiomes.addContinentalBiome(playaKey, OverworldClimate.DRY, 0.5)

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey), GenerationStep.Feature.UNDERGROUND_ORES, boraxPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey, saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, gypsumPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey, saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, natronPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, nitrePatch)




    }

}