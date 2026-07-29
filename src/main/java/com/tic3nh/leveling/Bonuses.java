package com.tic3nh.leveling;

import java.util.Locale;

import javax.annotation.Nullable;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.monster.Blaze;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.monster.WitherSkeleton;
import net.minecraft.world.entity.monster.ZombifiedPiglin;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.Tags;

import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import com.tic3nh.TiC3NH;
import com.tic3nh.config.Cfg;

public final class Bonuses {

    private Bonuses() {}

    private static final String USAGE_PREFIX = "usage_";

    private enum Category { HARVEST, WEAPON, BOW }

    private enum Bonus {

        REDSTONE ("tconstruct:haste",         "redstone",          45,  0, 100,  true,  false, true),
        LAPIS    ("tconstruct:luck",          "luckLooting",       40, 75,  60,  true,  true,  true),
        AUTOSMELT("tconstruct:smelting",      "autosmelt",          3, 15,   1,  true,  false, false),

        DIAMOND  ("tconstruct:diamond",       "diamond",           30, 15,  20,  true,  false, false),
        EMERALD  ("tconstruct:emerald",       "emerald",           35, 30,  25,  true,  false, false),

        REINFORCED("tconstruct:reinforced",   "reinforced",        77, 55,  65,  true,  true,  true),
        ATTACK   ("tconstruct:sharpness",     "attack",             7, 110, 25,  false, true,  false),
        BLAZE    ("tconstruct:fiery",         "fiery",              3, 45,  15,  false, true,  false),
        SMITE    ("tconstruct:smite",         "smite",              3, 40,  15,  false, true,  false),
        BANE     ("tconstruct:bane_of_sssss", "baneOfArthropods",   3, 40,  15,  false, true,  false),
        BEHEADING("tconstruct:severing",      "beheading",          3, 50,  10,  false, true,  false),
        LIFESTEAL("tconstruct:necrotic",      "lifeSteal",          3, 30,  10,  false, true,  false),
        KNOCKBACK("tconstruct:knockback",     "knockback",         10, 50,  50,  false, true,  true),
        STONEBOUND("tconstruct:stonebound",   "stonebound",         5,  1,   1,  true,  false, false),
        JAGGED   ("tconstruct:jagged",        "jagged",             1,  5,   1,  false, true,  false),
        CRITICAL ("tic3nh:critical",          "critical",           1,  2,   0,  false, true,  false);

        final ModifierId id;
        final String cfgName;
        final int toolWeight;
        final int weaponWeight;
        final int bowWeight;
        final boolean usefulTool;
        final boolean usefulWeapon;
        final boolean usefulBow;
        final ResourceLocation usageKey;

        // filled in by defineConfig, null until the spec is built
        ForgeConfigSpec.BooleanValue allowCfg;
        ForgeConfigSpec.IntValue toolWeightCfg;
        ForgeConfigSpec.IntValue weaponWeightCfg;
        ForgeConfigSpec.IntValue bowWeightCfg;
        ForgeConfigSpec.BooleanValue usefulToolCfg;
        ForgeConfigSpec.BooleanValue usefulWeaponCfg;
        ForgeConfigSpec.BooleanValue usefulBowCfg;

        Bonus(String id, String cfgName, int tool, int weapon, int bow,
              boolean usefulTool, boolean usefulWeapon, boolean usefulBow) {
            this.id = new ModifierId(id);
            this.cfgName = cfgName;
            this.toolWeight = tool;
            this.weaponWeight = weapon;
            this.bowWeight = bow;
            this.usefulTool = usefulTool;
            this.usefulWeapon = usefulWeapon;
            this.usefulBow = usefulBow;
            this.usageKey = new ResourceLocation(TiC3NH.MOD_ID, USAGE_PREFIX + name().toLowerCase(Locale.ROOT));
        }

        boolean allowed() {
            return allowCfg == null || !Cfg.loaded() || allowCfg.get();
        }

        int weight(Category cat) {
            ForgeConfigSpec.IntValue cfg = switch (cat) {
                case HARVEST -> toolWeightCfg;
                case WEAPON -> weaponWeightCfg;
                case BOW -> bowWeightCfg;
            };
            if (cfg != null && Cfg.loaded()) {
                return cfg.get();
            }
            return switch (cat) {
                case HARVEST -> toolWeight;
                case WEAPON -> weaponWeight;
                case BOW -> bowWeight;
            };
        }

        boolean useful(Category cat) {
            ForgeConfigSpec.BooleanValue cfg = switch (cat) {
                case HARVEST -> usefulToolCfg;
                case WEAPON -> usefulWeaponCfg;
                case BOW -> usefulBowCfg;
            };
            if (cfg != null && Cfg.loaded()) {
                return cfg.get();
            }
            return switch (cat) {
                case HARVEST -> usefulTool;
                case WEAPON -> usefulWeapon;
                case BOW -> usefulBow;
            };
        }
    }

    // mirrors IguanaTweaks' BonusModifierDefaults.cfg: one weight table and one useful table per category
    public static void defineConfig(ForgeConfigSpec.Builder b) {
        b.comment("Random bonus tables. Weights are summed, then one is picked at random inside that sum,",
                  "so a weight of 0 or a useful flag of false means the bonus never rolls for that category.")
                .push("bonuses");

        b.comment("Disable a bonus everywhere, whatever the weights say.").push("allow");
        for (Bonus x : Bonus.values()) {
            x.allowCfg = b.define(x.cfgName, true);
        }
        b.pop();

        b.comment("Roll weights for harvest tools.").push("toolWeights");
        for (Bonus x : Bonus.values()) {
            x.toolWeightCfg = b.defineInRange(x.cfgName, x.toolWeight, 0, 9999);
        }
        b.pop();

        b.comment("Roll weights for weapons.").push("weaponWeights");
        for (Bonus x : Bonus.values()) {
            x.weaponWeightCfg = b.defineInRange(x.cfgName, x.weaponWeight, 0, 9999);
        }
        b.pop();

        b.comment("Roll weights for bows.").push("bowWeights");
        for (Bonus x : Bonus.values()) {
            x.bowWeightCfg = b.defineInRange(x.cfgName, x.bowWeight, 0, 9999);
        }
        b.pop();

        b.comment("Bonuses worth rolling on a harvest tool, used when leveling.usefulBonuses is on.")
                .push("usefulToolBonuses");
        for (Bonus x : Bonus.values()) {
            x.usefulToolCfg = b.define(x.cfgName, x.usefulTool);
        }
        b.pop();

        b.comment("Bonuses worth rolling on a weapon.").push("usefulWeaponBonuses");
        for (Bonus x : Bonus.values()) {
            x.usefulWeaponCfg = b.define(x.cfgName, x.usefulWeapon);
        }
        b.pop();

        b.comment("Bonuses worth rolling on a bow.").push("usefulBowBonuses");
        for (Bonus x : Bonus.values()) {
            x.usefulBowCfg = b.define(x.cfgName, x.usefulBow);
        }
        b.pop();

        b.pop();
    }

    static void roll(IToolStackView tool, @Nullable Player player, int level) {
        if (!(tool instanceof ToolStack stack)) {
            return;
        }
        if (player != null && player.level().isClientSide()) {
            return;
        }
        Category cat = category(tool);
        ModDataNBT data = tool.getPersistentData();
        int requiredXp = Math.max(1, LvlLogic.requiredXp(tool, level));

        boolean equalOdds = Cfg.loaded() && Cfg.COMPLETELY_RANDOM_BONUSES.get();
        boolean usefulOnly = !Cfg.loaded() || Cfg.USEFUL_BONUSES.get();
        int usageWeight = Cfg.usageBonusWeight();

        Bonus[] all = Bonus.values();
        float[] weights = new float[all.length];
        float total = 0f;
        for (int i = 0; i < all.length; i++) {
            Bonus b = all[i];
            float w;
            if (!b.allowed() || !applicable(tool, b)) {
                w = 0f;
            } else if (equalOdds) {
                w = 1f;
            } else if (usefulOnly && !b.useful(cat)) {

                w = 0f;
            } else {
                w = b.weight(cat);

                int usage = data.getInt(b.usageKey);
                if (usage > 0) {
                    w += (float) usage / requiredXp * usageWeight;
                }
            }
            weights[i] = w;
            total += w;
        }
        if (total <= 0f) {
            return;
        }

        float r = (player != null ? player.getRandom().nextFloat() : (float) Math.random()) * total;
        Bonus choice = null;
        float cumulative = 0f;
        for (int i = 0; i < all.length; i++) {
            cumulative += weights[i];
            if (r < cumulative) {
                choice = all[i];
                break;
            }
        }
        if (choice == null) {
            return;
        }

        stack.addModifier(choice.id, 1);
        data.remove(choice.usageKey);
        if (player != null) {
            player.displayClientMessage(Component.translatable("tic3nh.message.random_bonus",
                    Component.translatable(Util.makeDescriptionId("modifier", choice.id))), false);
        }
    }

    private static boolean applicable(IToolStackView tool, Bonus b) {
        if (b == Bonus.JAGGED) {
            return tool.getModifierLevel(Bonus.STONEBOUND.id) == 0;
        }
        if (b == Bonus.STONEBOUND) {
            return tool.getModifierLevel(Bonus.JAGGED.id) == 0;
        }
        return true;
    }

    private static Category category(IToolStackView tool) {
        String p = tool.getDefinition().getId().getPath();
        if (p.contains("bow")) {
            return Category.BOW;
        }
        if (p.contains("sword") || p.contains("cleaver") || p.contains("dagger")
                || p.contains("rapier") || p.contains("battlesign")) {
            return Category.WEAPON;
        }
        return Category.HARVEST;
    }

    public static void noteMiningUsage(IToolStackView tool, ToolHarvestContext context) {
        ModDataNBT data = tool.getPersistentData();
        BlockState state = context.getState();
        if (state.is(Tags.Blocks.ORES)) {
            addUsage(data, Bonus.LAPIS, 1);
        } else {
            addUsage(data, Bonus.REDSTONE, 1);
        }
        Level world = context.getWorld();
        BlockPos pos = context.getPos();
        for (Direction dir : Direction.values()) {
            if (world.getBlockState(pos.relative(dir)).is(Blocks.LAVA)) {
                addUsage(data, Bonus.AUTOSMELT, 10);
                break;
            }
        }
    }

    public static void noteCombatUsage(IToolStackView tool, @Nullable LivingEntity target) {
        if (target == null) {
            return;
        }
        ModDataNBT data = tool.getPersistentData();
        MobType type = target.getMobType();
        if (type == MobType.UNDEAD) {
            addUsage(data, Bonus.SMITE, 1);
        } else if (type == MobType.ARTHROPOD) {
            addUsage(data, Bonus.BANE, 1);
        }
        if (target instanceof Blaze) {
            addUsage(data, Bonus.BLAZE, 1);
        } else if (target instanceof EnderMan) {
            addUsage(data, Bonus.BEHEADING, 1);
        } else if (target instanceof WitherSkeleton || target instanceof ZombifiedPiglin) {
            addUsage(data, Bonus.LIFESTEAL, 1);
        }
    }

    private static void addUsage(ModDataNBT data, Bonus b, int amount) {
        data.putInt(b.usageKey, data.getInt(b.usageKey) + amount);
    }
}
