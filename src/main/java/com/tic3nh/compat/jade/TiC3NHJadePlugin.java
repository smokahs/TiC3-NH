package com.tic3nh.compat.jade;

import net.minecraft.world.level.block.Block;

import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

import com.tic3nh.TiC3NH;

/**
 * Jade plugin entry (DESIGN.md §3.1 "WAILA-style tooltip"). Discovered by Jade via {@link WailaPlugin};
 * only loaded when Jade is installed, so the mod runs fine without it (Jade is a compile-only dep).
 */
@WailaPlugin(TiC3NH.MOD_ID)
public class TiC3NHJadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        // registerBlockComponent already registers the config key for the provider's UID; calling
        // addConfig again throws "Duplicate config key" and aborts the whole plugin (Jade 11.13.2).
        registration.registerBlockComponent(MiningLevelProvider.INSTANCE, Block.class);
    }
}
