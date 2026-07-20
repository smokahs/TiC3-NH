package com.tic3nh.setup;

import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import com.tic3nh.TiC3NH;
import com.tic3nh.mininglevel.MiningBoostModifier;

/**
 * Central registry wiring.
 * <ul>
 *   <li>Phase 2 — GTNH harvest tiers (in common setup via TierSortingRegistry, not a register here).</li>
 *   <li>Phase 3 — mining-boost modifier (below). Tool-definition modules register in common setup.</li>
 *   <li>Phase 3-5 — boost items (skulls) into {@link #ITEMS}.</li>
 * </ul>
 */
public final class reg {

    private reg() {}

    /** Placeholder item register for future GTNH boost items (mob skulls, etc.). */
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, TiC3NH.MOD_ID);

    /** TiC modifier register. */
    public static final ModifierDeferredRegister MODIFIERS =
            ModifierDeferredRegister.create(TiC3NH.MOD_ID);

    /** Innate mining-boost trait: mine enough to fire the one-time tier boost (DESIGN §3.2). */
    public static final StaticModifier<MiningBoostModifier> MINING_BOOST =
            MODIFIERS.register("mining_boost", MiningBoostModifier::new);

    public static void init(IEventBus modBus) {
        ITEMS.register(modBus);
        MODIFIERS.register(modBus);
    }
}
