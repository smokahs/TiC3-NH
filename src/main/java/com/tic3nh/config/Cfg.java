package com.tic3nh.config;

import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;

public final class Cfg {

    private Cfg() {}

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue NERF_VANILLA_TOOLS;
    public static final ForgeConfigSpec.BooleanValue NERF_VANILLA_HOES;
    public static final ForgeConfigSpec.BooleanValue DISABLE_STONE_TOOLS;
    public static final ForgeConfigSpec.BooleanValue EXCLUDED_IS_WHITELIST;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_TOOLS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_MOD_TOOLS;

    public static final ForgeConfigSpec.IntValue DURABILITY_PERCENTAGE;
    public static final ForgeConfigSpec.BooleanValue REPAIR_MODIFIER_PENALTY;

    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();

        b.comment("Make non-Tinkers tools useless, forcing players onto Tinkers tools.")
                .push("nerf");

        NERF_VANILLA_TOOLS = b.comment("Non-Tinkers pickaxes/axes/shovels/hoes mine like a bare hand.")
                .define("nerfVanillaTools", false);

        NERF_VANILLA_HOES = b.comment("Include hoes in the nerf.")
                .define("nerfVanillaHoes", false);

        DISABLE_STONE_TOOLS = b.comment("Always nerf stone tools, even with the nerf off.")
                .define("disableStoneTools", false);

        EXCLUDED_IS_WHITELIST = b.comment("true: only listed tools/mods are nerfed instead of exempted.")
                .define("excludedToolsIsWhitelist", false);

        EXCLUDED_TOOLS = b.comment("Item ids to exempt, e.g. \"minecraft:diamond_pickaxe\".")
                .defineListAllowEmpty("excludedTools", List.<String>of(), Cfg::isResourceId);

        EXCLUDED_MOD_TOOLS = b.comment("Mod ids to exempt, e.g. \"gtceu\" to keep GregTech tools working.")
                .defineListAllowEmpty("excludedModTools", List.<String>of(), Cfg::isResourceId);

        b.pop();

        b.comment("Repair and durability tweaks.").push("repair");

        DURABILITY_PERCENTAGE = b.comment("Durability of all tools/armor, as a percent. 80 = GTNH's -20%.")
                .defineInRange("durabilityPercentage", 100, 1, 100);

        REPAIR_MODIFIER_PENALTY = b.comment("Tools with used upgrade slots cost more to repair.")
                .define("repairModifierPenalty", false);

        b.pop();
        SPEC = b.build();
    }

    private static boolean isResourceId(Object o) {
        return o instanceof String s && !s.isBlank();
    }
}
