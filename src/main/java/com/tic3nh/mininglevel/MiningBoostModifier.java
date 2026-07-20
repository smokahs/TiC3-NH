package com.tic3nh.mininglevel;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.TooltipFlag;
import net.minecraftforge.common.TierSortingRegistry;
import slimeknights.mantle.client.TooltipKey;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.display.TooltipModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockHarvestModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

/**
 * Innate trait (DESIGN.md §3.2, §3.3): accumulates mining-boost XP as the tool harvests and, once, flips
 * {@link MiningKeys#BOOSTED} so {@link UnboostedTierPenaltyModule} stops docking the tier. One-time —
 * GTNH's "mine enough and your tool reaches its material's true level."
 *
 * <p>Attached via each mining tool definition's {@code tconstruct:traits} list, so it is innate and
 * costs no modifier slot. Because {@code modifyTier} reads persistent data live, the boost applies the
 * instant it fires, no rebuild needed.
 */
public class MiningBoostModifier extends NoLevelsModifier
        implements BlockHarvestModifierHook, TooltipModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hooks) {
        super.registerHooks(hooks);
        hooks.addHook(this, ModifierHooks.BLOCK_HARVEST, ModifierHooks.TOOLTIP);
    }

    @Override
    public void finishHarvest(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context,
                              int harvested) {
        if (harvested <= 0) {
            return;
        }
        ModDataNBT data = tool.getPersistentData();
        if (data.getBoolean(MiningKeys.BOOSTED)) {
            return; // already boosted, one-time only
        }
        int required = requiredBoostXp(tool);
        int xp = data.getInt(MiningKeys.BOOST_XP) + harvested;
        if (xp >= required) {
            data.putBoolean(MiningKeys.BOOSTED, true);
            data.remove(MiningKeys.BOOST_XP);
            ServerPlayer player = context.getPlayer();
            if (player != null) {
                player.displayClientMessage(Component.translatable("tic3nh.message.boosted"), true);
            }
        } else {
            data.putInt(MiningKeys.BOOST_XP, xp);
        }
    }

    @Override
    public void addTooltip(IToolStackView tool, ModifierEntry modifier, @Nullable Player player,
                           List<Component> tooltip, TooltipKey tooltipKey, TooltipFlag tooltipFlag) {
        // SHIFT view: TiC already renders the mining level (HARVEST_TIER stat); add the XP line.
        tooltip.add(MiningTooltips.miningXp(tool));
    }

    /** Blocks to mine before the boost fires, scaled by the tool's material harvest tier. */
    public static int requiredBoostXp(IToolStackView tool) {
        Tier tier = tool.getStats().get(ToolStats.HARVEST_TIER);
        int index = Math.max(0, TierSortingRegistry.getSortedTiers().indexOf(tier));
        return MiningKeys.BASE_BOOST_XP * (index + 1);
    }
}
