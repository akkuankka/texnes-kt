package es.headbe.texnes.registry.generation

import com.google.common.collect.ImmutableList
import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.*
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.util.Identifier
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.Heightmap
import net.minecraft.world.biome.*
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.PlacedFeature
import net.minecraft.world.gen.feature.PlacedFeatures
import net.minecraft.world.gen.placementmodifier.*

object Generation {

    private val saltFlats: Biome = run {
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
    private val playa: Biome = run {
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

//    private fun configureEvaporitePatch(block: BlockState, chance: Int): ConfiguredFeature<EvaporitePatchConfig, *> {
//        return EvaporitePatch.evaporitePatch.configure(
//            EvaporitePatchConfig(
//            UniformIntProvider.create(2, 12),
//            SimpleBlockStateProvider(block),
//                ImmutableList.of(Blocks.saltBlock.defaultState, Blocks.gypsumBlock.defaultState)
//        )
//        )
//    }
    private fun configuredFeatureKey(name: String): RegistryKey<ConfiguredFeature<*, *>> {
       return RegistryKey.of(Registry.CONFIGURED_FEATURE_KEY, ident(name))
    }
     private fun registerConfiguredFeature(key: RegistryKey<ConfiguredFeature<*, *>>, feature: ConfiguredFeature<*, *>):
     RegistryEntry<ConfiguredFeature<*, *>> {
         Registry.register(BuiltinRegistries.CONFIGURED_FEATURE, key.value, feature)
         return BuiltinRegistries.CONFIGURED_FEATURE.getOrCreateEntry(BuiltinRegistries.CONFIGURED_FEATURE.getKey(feature).orElseThrow())
     }

    private fun  RegistryEntry<ConfiguredFeature<*, *>>.setConfiguredFeaturePlacement(id: Identifier, mods: List<PlacementModifier>): RegistryKey<PlacedFeature> {
        val placedFeature = PlacedFeature(this, mods)
        val key = RegistryKey.of(Registry.PLACED_FEATURE_KEY, id)
        Registry.register(BuiltinRegistries.PLACED_FEATURE, key.value, placedFeature)
        return key
    }

    val playaKey = RegistryKey.of(Registry.BIOME_KEY, ident("playa"))
    val saltFlatsKey = RegistryKey.of(Registry.BIOME_KEY, ident("salt_flats"))

    fun registerAll() {
        // features
        Registry.register(Registry.FEATURE, ident("evaporite_patch"), EvaporitePatch.evaporitePatch)

        val evaporitePlacementMods = {chance: Int ->
            listOf(PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, SquarePlacementModifier.of(), RarityFilterPlacementModifier.of(chance), BiomePlacementModifier.of())
        }

        // -- + configurations
        val boraxPatchKey = configuredFeatureKey("borax_patch")
        val boraxPatch = registerConfiguredFeature(boraxPatchKey, EvaporitePatch.configureWith(Blocks.boraxBlock.defaultState))
            .setConfiguredFeaturePlacement(boraxPatchKey.value, evaporitePlacementMods(1))
        val gypsumPatchKey = configuredFeatureKey("gypsum_patch")
        val gypsumPatch = registerConfiguredFeature(gypsumPatchKey, EvaporitePatch.configureWith(Blocks.gypsumBlock.defaultState))
            .setConfiguredFeaturePlacement(gypsumPatchKey.value, evaporitePlacementMods(4))
        val natronPatchKey = configuredFeatureKey("natron_patch")
        val natronPatch = registerConfiguredFeature(natronPatchKey, EvaporitePatch.configureWith(Blocks.natronBlock.defaultState))
            .setConfiguredFeaturePlacement(natronPatchKey.value, evaporitePlacementMods(2))
        val nitrePatchKey = configuredFeatureKey("nitre_patch")
        val nitrePatch = registerConfiguredFeature(nitrePatchKey, EvaporitePatch.configureWith(Blocks.nitreBlock.defaultState))
            .setConfiguredFeaturePlacement(nitrePatchKey.value, evaporitePlacementMods(1))

        // surface builders

        // biomes
       Registry.register(BuiltinRegistries.BIOME, saltFlatsKey.value, saltFlats)
       Registry.register(BuiltinRegistries.BIOME, playaKey.value, playa)

        // add biomes to climate zones

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey), GenerationStep.Feature.UNDERGROUND_ORES, boraxPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey, saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, gypsumPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey, saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, natronPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, nitrePatch)




    }

}