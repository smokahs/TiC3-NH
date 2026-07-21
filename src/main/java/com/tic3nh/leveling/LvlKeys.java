package com.tic3nh.leveling;

import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import net.minecraft.resources.ResourceLocation;

import com.tic3nh.TiC3NH;

public final class LvlKeys {

    private LvlKeys() {}

    public static final ResourceLocation TOOL_XP = new ResourceLocation(TiC3NH.MOD_ID, "tool_xp");

    public static final ResourceLocation TOOL_LEVEL = new ResourceLocation(TiC3NH.MOD_ID, "tool_level");

    public static final int MAX_LEVEL = 99;

    public static final float XP_PER_LEVEL_MULTIPLIER = 1.20f;

    public static final float MINING_SPEED_DIVIDER = 2.4f;

    public static final ResourceLocation XP_REQ_REF = new ResourceLocation(TiC3NH.MOD_ID, "xp_req_ref");

    public static final Set<Integer> RANDOM_BONUS_LEVELS = Set.of(2, 3, 4, 5, 6);

    public static final int USAGE_BONUS_WEIGHT = 70;

    private static final NavigableSet<Integer> SLOT_LEVELS = new TreeSet<>(List.of(
            2, 3, 5, 7, 9, 11, 14, 17, 20, 24, 28, 32, 36, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95));

    public static int slotsForLevel(int level) {
        return SLOT_LEVELS.headSet(level, true).size();
    }
}
