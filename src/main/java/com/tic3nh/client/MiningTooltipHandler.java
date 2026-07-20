package com.tic3nh.client;

import java.util.List;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.definition.module.mining.MiningTierToolHook;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import com.tic3nh.TiC3NH;
import com.tic3nh.mininglevel.MiningTooltips;
import com.tic3nh.setup.reg;

/**
 * Shows the mining level + boost XP on a tool's tooltip in the DEFAULT (no-key) view, so it is always
 * visible — as GTNH did. TiC only runs {@code ModifierHooks.TOOLTIP} (the modifier's {@code addTooltip})
 * and the {@code HARVEST_TIER} stat line under SHIFT, so this fills the non-shift view to avoid dupes.
 */
@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.FORGE)
public final class MiningTooltipHandler {

    private MiningTooltipHandler() {}

    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        // SHIFT view already shows the mining level (HARVEST_TIER stat) + XP (modifier tooltip).
        if (Screen.hasShiftDown()) {
            return;
        }
        ItemStack stack = event.getItemStack();
        if (!stack.is(TinkerTags.Items.MODIFIABLE)) {
            return;
        }
        ToolStack tool = ToolStack.from(stack);
        if (tool.getModifierLevel(reg.MINING_BOOST.getId()) <= 0) {
            return; // not one of our mining-boost tools
        }

        List<Component> tooltip = event.getToolTip();
        Tier tier = MiningTierToolHook.getTier(tool);
        // Insert directly under the item name (index 0), GTNH-style, rather than at the very bottom.
        tooltip.add(1, MiningTooltips.miningLevel(tier, null)); // effective level, in its own tier color
        tooltip.add(2, MiningTooltips.miningXp(tool));
        tooltip.add(3, Component.empty()); // spacer before durability/modifiers
    }
}
