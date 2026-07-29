package com.tic3nh.leveling;

import net.minecraft.resources.ResourceLocation;

import com.tic3nh.TiC3NH;
import com.tic3nh.config.Cfg;

public final class LvlKeys {

    private LvlKeys() {}

    public static final ResourceLocation TOOL_XP = new ResourceLocation(TiC3NH.MOD_ID, "tool_xp");

    public static final ResourceLocation TOOL_LEVEL = new ResourceLocation(TiC3NH.MOD_ID, "tool_level");

    // hard ceiling: the skill-name lang table stops here, config cannot exceed it
    public static final int MAX_LEVEL = 99;

    public static final ResourceLocation XP_REQ_REF = new ResourceLocation(TiC3NH.MOD_ID, "xp_req_ref");

    // fingerprint of the tool's materials, used to spot a part replacement
    public static final ResourceLocation MATERIAL_REF = new ResourceLocation(TiC3NH.MOD_ID, "material_ref");

    public static int maxLevel() {
        return Math.min(MAX_LEVEL, Cfg.maxToolLevel());
    }

    public static float xpPerLevelMultiplier() {
        return Cfg.xpPerLevelMultiplier();
    }

    public static float miningSpeedDivider() {
        return Cfg.miningSpeedDivider();
    }

    public static boolean isBonusLevel(int level) {
        return Cfg.isBonusLevel(level);
    }

    public static int slotsForLevel(int level) {
        int slots = Cfg.slotsForLevel(level);
        return Cfg.loaded() ? slots + Cfg.EXTRA_MODIFIERS.get() : slots;
    }
}
