package com.tic3nh.mininglevel;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.block.state.BlockState;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.utils.HarvestTiers;

public final class MineTips {

    // Built from code points so the source stays pure ASCII; raw glyphs get mangled by javac's charset.
    private static final String CHECK = new String(Character.toChars(0x2714)); // heavy check mark
    private static final String CROSS = new String(Character.toChars(0x2718)); // heavy ballot X

    private MineTips() {}

    public static Component miningLevel(Tier tier, ChatFormatting override) {
        Component name = HarvestTiers.getName(GtTiers.displayFor(tier).tier());
        if (override != null) {
            name = name.copy().withStyle(override);
        }
        return Component.translatable("tic3nh.tooltip.mining_level", name);
    }

    /** WailaHarvestability-style "Effective Tool" line: the tool class the block wants. */
    public static Component effectiveTool(BlockState state) {
        String tool;
        if (state.is(BlockTags.MINEABLE_WITH_PICKAXE)) {
            tool = "pickaxe";
        } else if (state.is(BlockTags.MINEABLE_WITH_AXE)) {
            tool = "axe";
        } else if (state.is(BlockTags.MINEABLE_WITH_SHOVEL)) {
            tool = "shovel";
        } else if (state.is(BlockTags.MINEABLE_WITH_HOE)) {
            tool = "hoe";
        } else {
            tool = "unknown";
        }
        Component name = Component.translatable("tic3nh.tooltip.tool." + tool).withStyle(ChatFormatting.AQUA);
        return Component.translatable("tic3nh.tooltip.effective_tool", name);
    }

    /** WailaHarvestability-style "Harvestable" line: whether the held tool can harvest the block. */
    public static Component harvestable(boolean meets) {
        Component mark = Component.literal(meets ? CHECK : CROSS)
                .withStyle(meets ? ChatFormatting.GREEN : ChatFormatting.RED);
        return Component.translatable("tic3nh.tooltip.harvestable", mark);
    }

    public static Component miningXp(IToolStackView tool) {
        if (tool.getPersistentData().getBoolean(MineKeys.BOOSTED)) {
            return Component.translatable("tic3nh.tooltip.mining_xp",
                    Component.translatable("tic3nh.tooltip.boosted").withStyle(ChatFormatting.GOLD));
        }
        int xp = tool.getPersistentData().getInt(MineKeys.BOOST_XP);
        int required = BoostMod.requiredBoostXp(tool);
        float percent = required > 0 ? xp * 100f / required : 0f;
        return Component.translatable("tic3nh.tooltip.mining_xp",
                Component.literal(String.format("%d / %d (%.2f%%)", xp, required, percent)));
    }
}
