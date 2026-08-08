package com.tic3nh.compat.gtceu;

import java.util.Map;

public final class TierMap {

    private TierMap() {}
    // gtceu harvest level -> our tier, on gtnh's ladder: a material's tool sits one tier above the
    // ore of the same name, so gtceu 2 (iron, bronze) lands on tin/redstone, not iron.
    private static final String[][] BANDS = {
            { "stone" },
            { "copper" },
            { "tin", "redstone" },
            { "obsidian" },
            { "ardite" },
            { "cobalt" },
            { "manyullyn" },
            { "darconite" },
    };

    private static final int[] CUTS = { 512, 1280 };

    private static final Map<String, String> OVERRIDES = Map.of(
            "tin",            "tin",
            "darconite",      "darconite",
            "monium",         "monium",
            "trinium",        "monium",
            "naquadah",       "ardite",
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
