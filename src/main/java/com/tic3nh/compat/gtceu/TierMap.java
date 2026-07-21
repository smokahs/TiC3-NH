package com.tic3nh.compat.gtceu;

import java.util.Map;

public final class TierMap {

    private TierMap() {}

    private static final String[] BY_LEVEL = {
            "stone",
            "copper",
            "iron",
            "obsidian",
            "ardite",
            "manyullyn",
            "monium",
    };

    private static final Map<String, String> OVERRIDES = Map.of(
            "tin",       "tin",
            "darconite", "darconite",
            "monium",    "monium",
            "trinium",   "cobalt",
            "naquadah",  "cobalt"
    );

    public static String tierId(String materialName, int harvestLevel) {
        String tier = OVERRIDES.get(materialName);
        if (tier == null) {
            int lvl = Math.max(0, Math.min(harvestLevel, BY_LEVEL.length - 1));
            tier = BY_LEVEL[lvl];
        }
        return "tic3nh:" + tier;
    }
}
