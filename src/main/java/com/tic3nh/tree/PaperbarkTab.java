package com.tic3nh.tree;

import net.minecraft.world.item.CreativeModeTabs;

import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.tic3nh.TiC3NH;

/** Slots the paperbark set into the vanilla tabs its counterparts live in. */
@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class PaperbarkTab {

    private PaperbarkTab() {}

    @SubscribeEvent
    public static void addToTabs(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(Paperbark.LOG_ITEM);
            event.accept(Paperbark.WOOD_ITEM);
            event.accept(Paperbark.STRIPPED_LOG_ITEM);
            event.accept(Paperbark.STRIPPED_WOOD_ITEM);
            event.accept(Paperbark.PLANKS_ITEM);
            event.accept(Paperbark.STAIRS_ITEM);
            event.accept(Paperbark.SLAB_ITEM);
            event.accept(Paperbark.FENCE_ITEM);
            event.accept(Paperbark.FENCE_GATE_ITEM);
            event.accept(Paperbark.DOOR_ITEM);
            event.accept(Paperbark.TRAPDOOR_ITEM);
        } else if (event.getTabKey() == CreativeModeTabs.NATURAL_BLOCKS) {
            event.accept(Paperbark.LOG_ITEM);
            event.accept(Paperbark.LEAVES_ITEM);
            event.accept(Paperbark.SAPLING_ITEM);
        } else if (event.getTabKey() == CreativeModeTabs.FUNCTIONAL_BLOCKS) {
            event.accept(Paperbark.SIGN_ITEM);
            event.accept(Paperbark.HANGING_SIGN_ITEM);
        } else if (event.getTabKey() == CreativeModeTabs.REDSTONE_BLOCKS) {
            event.accept(Paperbark.BUTTON_ITEM);
            event.accept(Paperbark.PRESSURE_PLATE_ITEM);
        }
    }
}
