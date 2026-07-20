package com.tic3nh.mininglevel;

import net.minecraft.resources.ResourceLocation;

import com.tic3nh.TiC3NH;

/**
 * Persistent-data keys for the mining-level system (DESIGN.md §2). Stored on each tool via
 * {@code tool.getPersistentData()} ({@code ModDataNBT}); survives rebuilds, reset when the head part
 * is swapped (rebuild wipes persistent data), reproducing GTNH's "replace head resets mining XP".
 */
public final class MiningKeys {

    private MiningKeys() {}

    /** Boolean: the tool's one-time mining-level boost has fired → run at full material tier. */
    public static final ResourceLocation BOOSTED = new ResourceLocation(TiC3NH.MOD_ID, "boosted");

    /** Int: accumulated mining-boost XP (blocks harvested) toward the one-time boost. */
    public static final ResourceLocation BOOST_XP = new ResourceLocation(TiC3NH.MOD_ID, "boost_xp");

    /** Base blocks-to-boost, multiplied by (tier index + 1). Tunable. */
    public static final int BASE_BOOST_XP = 100;
}
