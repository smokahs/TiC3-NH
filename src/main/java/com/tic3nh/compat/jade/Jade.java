package com.tic3nh.compat.jade;

import net.minecraft.world.level.block.Block;

import com.tic3nh.TiC3NH;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin(TiC3NH.MOD_ID)
public class Jade implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {

        registration.registerBlockComponent(MiningLevel.INSTANCE, Block.class);

        // GTNH-faithful defaults: sneak to reveal, all lines on. Users can flip these in Jade's config.
        registration.addConfig(MiningLevel.SNEAK_ONLY, true);
        registration.addConfig(MiningLevel.SHOW_EFFECTIVE_TOOL, true);
        registration.addConfig(MiningLevel.SHOW_HARVESTABLE, true);
        registration.addConfig(MiningLevel.SHOW_LEVEL, true);
    }
}
