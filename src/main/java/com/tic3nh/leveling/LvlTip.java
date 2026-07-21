package com.tic3nh.leveling;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public final class LvlTip {

    private LvlTip() {}

    private static final ChatFormatting[] LEVEL_COLORS = {
            ChatFormatting.GRAY, ChatFormatting.DARK_RED, ChatFormatting.GOLD, ChatFormatting.YELLOW,
            ChatFormatting.DARK_GREEN, ChatFormatting.DARK_AQUA, ChatFormatting.LIGHT_PURPLE,
            ChatFormatting.WHITE, ChatFormatting.RED, ChatFormatting.DARK_PURPLE, ChatFormatting.AQUA,
            ChatFormatting.GREEN };

    public static Component level(IToolStackView tool) {
        int level = LvlLogic.level(tool);
        Component rank = Component.translatable("tic3nh.tooltip.skill." + Math.min(level, LvlKeys.MAX_LEVEL))
                .withStyle(LEVEL_COLORS[level % LEVEL_COLORS.length]);
        return Component.translatable("tic3nh.tooltip.tool_level", rank);
    }

    public static Component xp(IToolStackView tool) {
        int level = LvlLogic.level(tool);
        if (level >= LvlKeys.MAX_LEVEL) {
            return Component.translatable("tic3nh.tooltip.tool_xp",
                    Component.translatable("tic3nh.tooltip.max_level").withStyle(ChatFormatting.GOLD));
        }
        int xp = tool.getPersistentData().getInt(LvlKeys.TOOL_XP);
        int required = LvlLogic.requiredXp(tool, level);
        float percent = required > 0 ? xp * 100f / required : 0f;
        return Component.translatable("tic3nh.tooltip.tool_xp",
                Component.literal(String.format("%d / %d (%.1f%%)", xp, required, percent)));
    }
}
