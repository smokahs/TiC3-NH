package com.tic3nh;

import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import net.minecraft.resources.ResourceLocation;

import slimeknights.tconstruct.library.tools.definition.module.ToolModule;

import com.tic3nh.mininglevel.GtnhTiers;
import com.tic3nh.mininglevel.UnboostedTierPenaltyModule;
import com.tic3nh.setup.reg;

/**
 * TiC3-NH — a GregTech New Horizons-style Tinkers' Construct 3 addon for 1.20.1.
 *
 * <p>Recreates the GTNH tinker experience on modern TiC: numbered mining levels (GTNH tier scheme),
 * per-tool XP leveling, the GTNH modifier set, and a GTCEu Modern + Monifactory material bridge.
 * See {@code DESIGN.md} for the full architecture and the mapping onto TiC3's 1.20.1 API.
 *
 * <p>Phase 1 scaffold: this loads as a no-op and wires the registration entry points that later
 * phases (tiers, mining-boost, leveling, modifiers, materials) attach to.
 */
@Mod(TiC3NH.MOD_ID)
public final class TiC3NH {

    public static final String MOD_ID = "tic3nh";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TiC3NH() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        reg.init(modBus);
        modBus.addListener(this::commonSetup);

        LOGGER.info("TiC3-NH loading (Phase 3: mining boost)");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            // TierSortingRegistry is not thread-safe; must run on the sync work queue.
            GtnhTiers.register();
            // Make the tier-penalty module referenceable from tool-definition JSON.
            ToolModule.LOADER.register(
                    new ResourceLocation(MOD_ID, "unboosted_tier_penalty"),
                    UnboostedTierPenaltyModule.INSTANCE.getLoader());
        });
    }
}
