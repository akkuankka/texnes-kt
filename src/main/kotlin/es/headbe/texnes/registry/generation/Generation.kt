package es.headbe.texnes.registry.generation

import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.*
import es.headbe.texnes.worldgen.Biomes
import es.headbe.texnes.worldgen.EvaporitePatch
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.util.Identifier
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.*
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.DefaultBiomeFeatures
import net.minecraft.world.gen.feature.PlacedFeature
import net.minecraft.world.gen.feature.PlacedFeatures
import net.minecraft.world.gen.placementmodifier.*

object Generation {



    val playaKey: RegistryKey<Biome> = RegistryKey.of(Registry.BIOME_KEY, ident("playa"))
    val saltFlatsKey: RegistryKey<Biome> = RegistryKey.of(Registry.BIOME_KEY, ident("salt_flats"))

    fun registerAll() {
        // features
        Registry.register(Registry.FEATURE, ident("evaporite_patch"), EvaporitePatch.evaporitePatch)

        val evaporitePlacementMods = {chance: Int ->
            listOf(PlacedFeatures.OCEAN_FLOOR_WG_HEIGHTMAP, SquarePlacementModifier.of(), RarityFilterPlacementModifier.of(chance), BiomePlacementModifier.of())
        }


        // feature placement
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


        // biomes
       Registry.register(BuiltinRegistries.BIOME, saltFlatsKey.value, Biomes.saltFlats)
       Registry.register(BuiltinRegistries.BIOME, playaKey.value, Biomes.playa)

        // add biomes to climate zones

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey), GenerationStep.Feature.UNDERGROUND_ORES, boraxPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey, saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, gypsumPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey, saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, natronPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, nitrePatch)

    }

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

}