package com.tic3nh.data;

import java.nio.file.Path;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;

import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.resource.PathPackResources;

import com.tic3nh.TiC3NH;
import com.tic3nh.config.Cfg;

// strips vanilla ores out of the vanilla harvest tags so the gtnh tiers gates them.
@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class StrictTiers {

    private StrictTiers() {}

    private static final String NAME = "strict_tiers";

    @SubscribeEvent
    public static void addPacks(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }
        Path source = ModList.get().getModFileById(TiC3NH.MOD_ID).getFile()
                .findResource("datapacks", NAME);

        boolean forced = Cfg.loaded() && Cfg.STRICT_TIERS.get();

        event.addRepositorySource(consumer -> {
            Pack pack = Pack.readMetaAndCreate(
                    TiC3NH.MOD_ID + ":" + NAME,
                    Component.literal("TiC3-NH Strict Tiers"),
                    forced,
                    id -> new PathPackResources(id, true, source),
                    PackType.SERVER_DATA,
                    Pack.Position.TOP,
                    PackSource.BUILT_IN);
            if (pack != null) {
                consumer.accept(pack);
            }
        });
    }
}
