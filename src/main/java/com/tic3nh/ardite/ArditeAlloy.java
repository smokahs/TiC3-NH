package com.tic3nh.ardite;

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

// puts manyullyn back on ardite + cobalt. tinkers 3 alloys it from ancient debris because it has no
// ardite; we do, so the debris recipe goes.
@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class ArditeAlloy {

    private ArditeAlloy() {}

    private static final String NAME = "ardite_alloy";

    @SubscribeEvent
    public static void addPacks(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) {
            return;
        }
        Path source = ModList.get().getModFileById(TiC3NH.MOD_ID).getFile()
                .findResource("datapacks", NAME);

        event.addRepositorySource(consumer -> {
            Pack pack = Pack.readMetaAndCreate(
                    TiC3NH.MOD_ID + ":" + NAME,
                    Component.literal("TiC3-NH Ardite Alloys"),
                    true,
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
