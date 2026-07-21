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

import net.minecraftforge.common.Tags;

import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import com.tic3nh.TiC3NH;

public final class Bonuses {

    private Bonuses() {}

    private static final String USAGE_PREFIX = "usage_";

    private enum Category { HARVEST, WEAPON, BOW }

    private enum Bonus {

        REDSTONE ("tconstruct:haste",         45,  0, 100,  true,  false, true),
        LAPIS    ("tconstruct:luck",          40, 75,  60,  true,  true,  true),
        AUTOSMELT("tconstruct:smelting",       3, 15,   1,  true,  false, false),

        DIAMOND  ("tconstruct:diamond",       30, 15,  20,  true,  false, false),
        EMERALD  ("tconstruct:emerald",       35, 30,  25,  true,  false, false),

        REINFORCED("tconstruct:reinforced",   77, 55,  65,  true,  true,  true),
        ATTACK   ("tconstruct:sharpness",      7, 110, 25,  false, true,  false),
        BLAZE    ("tconstruct:fiery",          3, 45,  15,  false, true,  false),
        SMITE    ("tconstruct:smite",          3, 40,  15,  false, true,  false),
        BANE     ("tconstruct:bane_of_sssss",  3, 40,  15,  false, true,  false),
        BEHEADING("tconstruct:severing",       3, 50,  10,  false, true,  false),
        LIFESTEAL("tconstruct:necrotic",       3, 30,  10,  false, true,  false),
        KNOCKBACK("tconstruct:knockback",     10, 50,  50,  false, true,  true),
        STONEBOUND("tconstruct:stonebound",    5,  1,   1,  true,  false, false),
        JAGGED   ("tconstruct:jagged",         1,  5,   1,  false, true,  false),
        CRITICAL ("tic3nh:critical",           1,  2,   0,  false, true,  false);

        final ModifierId id;
        final int toolWeight;
        final int weaponWeight;
        final int bowWeight;
        final boolean usefulTool;
        final boolean usefulWeapon;
        final boolean usefulBow;
        final ResourceLocation usageKey;

        Bonus(String id, int tool, int weapon, int bow,
              boolean usefulTool, boolean usefulWeapon, boolean usefulBow) {
            this.id = new ModifierId(id);
            this.toolWeight = tool;
            this.weaponWeight = weapon;
            this.bowWeight = bow;
            this.usefulTool = usefulTool;
            this.usefulWeapon = usefulWeapon;
            this.usefulBow = usefulBow;
            this.usageKey = new ResourceLocation(TiC3NH.MOD_ID, USAGE_PREFIX + name().toLowerCase(Locale.ROOT));
        }

        int weight(Category cat) {
            return switch (cat) {
                case HARVEST -> toolWeight;
                case WEAPON -> weaponWeight;
                case BOW -> bowWeight;
            };
        }

        boolean useful(Category cat) {
            return switch (cat) {
                case HARVEST -> usefulTool;
                case WEAPON -> usefulWeapon;
                case BOW -> usefulBow;
            };
        }
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

        Bonus[] all = Bonus.values();
        float[] weights = new float[all.length];
        float total = 0f;
        for (int i = 0; i < all.length; i++) {
            Bonus b = all[i];
            float w = b.useful(cat) ? b.weight(cat) : 0f;
            int usage = data.getInt(b.usageKey);
            if (usage > 0) {
                w += (float) usage / requiredXp * LvlKeys.USAGE_BONUS_WEIGHT;
            }
            if (!applicable(tool, b)) {
                w = 0f;
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
