package com.tic3nh.compat.gtceu;

import java.util.Map;

import javax.annotation.Nullable;

import com.tic3nh.compat.gtceu.GtAccess.GtMat;

// gtceu leaves a few metals without a ToolProperty, so the scan never sees them. gtnh gave them
// tinker stats anyway, so we hand them the numbers here and let the rest of the bridge treat them
// like any other scanned material.
public final class Forced {

    private Forced() {}

    private record Stats(int durability, float harvestSpeed, float attackDamage, int harvestLevel,
                         int enchantability) {}

    private static final Map<String, Stats> TABLE = Map.of(
            "trinium", new Stats(2200, 8.0f, 4.0f, 7, 18));

    @Nullable
    public static GtMat build(String name, int rgb, boolean hasIngot) {
        Stats s = TABLE.get(name);
        if (s == null) {
            return null;
        }
        return new GtMat(name, s.durability(), 1, s.harvestSpeed(), s.attackDamage(), 1.0f,
                s.harvestLevel(), false, false, s.enchantability(), rgb, hasIngot);
    }
}
