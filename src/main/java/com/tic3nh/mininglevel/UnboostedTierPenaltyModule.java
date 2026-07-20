package com.tic3nh.mininglevel;

import java.util.List;

import net.minecraft.world.item.Tier;
import net.minecraftforge.common.TierSortingRegistry;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.mantle.data.loadable.record.SingletonLoader;
import slimeknights.tconstruct.library.module.HookProvider;
import slimeknights.tconstruct.library.module.ModuleHook;
import slimeknights.tconstruct.library.tools.definition.module.ToolHooks;
import slimeknights.tconstruct.library.tools.definition.module.ToolModule;
import slimeknights.tconstruct.library.tools.definition.module.mining.MiningTierToolHook;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

/**
 * Tool-definition module (DESIGN.md §3.2) that drops a tool's effective harvest tier by one — GTNH's
 * "start one below your material" — until the one-time mining boost fires ({@link MiningKeys#BOOSTED}).
 *
 * <p>Lowering tier CANNOT be a modifier: {@code ToolStats.HARVEST_TIER} merges upward (max) and there
 * is no {@code MINING_TIER} modifier hook. It must be a {@link MiningTierToolHook} on the tool
 * definition, which runs live on every tier query — so the boost takes effect instantly, no rebuild.
 *
 * <p>Added only to mining tool definitions (pickaxe/mattock/hammer), so "is this a mining tool" is
 * implied by presence; flooring at the lowest sorted tier handles a stone-tier tool (no under-flow).
 * Singleton (no config) — mirrors {@link ToolModule#EMPTY}'s loader pattern.
 */
public final class UnboostedTierPenaltyModule implements MiningTierToolHook, ToolModule {

    /** Singleton instance; the captured loader handles (de)serialization from tool-definition JSON. */
    public static final UnboostedTierPenaltyModule INSTANCE =
            SingletonLoader.singleton(UnboostedTierPenaltyModule::new);

    private static final List<ModuleHook<?>> DEFAULT_HOOKS =
            HookProvider.<UnboostedTierPenaltyModule>defaultHooks(ToolHooks.MINING_TIER);

    private final RecordLoadable<UnboostedTierPenaltyModule> loader;

    private UnboostedTierPenaltyModule(RecordLoadable<UnboostedTierPenaltyModule> loader) {
        this.loader = loader;
    }

    @Override
    public RecordLoadable<UnboostedTierPenaltyModule> getLoader() {
        return loader;
    }

    @Override
    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }

    @Override
    public Tier modifyTier(IToolStackView tool, Tier tier) {
        if (tool.getPersistentData().getBoolean(MiningKeys.BOOSTED)) {
            return tier; // boost fired → run at full material tier
        }
        List<Tier> sorted = TierSortingRegistry.getSortedTiers();
        int index = sorted.indexOf(tier);
        if (index <= 0) {
            return tier; // lowest sorted tier (or unsorted) → nothing to drop
        }
        return sorted.get(index - 1);
    }
}
