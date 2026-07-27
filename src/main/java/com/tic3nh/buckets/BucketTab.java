package com.tic3nh.buckets;

import net.minecraft.world.item.CreativeModeTabs;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.tic3nh.TiC3NH;
import com.tic3nh.setup.reg;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class BucketTab {

    private BucketTab() {}

    @SubscribeEvent
    public static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.INGREDIENTS) {
            event.accept(reg.UNFIRED_CLAY_BUCKET);
        } else if (event.getTabKey() == CreativeModeTabs.TOOLS_AND_UTILITIES) {
            event.accept(reg.CLAY_BUCKET);
            event.accept(reg.WATER_CLAY_BUCKET);
            event.accept(reg.LAVA_CLAY_BUCKET);
            event.accept(reg.MILK_CLAY_BUCKET);
        }
    }
}
