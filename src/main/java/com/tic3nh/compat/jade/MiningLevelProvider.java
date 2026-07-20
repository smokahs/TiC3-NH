package com.tic3nh.compat.jade;

import net.minecraft.ChatFormatting;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.TierSortingRegistry;

import snownee.jade.api.BlockAccessor;
import snownee.jade.api.IBlockComponentProvider;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

import com.tic3nh.TiC3NH;
import com.tic3nh.mininglevel.MiningTooltips;

/**
 * Jade block component reproducing GTNH's WAILA line: for any block that needs the correct tool, the
 * required mining level as {@code Mining Level: NN-Name}, GREEN when the held tool meets it and RED
 * when it does not (wiki §"Mining Level"). The required tier is resolved from Forge's
 * {@link TierSortingRegistry} (so it works on all blocks, vanilla + modded, mapped to a GTNH name),
 * not just our tagged ores. Toggle it in Jade's config under this UID.
 */
public enum MiningLevelProvider implements IBlockComponentProvider {
    INSTANCE;

    public static final ResourceLocation UID = new ResourceLocation(TiC3NH.MOD_ID, "mining_level");

    @Override
    public ResourceLocation getUid() {
        return UID;
    }

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockState state = accessor.getBlockState();
        if (!state.requiresCorrectToolForDrops()) {
            return; // only tool-gated blocks (stone/ores/obsidian/…), as GTNH did
        }

        // Lowest sorted tier that can harvest this block = its required mining level.
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

        Player player = accessor.getPlayer();
        boolean meets = player != null && player.hasCorrectToolForDrops(state);
        tooltip.add(MiningTooltips.miningLevel(required, meets ? ChatFormatting.GREEN : ChatFormatting.RED));
    }
}
