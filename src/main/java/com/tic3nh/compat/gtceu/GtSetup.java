package com.tic3nh.compat.gtceu;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;

import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.tic3nh.TiC3NH;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GtSetup {

    private static final String PACK_ID = "tic3nh_gtceu_materials";

    private GtSetup() {}

    @SubscribeEvent
    public static void addPackFinders(AddPackFindersEvent event) {
        if (!GtAccess.isLoaded()) {
            return;
        }
        PackType type = event.getPackType();
        Pack pack = Pack.readMetaAndCreate(
                PACK_ID,
                Component.literal("A Tinkers' New Horizon — GTCEu materials"),
                true,
                GtPack::new,
                type,
                Pack.Position.TOP,
                PackSource.BUILT_IN);
        if (pack != null) {
            event.addRepositorySource(consumer -> consumer.accept(pack));
        }
    }
}
