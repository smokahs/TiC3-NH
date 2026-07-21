package com.tic3nh;

import net.minecraft.resources.ResourceLocation;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import slimeknights.tconstruct.library.tools.definition.module.ToolModule;

import com.tic3nh.config.Cfg;
import com.tic3nh.mininglevel.GtTiers;
import com.tic3nh.mininglevel.Penalty;
import com.tic3nh.setup.reg;

import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

@Mod(TiC3NH.MOD_ID)
public final class TiC3NH {

    public static final String MOD_ID = "tic3nh";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TiC3NH() {
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();

        reg.init(modBus);
        modBus.addListener(this::commonSetup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, Cfg.SPEC);

        LOGGER.info("TiC3-NH loading (Phase 3: mining boost)");
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {

            GtTiers.register();

            ToolModule.LOADER.register(
                    new ResourceLocation(MOD_ID, "unboosted_tier_penalty"),
                    Penalty.INSTANCE.getLoader());
        });
    }
}
