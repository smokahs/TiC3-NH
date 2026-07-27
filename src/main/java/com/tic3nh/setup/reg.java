package com.tic3nh.setup;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.material.Fluids;

import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import com.tic3nh.TiC3NH;
import com.tic3nh.buckets.ClayBucket;
import com.tic3nh.buckets.MilkBucket;
import com.tic3nh.leveling.LvlMod;
import com.tic3nh.mininglevel.BoostMod;
import com.tic3nh.mininglevel.SkullMod;
import com.tic3nh.modifiers.CritMod;
import com.tic3nh.modifiers.MossMod;
import com.tic3nh.modifiers.RepairMod;

public final class reg {

    private reg() {}

    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TiC3NH.MOD_ID);

    public static final RegistryObject<Item> SILKY_JEWEL =
            ITEMS.register("silky_jewel", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> BALL_OF_MOSS =
            ITEMS.register("ball_of_moss", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> REINFORCEMENT =
            ITEMS.register("reinforcement", () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> UNFIRED_CLAY_BUCKET =
            ITEMS.register("unfired_clay_bucket", () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> CLAY_BUCKET = ITEMS
            .register("clay_bucket", () -> new ClayBucket(Fluids.EMPTY, false, new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> WATER_CLAY_BUCKET = ITEMS
            .register("water_clay_bucket", () -> new ClayBucket(Fluids.WATER, false, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> LAVA_CLAY_BUCKET = ITEMS
            .register("lava_clay_bucket", () -> new ClayBucket(Fluids.LAVA, true, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> MILK_CLAY_BUCKET =
            ITEMS.register("milk_clay_bucket", () -> new MilkBucket(new Item.Properties().stacksTo(1)));

    public static final ModifierDeferredRegister MODIFIERS =
            ModifierDeferredRegister.create(TiC3NH.MOD_ID);

    public static final StaticModifier<BoostMod> MINING_BOOST =
            MODIFIERS.register("mining_boost", BoostMod::new);

    public static final StaticModifier<LvlMod> LEVELING =
            MODIFIERS.register("leveling", LvlMod::new);

    public static final StaticModifier<SkullMod> MINING_LEVEL_BOOST =
            MODIFIERS.register("mining_level_boost", SkullMod::new);

    public static final StaticModifier<MossMod> MOSS =
            MODIFIERS.register("moss", MossMod::new);

    public static final StaticModifier<CritMod> CRITICAL =
            MODIFIERS.register("critical", CritMod::new);

    public static final StaticModifier<RepairMod> REPAIR_TWEAKS =
            MODIFIERS.register("repair_tweaks", RepairMod::new);

    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
        MODIFIERS.register(modBus);
    }
}
