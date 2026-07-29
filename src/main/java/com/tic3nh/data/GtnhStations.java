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

// replaces the tinkers table recipes with the gtnh recipes.
@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class GtnhStations {

    private GtnhStations() {}

    private static final String NAME = "gtnh_stations";

    @SubscribeEvent
    public static void addPacks(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }
        Path source = ModList.get().getModFileById(TiC3NH.MOD_ID).getFile()
                .findResource("datapacks", NAME);

        boolean forced = Cfg.loaded() && Cfg.gtnhStations();

        event.addRepositorySource(consumer -> {
            Pack pack = Pack.readMetaAndCreate(
                    TiC3NH.MOD_ID + ":" + NAME,
                    Component.literal("TiC3-NH GTNH Stations"),
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
