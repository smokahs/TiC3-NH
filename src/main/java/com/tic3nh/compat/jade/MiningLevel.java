package com.tic3nh.compat.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

import net.minecraftforge.common.TierSortingRegistry;

import com.tic3nh.TiC3NH;
import com.tic3nh.mininglevel.MineTips;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

public enum MiningLevel implements IBlockComponentProvider {
    INSTANCE;

    public static final ResourceLocation UID = new ResourceLocation(TiC3NH.MOD_ID, "mining_level");

    // Faithful to GTNH's WailaHarvestability: harvest info is gated behind sneak and each line is toggleable.
    public static final ResourceLocation SNEAK_ONLY =
            new ResourceLocation(TiC3NH.MOD_ID, "mining_level.sneak_only");
    public static final ResourceLocation SHOW_EFFECTIVE_TOOL =
            new ResourceLocation(TiC3NH.MOD_ID, "mining_level.effective_tool");
    public static final ResourceLocation SHOW_HARVESTABLE =
            new ResourceLocation(TiC3NH.MOD_ID, "mining_level.harvestable");
    public static final ResourceLocation SHOW_LEVEL =
            new ResourceLocation(TiC3NH.MOD_ID, "mining_level.level");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockState state = accessor.getBlockState();
        if (!state.requiresCorrectToolForDrops()) {
            return;
        }

        // GTNH's WailaHarvestability hid harvest info unless the player was sneaking.
        Player player = accessor.getPlayer();
        if (config.get(SNEAK_ONLY) && (player == null || !player.isShiftKeyDown())) {
            return;
        }

        Tier required = null;
        for (Tier tier : TierSortingRegistry.getSortedTiers()) {
            if (TierSortingRegistry.isCorrectTierForDrops(tier, state)) {
                required = tier;
                break;
            }
        }
        if (required == null) {
            return;
        }

        boolean meets = player != null && player.hasCorrectToolForDrops(state);

        if (config.get(SHOW_EFFECTIVE_TOOL)) {
            tooltip.add(MineTips.effectiveTool(state));
        }
        if (config.get(SHOW_HARVESTABLE)) {
            tooltip.add(MineTips.harvestable(meets));
        }
        if (config.get(SHOW_LEVEL)) {
            tooltip.add(MineTips.miningLevel(required, meets ? ChatFormatting.GREEN : ChatFormatting.RED));
        }
    }
}
