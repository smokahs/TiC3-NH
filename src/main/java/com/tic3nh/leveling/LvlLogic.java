package com.tic3nh.leveling;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import com.tic3nh.mininglevel.GtTiers;
import com.tic3nh.mininglevel.MineKeys;

public final class LvlLogic {

    private LvlLogic() {}

    private static final AtomicBoolean MIXIN_LOGGED = new AtomicBoolean(false);

    public static void noteMixinActive() {
        if (MIXIN_LOGGED.compareAndSet(false, true)) {
            com.tic3nh.TiC3NH.LOGGER.info(
                    "[tic3nh] leveling mixin ACTIVE — leveling trait injected on tool build");
        }
    }

    public static int requiredXp(IToolStackView tool, int level) {
        ResourceLocation id = tool.getDefinition().getId();
        float base;
        if (isWeapon(id)) {
            float attack = tool.getStats().get(ToolStats.ATTACK_DAMAGE);
            base = 140f * Math.max(1f, attack) * 1.2f;
        } else {
            base = 100f;

            base += tool.getStats().get(ToolStats.MINING_SPEED) / LvlKeys.MINING_SPEED_DIVIDER;

            int hl = gtnhOrdinal(tool.getStats().get(ToolStats.HARVEST_TIER));
            if (hl < 1) {
                base -= 20f;
            }
            if (hl < 2) {
                base -= 15f;
            }
        }
        base *= toolTypeMultiplier(id);
        if (level > 1) {
            base *= (float) Math.pow(LvlKeys.XP_PER_LEVEL_MULTIPLIER, level - 1);
        }
        return Math.max(1, Math.round(base));
    }

    public static int baseRequiredXp(IToolStackView tool) {
        return requiredXp(tool, 1);
    }

    public static int requiredBoostXp(IToolStackView tool) {

        int reduced = Math.max(0, gtnhOrdinal(tool.getStats().get(ToolStats.HARVEST_TIER)) - 1);
        float base = 100f;
        if (reduced < 1) {
            base -= 20f;
        }
        if (reduced < 2) {
            base -= 15f;
        }
        base += tool.getStats().get(ToolStats.MINING_SPEED) / LvlKeys.MINING_SPEED_DIVIDER;
        base *= toolTypeMultiplier(tool.getDefinition().getId());
        float mult = MineKeys.XP_PER_BOOST_LEVEL_MULTIPLIER;
        if (reduced >= 1) {
            base *= (float) Math.pow(mult, reduced - 1);
        } else {
            base /= mult * mult;
        }
        return Math.max(1, Math.round(base));
    }

    public static void addXp(IToolStackView tool, @Nullable Player player, int amount) {
        if (amount <= 0) {
            return;
        }
        ModDataNBT data = tool.getPersistentData();
        int level = data.getInt(LvlKeys.TOOL_LEVEL);
        if (level >= LvlKeys.MAX_LEVEL) {
            return;
        }
        int xp = data.getInt(LvlKeys.TOOL_XP) + amount;
        boolean leveled = false;
        while (level < LvlKeys.MAX_LEVEL) {
            int required = requiredXp(tool, level);
            if (xp < required) {
                break;
            }
            xp -= required;
            level++;
            leveled = true;
            maybeRandomBonus(tool, player, level);
        }
        if (level >= LvlKeys.MAX_LEVEL) {
            xp = 0;
        }
        data.putInt(LvlKeys.TOOL_LEVEL, level);
        data.putInt(LvlKeys.TOOL_XP, xp);
        if (leveled) {
            notifyLevel(player, level);
            rebuild(tool);
        }
    }

    public static void addLevels(IToolStackView tool, @Nullable Player player, int levels) {
        if (levels == 0) {
            return;
        }
        ModDataNBT data = tool.getPersistentData();
        int oldLevel = data.getInt(LvlKeys.TOOL_LEVEL);
        int level = Math.max(0, Math.min(LvlKeys.MAX_LEVEL, oldLevel + levels));

        for (int l = oldLevel + 1; l <= level; l++) {
            maybeRandomBonus(tool, player, l);
        }
        data.putInt(LvlKeys.TOOL_LEVEL, level);
        data.putInt(LvlKeys.TOOL_XP, 0);
        notifyLevel(player, level);
        rebuild(tool);
    }

    private static void maybeRandomBonus(IToolStackView tool, @Nullable Player player, int level) {
        if (LvlKeys.RANDOM_BONUS_LEVELS.contains(level)) {
            Bonuses.roll(tool, player, level);
        }
    }

    public static int level(IToolStackView tool) {
        return tool.getPersistentData().getInt(LvlKeys.TOOL_LEVEL);
    }

    private static void rebuild(IToolStackView tool) {
        if (tool instanceof ToolStack ts) {
            ts.rebuildStats();
        }
    }

    private static void notifyLevel(@Nullable Player player, int level) {
        if (player != null) {
            player.displayClientMessage(Component.translatable("tic3nh.message.level_up", level), true);
        }
    }

    static int gtnhOrdinal(Tier tier) {
        return GtTiers.TIERS.indexOf(GtTiers.displayFor(tier));
    }

    private static boolean isWeapon(ResourceLocation id) {
        String p = id.getPath();
        return p.contains("sword") || p.contains("cleaver") || p.contains("dagger")
                || p.contains("rapier") || p.contains("battlesign");
    }

    private static float toolTypeMultiplier(ResourceLocation id) {
        String p = id.getPath();
        if (p.contains("hammer")) {
            return 5.1f;
        }
        if (p.contains("excavator")) {
            return 6.2f;
        }
        if (p.contains("broad_axe")) {
            return 1.38f;
        }
        if (p.contains("mattock") || p.contains("shovel") || p.contains("pickadze")) {
            return 1.2f;
        }
        if (p.contains("hand_axe") || p.contains("kama")) {
            return 0.66f;
        }
        return 1.0f;
    }
}
