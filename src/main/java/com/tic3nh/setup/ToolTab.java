package com.tic3nh.setup;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTabs;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.library.tools.item.IModifiableDisplay;

import com.tic3nh.TiC3NH;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ToolTab {

    private static final ResourceLocation TCONSTRUCT_TOOLS = new ResourceLocation("tconstruct", "tools");
    private static final ResourceLocation TCONSTRUCT_TOOL_PARTS = new ResourceLocation("tconstruct", "tool_parts");
    private static final ResourceLocation TCONSTRUCT_SMELTERY = new ResourceLocation("tconstruct", "smeltery");

    private ToolTab() {}

    @SubscribeEvent
    public static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        // loose crafting items, none of which tconstruct puts in a tab for us
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(reg.SILKY_JEWEL);
            event.accept(reg.BALL_OF_MOSS);
            event.accept(reg.REINFORCEMENT);
            event.accept(reg.TWILIGHT_CRYSTAL);
            return;
        }
        ResourceLocation tab = event.getTabKey().location();
        if (tab.equals(TCONSTRUCT_TOOLS)) {
            event.accept(((IModifiableDisplay) reg.SHOVEL.get()).getRenderTool());
        } else if (tab.equals(TCONSTRUCT_TOOL_PARTS)) {
            reg.SHOVEL_HEAD.get().addVariants(event::accept, "");
        } else if (tab.equals(TCONSTRUCT_SMELTERY)) {
            event.accept(reg.SHOVEL_HEAD_CAST);
            event.accept(reg.SHOVEL_HEAD_SAND_CAST);
            event.accept(reg.SHOVEL_HEAD_RED_SAND_CAST);
        }
    }
}
