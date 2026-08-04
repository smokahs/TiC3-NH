package com.tic3nh.setup;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.library.tools.item.IModifiableDisplay;

import com.tic3nh.TiC3NH;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ToolTab {

    private static final ResourceLocation TCONSTRUCT_TOOLS = new ResourceLocation("tconstruct", "tools");

    private ToolTab() {}

    @SubscribeEvent
    public static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey().location().equals(TCONSTRUCT_TOOLS)) {
            event.accept(((IModifiableDisplay) reg.SHOVEL.get()).getRenderTool());
            event.accept(((IModifiableDisplay) reg.HATCHET.get()).getRenderTool());
        }
    }
}
