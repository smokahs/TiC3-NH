package com.tic3nh.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;

import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.tic3nh.TiC3NH;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public final class gen {

    private gen() {}

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existing = event.getExistingFileHelper();

        TierTags blockTags = new TierTags(output, event.getLookupProvider(), existing);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), new PaperbarkItemTags(output, event.getLookupProvider(),
                blockTags.contentsGetter(), existing));
        generator.addProvider(event.includeServer(), new PaperbarkLoot(output));
        generator.addProvider(event.includeServer(), new PaperbarkRecipes(output));

        // block models first: the item models parent onto them
        generator.addProvider(event.includeClient(), new PaperbarkStates(output, existing));
        generator.addProvider(event.includeClient(), new PaperbarkItems(output, existing));
    }
}
