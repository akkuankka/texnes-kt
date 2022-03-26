package es.headbe.texnes.worldgen

import com.mojang.serialization.Codec
import net.minecraft.block.BlockState
import net.minecraft.util.math.BlockPos
import net.minecraft.util.math.ChunkSectionPos
import net.minecraft.util.math.MathHelper
import net.minecraft.world.ChunkSectionCache
import net.minecraft.world.Heightmap
import net.minecraft.world.StructureWorldAccess
import net.minecraft.world.gen.feature.ConfiguredFeature
import net.minecraft.world.gen.feature.Feature
import net.minecraft.world.gen.feature.FeatureConfig
import net.minecraft.world.gen.feature.util.FeatureContext
import java.util.*
import java.util.function.Function
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class MixedOreFeature(codec: Codec<MixedOreConfig>) : Feature<MixedOreConfig>(codec) {


    override fun generate(context: FeatureContext<MixedOreConfig>): Boolean {
        val random = context.random
        val blockPos = context.origin
        val structureWorldAccess = context.world
        val mixedOreConfig = context.config as MixedOreConfig
        val randomPi = random.nextDouble() * 3.1415927
        val size = mixedOreConfig.size.get(random)
        val eighth = size / 8.0
        val ceilinged = MathHelper.ceil((size.toFloat() / 16.0f * 2.0f + 1.0f) / 2.0f)
        val d = blockPos.x.toDouble() + sin(randomPi) * eighth
        val e = blockPos.x.toDouble() - sin(randomPi) * eighth
        val h = blockPos.z.toDouble() + cos(randomPi) * eighth
        val j = blockPos.z.toDouble() - cos(randomPi) * eighth
        val l = (blockPos.y + random.nextInt(3) - 2).toDouble()
        val m = (blockPos.y + random.nextInt(3) - 2).toDouble()
        val n = blockPos.x - MathHelper.ceil(eighth) - ceilinged
        val o = blockPos.y - 2 - ceilinged
        val p = blockPos.z - MathHelper.ceil(eighth) - ceilinged
        val q = 2 * (MathHelper.ceil(eighth) + ceilinged)
        val r = 2 * (2 + ceilinged)
        for (s in n..n + q) {
            for (t in p..p + q) {
                if (o <= structureWorldAccess.getTopY(Heightmap.Type.OCEAN_FLOOR_WG, s, t)) {
                    return generateVeinPart(
                        structureWorldAccess,
                        random,
                        mixedOreConfig,
                        d,
                        e,
                        h,
                        j,
                        l,
                        m,
                        n,
                        o,
                        p,
                        q,
                        r
                    )
                }
            }
        }
        return false
    }

    companion object {
        val mixedOre = MixedOreFeature(MixedOreConfig.codec)
        fun <FC : FeatureConfig> Feature<FC>.configure(config: FC): ConfiguredFeature<FC, Feature<FC>> = ConfiguredFeature(this, config)
    }

    private fun generateVeinPart(
        world: StructureWorldAccess,
        random: Random,
        config: MixedOreConfig,
        startX: Double,
        endX: Double,
        startZ: Double,
        endZ: Double,
        startY: Double,
        endY: Double,
        x: Int,
        y: Int,
        z: Int,
        horizontalSize: Int,
        verticalSize: Int
    ): Boolean {
        var i = 0
        val bitSet = BitSet(horizontalSize * verticalSize * horizontalSize)
        val position = BlockPos.Mutable()
        val size = config.size.get(random)
        val doubles = DoubleArray(size * 4)
        var outX: Double
        var outY: Double
        var outZ: Double
        var randomFallOff: Double = random.nextDouble() * size.toDouble() / 16.0
        var currentN = 0
        while (currentN < size) {
            val fractionOut = currentN.toDouble() / size.toDouble()
            outX = MathHelper.lerp(fractionOut, startX, endX)
            outY = MathHelper.lerp(fractionOut, startY, endY)
            outZ = MathHelper.lerp(fractionOut, startZ, endZ)
            randomFallOff = random.nextDouble() * size.toDouble() / 16.0
            val trigRandom = ((MathHelper.sin(3.1415927f * fractionOut.toFloat()) + 1.0f).toDouble() * randomFallOff + 1.0) / 2.0
            doubles[currentN * 4 + 0] = outX
            doubles[currentN * 4 + 1] = outY
            doubles[currentN * 4 + 2] = outZ
            doubles[currentN * 4 + 3] = trigRandom
            ++currentN
        }
        var currentM: Int
        currentN = 0
        while (currentN < size - 1) {
            if (doubles[currentN * 4 + 3] > 0.0) {
                currentM = currentN + 1
                while (currentM < size) {
                    if (doubles[currentM * 4 + 3] > 0.0) {
                        outX = doubles[currentN * 4 + 0] - doubles[currentM * 4 + 0]
                        outY = doubles[currentN * 4 + 1] - doubles[currentM * 4 + 1]
                        outZ = doubles[currentN * 4 + 2] - doubles[currentM * 4 + 2]
                        randomFallOff = doubles[currentN * 4 + 3] - doubles[currentM * 4 + 3]
                        if (randomFallOff * randomFallOff > outX * outX + outY * outY + outZ * outZ) {
                            if (randomFallOff > 0.0) {
                                doubles[currentM * 4 + 3] = -1.0
                            } else {
                                doubles[currentN * 4 + 3] = -1.0
                            }
                        }
                    }
                    ++currentM
                }
            }
            ++currentN
        }
        val chunkSectionCache = ChunkSectionCache(world)
        try {
            currentM = 0
            while (currentM < size) {
                outX = doubles[currentM * 4 + 3]
                if (outX >= 0.0) {
                    outY = doubles[currentM * 4 + 0]
                    outZ = doubles[currentM * 4 + 1]
                    randomFallOff = doubles[currentM * 4 + 2]
                    val diffYX = max(MathHelper.floor(outY - outX), x)
                    val diffZX = max(MathHelper.floor(outZ - outX), y)
                    val randSubXZ = max(MathHelper.floor(randomFallOff - outX), z)
                    val sumYX = max(MathHelper.floor(outY + outX), diffYX)
                    val sumZX = max(MathHelper.floor(outZ + outX), diffZX)
                    val randPosXZ = max(MathHelper.floor(randomFallOff + outX), randSubXZ)
                    for (yx in diffYX..sumYX) {
                        val yogYX = (yx.toDouble() + 0.5 - outY) / outX // I don't know what yog does
                        if (yogYX * yogYX < 1.0) {
                            for (zx in diffZX..sumZX) {
                                val yogZX = (zx.toDouble() + 0.5 - outZ) / outX
                                if (yogYX * yogYX + yogZX * yogZX < 1.0) {
                                    for (rxz in randSubXZ..randPosXZ) {
                                        val yogRZX = (rxz.toDouble() + 0.5 - randomFallOff) / outX
                                        if (yogYX * yogYX + yogZX * yogZX + yogRZX * yogRZX < 1.0 && !world.isOutOfHeightLimit(zx)) {
                                            val allTogetherNow =
                                                yx - x + (zx - y) * horizontalSize + (rxz - z) * horizontalSize * verticalSize
                                            if (!bitSet[allTogetherNow]) {
                                                bitSet.set(allTogetherNow)
                                                position[yx, zx] = rxz
                                                if (world.isValidForSetBlock(position)) {
                                                    val chunkSection = chunkSectionCache.getSection(position)
                                                    if (chunkSection != null) {
                                                        val localX = ChunkSectionPos.getLocalCoord(yx)
                                                        val localY = ChunkSectionPos.getLocalCoord(zx)
                                                        val localZ = ChunkSectionPos.getLocalCoord(rxz)
                                                        val blockState = chunkSection.getBlockState(localX, localY, localZ)
                                                        for ( target in config.targets) {
                                                            Objects.requireNonNull(chunkSectionCache)
                                                            if (shouldPlace(blockState,
                                                                    { pos: BlockPos? ->
                                                                        chunkSectionCache.getBlockState(
                                                                            pos
                                                                        )
                                                                    }, random, config, target, position
                                                                )
                                                            ) {
                                                                chunkSection.setBlockState(
                                                                    localX,
                                                                    localY,
                                                                    localZ,
                                                                    target.blocks.getBlockState(random, position),
                                                                    false
                                                                )
                                                                ++i
                                                                break
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                ++currentM
            }
        } catch (e: Throwable) {
            try {
                chunkSectionCache.close()
            } catch (cantCloseSection: Throwable) {
                e.addSuppressed(cantCloseSection)
            }
            throw e
        }
        chunkSectionCache.close()
        return i > 0
    }

    fun shouldPlace(
        state: BlockState?,
        posToState: Function<BlockPos?, BlockState>?,
        random: Random,
        config: MixedOreConfig,
        target: MixedOreConfig.Target,
        pos: BlockPos.Mutable?
    ): Boolean {
        return if (!target.test.test(state, random)) {
            false
        } else {
            !isExposedToAir(posToState, pos)
        }
    }

}