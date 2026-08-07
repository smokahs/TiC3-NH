package com.tic3nh.compat.gtceu;

import java.util.Map;

public final class TierMap {

    private TierMap() {}
    private static final String[][] BANDS = {
            { "stone" },
            { "copper" },
            { "iron", "tin", "redstone" },
            { "obsidian", "ardite" },
            { "cobalt" },
            { "manyullyn" },
            { "darconite" },
            { "monium" },
    };

    private static final int[] CUTS = { 512, 1280 };

    private static final Map<String, String> OVERRIDES = Map.of(
            "tin",            "tin",
            "darconite",      "darconite",
            "monium",         "monium",
            "trinium",        "cobalt",
            "naquadah",       "cobalt",
            "titanium",       "obsidian",
            "vanadium_steel", "obsidian"
    );

    public static String tierId(String materialName, int harvestLevel, int durability) {
        String tier = OVERRIDES.get(materialName);
        if (tier == null) {
            String[] band = BANDS[Math.max(0, Math.min(harvestLevel, BANDS.length - 1))];
            tier = band[step(durability, band.length)];
        }
        return "tic3nh:" + tier;
    }

    private static int step(int durability, int bandLength) {
        int step = 0;
        for (int cut : CUTS) {
            if (durability > cut) {
                step++;
            }
        }
        return Math.min(step, bandLength - 1);
    }
}
