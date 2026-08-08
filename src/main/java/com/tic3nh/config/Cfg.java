package com.tic3nh.config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;

import com.tic3nh.TiC3NH;
import com.tic3nh.leveling.Bonuses;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class Cfg {

    private Cfg() {}

    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue NEW_HORIZONS_MODE;

    // nerf — IguanaTweaks "tweaks" disableRegular* plus the "allowedtools" exclusion lists
    public static final ForgeConfigSpec.BooleanValue NERF_REGULAR_TOOLS;
    public static final ForgeConfigSpec.BooleanValue NERF_REGULAR_HOES;
    public static final ForgeConfigSpec.BooleanValue NERF_REGULAR_SWORDS;
    public static final ForgeConfigSpec.BooleanValue NERF_REGULAR_BOWS;
    public static final ForgeConfigSpec.BooleanValue DISABLE_STONE_TOOLS;
    public static final ForgeConfigSpec.BooleanValue EXCLUSION_IS_WHITELIST;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_TOOLS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_SWORDS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_HOES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_BOWS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> EXCLUDED_MODS;

    // repair — IguanaTweaks "harvestleveltweaks" material percentages
    public static final ForgeConfigSpec.IntValue DURABILITY_PERCENTAGE;
    public static final ForgeConfigSpec.IntValue MINING_SPEED_PERCENTAGE;
    public static final ForgeConfigSpec.BooleanValue REPAIR_MODIFIER_PENALTY;
    public static final ForgeConfigSpec.DoubleValue REPAIR_PENALTY_PER_MODIFIER;
    public static final ForgeConfigSpec.DoubleValue REPAIR_PENALTY_FLOOR;

    public static final ForgeConfigSpec.BooleanValue STRICT_TIERS;
    public static final ForgeConfigSpec.BooleanValue GTNH_STATIONS;

    // leveling — IguanaTweaks "toolleveling"
    public static final ForgeConfigSpec.BooleanValue TOOL_LEVELING;
    public static final ForgeConfigSpec.IntValue MAX_TOOL_LEVEL;
    public static final ForgeConfigSpec.DoubleValue XP_PER_LEVEL_MULTIPLIER;
    public static final ForgeConfigSpec.IntValue XP_REQUIRED_TOOLS_PERCENTAGE;
    public static final ForgeConfigSpec.IntValue XP_REQUIRED_WEAPONS_PERCENTAGE;
    public static final ForgeConfigSpec.DoubleValue MINING_SPEED_DIVIDER;
    public static final ForgeConfigSpec.IntValue XP_PENALTY;
    public static final ForgeConfigSpec.IntValue EXTRA_MODIFIERS;
    public static final ForgeConfigSpec.IntValue STARTING_UPGRADE_SLOTS;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Number>> MODIFIERS_AT_LEVELS;
    public static final ForgeConfigSpec.BooleanValue RANDOM_BONUSES;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Number>> BONUSES_AT_LEVELS;
    public static final ForgeConfigSpec.BooleanValue USEFUL_BONUSES;
    public static final ForgeConfigSpec.BooleanValue COMPLETELY_RANDOM_BONUSES;
    public static final ForgeConfigSpec.IntValue USAGE_BONUS_WEIGHT;

    // boost — IguanaTweaks "pickleveling" plus its part-replacement penalties
    public static final ForgeConfigSpec.BooleanValue PICKAXE_BOOST_REQUIRED;
    public static final ForgeConfigSpec.BooleanValue ALLOW_LEVELING_BOOST;
    public static final ForgeConfigSpec.BooleanValue ADD_MOB_HEAD_BOOST;
    public static final ForgeConfigSpec.DoubleValue XP_PER_BOOST_LEVEL_MULTIPLIER;
    public static final ForgeConfigSpec.IntValue XP_REQUIRED_PICK_BOOST_PERCENTAGE;
    public static final ForgeConfigSpec.IntValue PICK_BOOST_XP_PENALTY;
    public static final ForgeConfigSpec.BooleanValue BOOST_LOST_ON_HEAD_CHANGE;
    public static final ForgeConfigSpec.BooleanValue REMOVE_MOB_HEAD;

    public static final ForgeConfigSpec.IntValue PAPER_PER_STRIP_CHANCE;
    public static final ForgeConfigSpec.IntValue BARK_REGROW_CHANCE;

    // GTNH's schedule: every level to 3, every 2 to 11, every 3 to 20, every 4 to 40, every 5 to 95,
    // then the cap itself, for the 26 modifiers the wiki quotes at level 99
    private static final List<Integer> DEFAULT_MODIFIER_LEVELS = List.of(
            2, 3, 5, 7, 9, 11, 14, 17, 20, 24, 28, 32, 36, 40, 45, 50, 55, 60, 65, 70, 75, 80, 85, 90, 95, 99);

    private static final List<Integer> DEFAULT_BONUS_LEVELS = List.of(2, 3, 4, 5, 6);

    // what New Horizons Mode supplies for a key the pack has left alone, keyed by identity
    private static final Map<ForgeConfigSpec.ConfigValue<?>, Object> HORIZONS = new IdentityHashMap<>();

    static {
        ForgeConfigSpec.Builder b = new ForgeConfigSpec.Builder();

        NEW_HORIZONS_MODE = b.comment(
                "Play the GregTech New Horizons default config options.",
                "Values are held while on, so turn it off to change one.")
                .define("newHorizonsMode", false);

        b.comment("Make regular (non-Tinkers) tools useless, forcing players onto Tinkers tools.",
                  "GTNH runs every one of these on with empty exclusion lists; on 1.20.1 GregTech's own",
                  "tools are vanilla DiggerItems, so add \"gtceu\" to excludedMods if you nerf tools.")
                .push("nerf");

        NERF_REGULAR_TOOLS = preset(b.comment("Regular pickaxes/axes/shovels/hoes mine like a bare hand.")
                .define("nerfRegularTools", false), true);

        NERF_REGULAR_HOES = preset(b.comment("Include hoes in the nerf.")
                .define("nerfRegularHoes", false), true);

        NERF_REGULAR_SWORDS = preset(b.comment("Regular swords deal bare-hand damage.")
                .define("nerfRegularSwords", false), true);

        NERF_REGULAR_BOWS = b.comment("Regular bows and crossbows cannot be drawn.")
                .define("nerfRegularBows", false);

        DISABLE_STONE_TOOLS = preset(b.comment("Always nerf stone tools, even with the nerf off.")
                .define("disableStoneTools", false), true);

        EXCLUSION_IS_WHITELIST = b.comment(
                "false: the lists below are exempt from the nerf (blacklist mode, like GTNH).",
                "true: only the listed tools/mods are nerfed.")
                .define("exclusionIsWhitelist", false);

        // wood stays usable so a fresh world can still reach a crafting station
        EXCLUDED_TOOLS = preset(b.comment("Digging tool ids, e.g. \"minecraft:diamond_pickaxe\".")
                .defineListAllowEmpty("excludedTools", List.<String>of(), Cfg::isResourceId),
                List.of("minecraft:wooden_pickaxe", "minecraft:wooden_axe", "minecraft:wooden_shovel",
                        "hungeroverhauled:healing_axe"));

        EXCLUDED_SWORDS = preset(b.comment("Sword ids, e.g. \"minecraft:diamond_sword\".")
                .defineListAllowEmpty("excludedSwords", List.<String>of(), Cfg::isResourceId),
                List.of("minecraft:wooden_sword"));

        EXCLUDED_HOES = preset(b.comment("Hoe ids, e.g. \"minecraft:diamond_hoe\".")
                .defineListAllowEmpty("excludedHoes", List.<String>of(), Cfg::isResourceId),
                List.of("minecraft:wooden_hoe"));

        EXCLUDED_BOWS = b.comment("Bow/crossbow ids, e.g. \"minecraft:crossbow\".")
                .defineListAllowEmpty("excludedBows", List.<String>of(), Cfg::isResourceId);

        // GTNH leaves this empty, but GregTech's 1.20.1 tools are vanilla DiggerItems and would be nerfed too
        EXCLUDED_MODS = preset(
                b.comment("Mod ids covering every category, e.g. \"gtceu\" to keep GregTech tools working.")
                        .defineListAllowEmpty("excludedMods", List.<String>of(), Cfg::isResourceId),
                List.of("gtceu"));

        b.pop();

        b.comment("Repair and durability tweaks.").push("repair");

        DURABILITY_PERCENTAGE = preset(b.comment("Durability of all tools/armor, as a percent. 75 = GTNH's -25%.")
                .defineInRange("durabilityPercentage", 100, 1, 999), 75);

        MINING_SPEED_PERCENTAGE = b.comment("Mining speed of all tools, as a percent. GTNH runs 100.")
                .defineInRange("miningSpeedPercentage", 100, 1, 999);

        REPAIR_MODIFIER_PENALTY = preset(b.comment("Modified tools recover less durability per repair.")
                .define("repairModifierPenalty", false), true);

        REPAIR_PENALTY_PER_MODIFIER = b.comment(
                "Repair yield lost per applied modifier level. 0.1 = -10% each, so 3 modifiers repair at 70%.")
                .defineInRange("repairPenaltyPerModifier", 0.1d, 0.0d, 1.0d);

        REPAIR_PENALTY_FLOOR = b.comment("Worst repair multiplier the penalty is allowed to reach.")
                .defineInRange("repairPenaltyFloor", 0.7d, 0.0d, 1.0d);

        b.pop();

        b.comment("Mining tier gating.").push("tiers");

        STRICT_TIERS = preset(b.comment(
                "Pull vanilla ores and metal blocks out of the vanilla harvest tags so the custom tiers",
                "gate them. Iron ore then needs Iron, gold and redstone need Tin, diamond needs Redstone.")
                .define("strictTiers", false), true);

        b.pop();

        b.comment("Tinkers table recipes.").push("stations");

        GTNH_STATIONS = preset(b.comment(
                "Re-gate the Tinkers tables with GTNH recipes.")
                .define("gtnhStationRecipes", false), true);

        b.pop();

        b.comment("Tool leveling: every tool earns XP and gains upgrade slots.").push("leveling");

        TOOL_LEVELING = b.comment("Master switch. Off means tools never gain XP or levels.")
                .define("toolLeveling", true);

        MAX_TOOL_LEVEL = b.comment("Highest reachable tool level.")
                .defineInRange("maxToolLevel", 99, 1, 99);

        XP_PER_LEVEL_MULTIPLIER = b.comment("Exponential multiplier on required XP per level.")
                .defineInRange("xpPerLevelMultiplier", 1.20d, 1.0d, 9.99d);

        XP_REQUIRED_TOOLS_PERCENTAGE =
                preset(b.comment("XP required to level harvest tools, as a percent. GTNH runs 125.")
                        .defineInRange("xpRequiredToolsPercentage", 100, 1, 999), 125);

        XP_REQUIRED_WEAPONS_PERCENTAGE =
                preset(b.comment("XP required to level weapons, as a percent. GTNH runs 125.")
                        .defineInRange("xpRequiredWeaponsPercentage", 100, 1, 999), 125);

        MINING_SPEED_DIVIDER = b.comment("Mining speed is divided by this and added to the XP requirement.")
                .defineInRange("miningSpeedDivider", 2.4d, 0.1d, 100.0d);

        XP_PENALTY = b.comment(
                "Percent of current tool XP removed when a tool part is replaced. GTNH runs 33.",
                "Levels are never lost, only progress toward the next one.")
                .defineInRange("xpPenalty", 33, 0, 100);

        EXTRA_MODIFIERS = b.comment("Free upgrade slots granted to every tool on top of leveling.")
                .defineInRange("extraModifiers", 0, 0, 9);

        STARTING_UPGRADE_SLOTS = preset(b.comment(
                "Upgrade slots a freshly built tool carries before it levels at all.",
                "-1 keeps each tool type's own count, which is what Tinkers ships: 3 for a pickaxe,",
                "2 for a hammer, 5 for a sky staff. GTNH starts every tool at 0 and hands out all 26",
                "slots through leveling. Material traits like writable still add their slot either way.",
                "Changing this on a live world can leave already-modified tools with negative free slots.")
                .defineInRange("startingUpgradeSlots", -1, -1, 9), 0);

        MODIFIERS_AT_LEVELS = b.comment("Tool levels that grant an upgrade slot.")
                .<Number>defineList("modifiersAtLevels", DEFAULT_MODIFIER_LEVELS, Cfg::isLevel);

        RANDOM_BONUSES = preset(b.comment(
                "Grant a random bonus modifier on the levels listed below. GTNH turns this off entirely.")
                .define("randomBonuses", true), false);

        BONUSES_AT_LEVELS = b.comment("Tool levels that roll a random bonus, if randomBonuses is on.")
                .<Number>defineList("bonusesAtLevels", DEFAULT_BONUS_LEVELS, Cfg::isLevel);

        USEFUL_BONUSES = b.comment(
                "Only roll bonuses flagged useful for the tool category, e.g. no silk touch on a sword.")
                .define("usefulBonuses", true);

        COMPLETELY_RANDOM_BONUSES = b.comment(
                "Every allowed bonus is equally likely. Ignores weights, useful flags and usage bonuses.")
                .define("completelyRandomBonuses", false);

        USAGE_BONUS_WEIGHT = b.comment(
                "Weight added (on average) to a bonus if the tool did only that action for a whole level.")
                .defineInRange("usageBonusWeight", 70, 0, 999);

        b.pop();

        b.comment("Mining level boost: the second, separate level that unlocks a tool's true harvest tier.")
                .push("boost");

        PICKAXE_BOOST_REQUIRED = b.comment(
                "Every tool's mining level is reduced by 1 until it earns the boost.")
                .define("pickaxeBoostRequired", true);

        ALLOW_LEVELING_BOOST = b.comment("Tools earn the boost by mining.")
                .define("allowLevelingBoost", true);

        ADD_MOB_HEAD_BOOST = b.comment("The Reanimated (mob head) modifier can skip the boost.")
                .define("addMobHeadBoost", true);

        XP_PER_BOOST_LEVEL_MULTIPLIER =
                preset(b.comment("Exponential multiplier on required boost XP per tier. GTNH runs 1.15.")
                        .defineInRange("xpPerBoostLevelMultiplier", 1.12d, 1.0d, 9.99d), 1.15d);

        XP_REQUIRED_PICK_BOOST_PERCENTAGE =
                preset(b.comment("Boost XP required, as a percent. GTNH runs 125.")
                        .defineInRange("xpRequiredPickBoostPercentage", 100, 1, 999), 125);

        PICK_BOOST_XP_PENALTY = b.comment(
                "Percent of current boost XP removed when the head is replaced. GTNH runs 67.")
                .defineInRange("pickBoostXpPenalty", 67, 0, 100);

        BOOST_LOST_ON_HEAD_CHANGE = b.comment(
                "An earned boost is lost outright when the head material changes, not just its XP.")
                .define("boostLostOnHeadChange", true);

        REMOVE_MOB_HEAD = b.comment(
                "Replacing the head returns the Reanimated modifier so it must be applied again.")
                .define("removeMobHead", true);

        b.pop();

        b.comment("The paperbark tree, a paper source.",
                  "Bark peels off a log in strips with an axe (bark, 25%, 50%, 75%, bare)",
                  "Every stage has a chance to drop paper.")
                .push("paperbark");

        PAPER_PER_STRIP_CHANCE = b.comment("Percent chance one strip yields a sheet of paper.")
                .defineInRange("paperPerStripChance", 33, 0, 100);

        BARK_REGROW_CHANCE = b.comment(
                "Percent chance a part-peeled log grows its bark back one stage per random tick",
                "(a block gets one about every 68 seconds), so 25 is roughly 4-5 minutes a stage.",
                "0 turns regrowth off. A fully stripped log never regrows!")
                .defineInRange("barkRegrowChance", 25, 0, 100);

        b.pop();

        Bonuses.defineConfig(b);

        SPEC = b.build();
    }

    // attribute events fire before the config file is read, reading a value then throws
    public static boolean loaded() {
        return SPEC.isLoaded();
    }

    private static <T, C extends ForgeConfigSpec.ConfigValue<T>> C preset(C value, T horizons) {
        HORIZONS.put(value, horizons);
        return value;
    }

    // config values are read long before the file is, most of all by attribute events
    private static <T> T value(ForgeConfigSpec.ConfigValue<T> config) {
        return loaded() ? config.get() : config.getDefault();
    }

    public static boolean nerfRegularTools() {
        return value(NERF_REGULAR_TOOLS);
    }

    public static boolean nerfRegularHoes() {
        return value(NERF_REGULAR_HOES);
    }

    public static boolean nerfRegularSwords() {
        return value(NERF_REGULAR_SWORDS);
    }

    public static boolean nerfRegularBows() {
        return value(NERF_REGULAR_BOWS);
    }

    public static boolean disableStoneTools() {
        return value(DISABLE_STONE_TOOLS);
    }

    public static List<? extends String> excludedMods() {
        return value(EXCLUDED_MODS);
    }

    public static int durabilityPercentage() {
        return value(DURABILITY_PERCENTAGE);
    }

    public static int miningSpeedPercentage() {
        return value(MINING_SPEED_PERCENTAGE);
    }

    public static boolean repairModifierPenalty() {
        return value(REPAIR_MODIFIER_PENALTY);
    }

    public static float repairPenaltyPerModifier() {
        return value(REPAIR_PENALTY_PER_MODIFIER).floatValue();
    }

    public static float repairPenaltyFloor() {
        return value(REPAIR_PENALTY_FLOOR).floatValue();
    }

    public static int startingUpgradeSlots() {
        return value(STARTING_UPGRADE_SLOTS);
    }

    public static int paperPerStripChance() {
        return value(PAPER_PER_STRIP_CHANCE);
    }

    public static int barkRegrowChance() {
        return value(BARK_REGROW_CHANCE);
    }

    public static boolean strictTiers() {
        return value(STRICT_TIERS);
    }

    public static boolean gtnhStations() {
        return value(GTNH_STATIONS);
    }


    public static int xpRequiredToolsPercentage() {
        return value(XP_REQUIRED_TOOLS_PERCENTAGE);
    }

    public static int xpRequiredWeaponsPercentage() {
        return value(XP_REQUIRED_WEAPONS_PERCENTAGE);
    }

    public static int xpRequiredPickBoostPercentage() {
        return value(XP_REQUIRED_PICK_BOOST_PERCENTAGE);
    }

    public static boolean randomBonuses() {
        return value(RANDOM_BONUSES);
    }

    public static int maxToolLevel() {
        return loaded() ? MAX_TOOL_LEVEL.get() : 99;
    }

    public static float xpPerLevelMultiplier() {
        return loaded() ? XP_PER_LEVEL_MULTIPLIER.get().floatValue() : 1.20f;
    }

    public static float miningSpeedDivider() {
        return loaded() ? MINING_SPEED_DIVIDER.get().floatValue() : 2.4f;
    }

    public static float xpPerBoostLevelMultiplier() {
        return value(XP_PER_BOOST_LEVEL_MULTIPLIER).floatValue();
    }

    public static int usageBonusWeight() {
        return loaded() ? USAGE_BONUS_WEIGHT.get() : 70;
    }

    public static boolean isBonusLevel(int level) {
        return loaded() && randomBonuses() && bonusLevels().contains(level);
    }

    public static int slotsForLevel(int level) {
        return modifierLevels().headSet(level, true).size();
    }

    private static Set<Integer> bonusLevels;
    private static NavigableSet<Integer> modifierLevels;

    private static Set<Integer> bonusLevels() {
        Set<Integer> cached = bonusLevels;
        if (cached == null) {
            cached = new HashSet<>(levels(BONUSES_AT_LEVELS, DEFAULT_BONUS_LEVELS));
            bonusLevels = cached;
        }
        return cached;
    }

    private static NavigableSet<Integer> modifierLevels() {
        NavigableSet<Integer> cached = modifierLevels;
        if (cached == null) {
            cached = new TreeSet<>(levels(MODIFIERS_AT_LEVELS, DEFAULT_MODIFIER_LEVELS));
            modifierLevels = cached;
        }
        return cached;
    }

    // toml integers can come back as any Number subtype, so never cast straight to Integer
    private static List<Integer> levels(ForgeConfigSpec.ConfigValue<List<? extends Number>> value,
                                        List<Integer> fallback) {
        if (!loaded()) {
            return fallback;
        }
        return value.get().stream().map(Number::intValue).toList();
    }

    @SubscribeEvent
    public static void onLoad(ModConfigEvent.Loading event) {
        invalidate();
        applyHorizons();
    }

    @SubscribeEvent
    public static void onReload(ModConfigEvent.Reloading event) {
        invalidate();
        applyHorizons();
    }

    /**
     * Writes the GTNH values straight into the config file, so the settings the mode changes are the
     * ones you see and can edit. Held there while the mode is on; turn it off to tune one of them.
     */
    private static void applyHorizons() {
        if (!loaded() || !NEW_HORIZONS_MODE.get()) {
            return;
        }
        List<String> written = new ArrayList<>();
        HORIZONS.forEach((config, horizons) -> {
            Object current = config.get();
            Object wanted = merge(current, horizons);
            if (!current.equals(wanted)) {
                write(config, wanted);
                written.add(String.join(".", config.getPath()));
            }
        });
        if (written.isEmpty()) {
            return;
        }

        // one save covers every value set above
        NEW_HORIZONS_MODE.save();
        invalidate();

        Collections.sort(written);
        TiC3NH.LOGGER.info("New Horizons Mode wrote {} GTNH values into the config: {}",
                written.size(), written);
    }

    // a scalar preset is held at the GTNH value, a list preset is only a floor: its entries stay,
    // whatever the pack added on top survives, so excludedMods is still editable with the mode on
    private static Object merge(Object current, Object horizons) {
        if (!(horizons instanceof List<?> preset) || !(current instanceof List<?> existing)) {
            return horizons;
        }
        List<Object> merged = new ArrayList<>(preset);
        for (Object entry : existing) {
            if (!merged.contains(entry)) {
                merged.add(entry);
            }
        }
        return merged;
    }

    @SuppressWarnings("unchecked")
    private static <T> void write(ForgeConfigSpec.ConfigValue<T> config, Object value) {
        config.set((T) value);
    }

    private static void invalidate() {
        bonusLevels = null;
        modifierLevels = null;
    }

    private static boolean isResourceId(Object o) {
        return o instanceof String s && !s.isBlank();
    }

    private static boolean isLevel(Object o) {
        return o instanceof Number n && n.intValue() >= 1 && n.intValue() <= 99;
    }
}
