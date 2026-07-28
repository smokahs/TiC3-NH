package com.tic3nh.config;

import java.util.List;

import net.minecraftforge.common.ForgeConfigSpec;

public final class Cfg {

    private Cfg() {}

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue NERF_VANILLA_TOOLS;
    public static final ForgeConfigSpec.BooleanValue NERF_VANILLA_HOES;
    public static final ForgeConfigSpec.BooleanValue NERF_VANILLA_SWORDS;
    public static final ForgeConfigSpec.BooleanValue DISABLE_STONE_TOOLS;
    public static final ForgeConfigSpec.BooleanValue EXCLUDED_IS_WHITELIST;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_TOOLS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_MOD_TOOLS;

    public static final ForgeConfigSpec.IntValue DURABILITY_PERCENTAGE;
    public static final ForgeConfigSpec.BooleanValue REPAIR_MODIFIER_PENALTY;

    public static final ForgeConfigSpec.BooleanValue STRICT_TIERS;
    public static final ForgeConfigSpec.BooleanValue GTNH_STATIONS;

    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();

        b.comment("Make non-Tinkers tools useless, forcing players onto Tinkers tools.")
                .push("nerf");

        NERF_VANILLA_TOOLS = b.comment("Non-Tinkers pickaxes/axes/shovels/hoes mine like a bare hand.")
                .define("nerfVanillaTools", false);

        NERF_VANILLA_HOES = b.comment("Include hoes in the nerf.")
                .define("nerfVanillaHoes", false);

        NERF_VANILLA_SWORDS = b.comment("Non-Tinkers swords deal bare-hand damage.")
                .define("nerfVanillaSwords", false);

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

        DURABILITY_PERCENTAGE = b.comment("Durability of all tools/armor, as a percent. 75 = GTNH's -25%.")
                .defineInRange("durabilityPercentage", 100, 1, 100);

        REPAIR_MODIFIER_PENALTY = b.comment("Tools with used upgrade slots cost more to repair.")
                .define("repairModifierPenalty", false);

        b.pop();

        b.comment("Mining tier gating.").push("tiers");

        STRICT_TIERS = b.comment(
                "Pull vanilla ores back out of the vanilla harvest tags so the custom tiers gate them.",
                "Iron ore then needs Copper, diamond ore needs Iron, obsidian needs Obsidian.")
                .define("strictTiers", false);

        b.pop();

        b.comment("Tinkers table recipes.").push("stations");

        GTNH_STATIONS = b.comment(
                "Re-gate the Tinkers tables with GTNH recipes.")
                .define("gtnhStationRecipes", false);

        b.pop();
        SPEC = b.build();
    }

    // attribute events fire before the config file is read, reading a value then throws
    public static boolean loaded() {
        return SPEC.isLoaded();
    }

    private static boolean isResourceId(Object o) {
        return o instanceof String s && !s.isBlank();
    }
}
