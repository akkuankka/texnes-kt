package es.headbe.texnes.registry.generation

import es.headbe.texnes.registry.blocks.Blocks
import es.headbe.texnes.util.*
import es.headbe.texnes.worldgen.Biomes
import es.headbe.texnes.worldgen.EvaporitePatch
import es.headbe.texnes.worldgen.MixedOreConfig
import es.headbe.texnes.worldgen.MixedOreFeature
import es.headbe.texnes.worldgen.MixedOreFeature.Companion.configure
import net.fabricmc.fabric.api.biome.v1.BiomeModifications
import net.fabricmc.fabric.api.biome.v1.BiomeSelectors
import net.minecraft.block.Block
import net.minecraft.block.BlockState
import net.minecraft.entity.passive.PandaEntity
import net.minecraft.util.Identifier
import net.minecraft.util.collection.DataPool
import net.minecraft.util.math.intprovider.UniformIntProvider
import net.minecraft.util.registry.RegistryEntry
import net.minecraft.util.registry.BuiltinRegistries
import net.minecraft.util.registry.Registry
import net.minecraft.util.registry.RegistryKey
import net.minecraft.world.biome.*
import net.minecraft.world.gen.GenerationStep
import net.minecraft.world.gen.YOffset
import net.minecraft.world.gen.feature.*
import net.minecraft.world.gen.placementmodifier.*
import net.minecraft.world.gen.stateprovider.BlockStateProvider
import net.minecraft.world.gen.stateprovider.WeightedBlockStateProvider

object Generation {



    val playaKey: RegistryKey<Biome> = RegistryKey.of(Registry.BIOME_KEY, ident("playa"))
    val saltFlatsKey: RegistryKey<Biome> = RegistryKey.of(Registry.BIOME_KEY, ident("salt_flats"))

    private fun stoneOreVein(block: Block, size: i32): ConfiguredFeature<*, *> = ConfiguredFeature(
        Feature.ORE, OreFeatureConfig(
            OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
            block.defaultState,
            size
        )
    )

    private val mixedGalenaVein = MixedOreFeature.mixedOre.configure(MixedOreConfig(
        UniformIntProvider.create(10, 15),
        listOf(MixedOreConfig.Target(OreConfiguredFeatures.STONE_ORE_REPLACEABLES,
            WeightedBlockStateProvider(DataPool.builder<BlockState?>()
                .add(Blocks.leadOre.defaultState, 3)
            .add(Blocks.silverOre.defaultState, 1))))))

    // TODO deepslate ores

    fun registerAll() {
        // features
        Registry.register(Registry.FEATURE, ident("evaporite_patch"), EvaporitePatch.evaporitePatch)
        Registry.register(Registry.FEATURE, ident("mixed_ore"), MixedOreFeature.mixedOre)

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

        val tinOreKey = configuredFeatureKey("tin_ore")
        val tinOre = registerConfiguredFeature(tinOreKey, stoneOreVein(Blocks.tinOre, 8))
            .setConfiguredFeaturePlacement(tinOreKey.value, listOf(
                CountPlacementModifier.of(1),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.trapezoid(YOffset.fixed(3), YOffset.fixed(80))
            ))
        val galenaOreKey = configuredFeatureKey("galena_ore")
        val galenaOre = registerConfiguredFeature(galenaOreKey, mixedGalenaVein)
            .setConfiguredFeaturePlacement(galenaOreKey.value, listOf(
                CountPlacementModifier.of(1),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.trapezoid(YOffset.fixed(3), YOffset.fixed(80))
            ))
        val zincOreKey = configuredFeatureKey("zinc_ore")
        val zincOre = registerConfiguredFeature(zincOreKey, stoneOreVein(Blocks.zincOre, 5))
            .setConfiguredFeaturePlacement(zincOreKey.value, listOf(
                CountPlacementModifier.of(1),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.trapezoid(YOffset.fixed(3), YOffset.fixed(80))
            ))
        val arsenicOreKey = configuredFeatureKey("arsenic_ore")
        val arsenicOre = registerConfiguredFeature(arsenicOreKey, stoneOreVein(Blocks.arsenicOre, 14))
            .setConfiguredFeaturePlacement(arsenicOreKey.value, listOf(
                CountPlacementModifier.of(1),
                SquarePlacementModifier.of(),
                HeightRangePlacementModifier.trapezoid(YOffset.fixed(3), YOffset.fixed(80))
            ))

        // biomes
       Registry.register(BuiltinRegistries.BIOME, saltFlatsKey.value, Biomes.saltFlats)
       Registry.register(BuiltinRegistries.BIOME, playaKey.value, Biomes.playa)

        // add features to biomes

        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey), GenerationStep.Feature.UNDERGROUND_ORES, boraxPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey, saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, gypsumPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(playaKey, saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, natronPatch)
        BiomeModifications.addFeature(BiomeSelectors.includeByKey(saltFlatsKey), GenerationStep.Feature.UNDERGROUND_ORES, nitrePatch)

        // add ores
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, galenaOre)
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, zincOre)
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, tinOre)
        BiomeModifications.addFeature(BiomeSelectors.foundInOverworld(), GenerationStep.Feature.UNDERGROUND_ORES, arsenicOre)

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