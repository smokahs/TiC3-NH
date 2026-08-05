package com.tic3nh.client;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.contents.TranslatableContents;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.materials.definition.IMaterial;
import slimeknights.tconstruct.library.tools.definition.module.mining.MiningTierToolHook;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.part.IToolPart;

import com.tic3nh.TiC3NH;
import com.tic3nh.leveling.LvlTip;
import com.tic3nh.mininglevel.MineTips;
import com.tic3nh.setup.reg;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class HudTip {

    private static final String HARVEST_TIER_STAT_KEY = "tool_stat.tconstruct.harvest_tier";

    private HudTip() {}

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (!stack.is(TinkerTags.Items.MODIFIABLE)) {
            return;
        }
        ToolStack tool = ToolStack.from(stack);
        boolean leveling = tool.getModifierLevel(reg.LEVELING.getId()) > 0;
        boolean mining = tool.getModifierLevel(reg.MINING_BOOST.getId()) > 0;
        if (!leveling && !mining) {
            return;
        }

        boolean shift = Screen.hasShiftDown();
        List<Component> lines = new ArrayList<>();
        if (leveling) {
            lines.add(LvlTip.level(tool));
            if (shift) {
                lines.add(LvlTip.xp(tool));
            }
        }
        if (mining) {
            Tier tier = MiningTierToolHook.getTier(tool);
            lines.add(MineTips.miningLevel(tier, null));
            if (shift) {
                lines.add(MineTips.miningXp(tool));
            }
        }
        if (!shift) {
            lines.add(Component.translatable("tic3nh.tooltip.hold_shift_xp")
                    .withStyle(ChatFormatting.DARK_GRAY));
        }
        lines.add(Component.empty());

        List<Component> tooltip = event.getToolTip();

        if (mining) {
            tooltip.removeIf(HudTip::isHarvestTierLine);
        }

        int index = 1;
        for (Component line : lines) {
            tooltip.add(index++, line);
        }
    }

    // iguana tweaks put a gold reminder on every tool part that it can be swapped into a built tool
    @SubscribeEvent
    public static void onPartTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (stack.getItem() instanceof IToolPart part
                && !part.getMaterial(stack).equals(IMaterial.UNKNOWN_ID)) {
            List<Component> tooltip = event.getToolTip();
            tooltip.add(Component.empty());
            tooltip.add(Component.translatable("tic3nh.tooltip.part.replaceable")
                    .withStyle(ChatFormatting.GOLD));
        }
    }

    private static boolean isHarvestTierLine(Component line) {
        if (line.getContents() instanceof TranslatableContents tc && HARVEST_TIER_STAT_KEY.equals(tc.getKey())) {
            return true;
        }
        for (Component sibling : line.getSiblings()) {
            if (sibling.getContents() instanceof TranslatableContents tc
                    && HARVEST_TIER_STAT_KEY.equals(tc.getKey())) {
                return true;
            }
        }
        return false;
    }
}
