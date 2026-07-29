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

import com.tic3nh.config.Cfg;

public final class Penalty implements MiningTierToolHook, ToolModule {

    public static final Penalty INSTANCE =
            SingletonLoader.singleton(Penalty::new);

    private static final List<ModuleHook<?>> DEFAULT_HOOKS =
            HookProvider.<Penalty>defaultHooks(ToolHooks.MINING_TIER);

    private final RecordLoadable<Penalty> loader;

    private Penalty(RecordLoadable<Penalty> loader) {
        this.loader = loader;
    }

    @Override
    public RecordLoadable<Penalty> getLoader() {
        return loader;
    }

    @Override
    public List<ModuleHook<?>> getDefaultHooks() {
        return DEFAULT_HOOKS;
    }

    @Override
    public Tier modifyTier(IToolStackView tool, Tier tier) {
        if (Cfg.loaded() && !Cfg.PICKAXE_BOOST_REQUIRED.get()) {
            return tier;
        }
        if (tool.getPersistentData().getBoolean(MineKeys.BOOSTED)) {
            return tier;
        }

        if (!Cfg.loaded() || Cfg.ADD_MOB_HEAD_BOOST.get()) {
            int skullCap = tool.getModifierLevel(com.tic3nh.setup.reg.MINING_LEVEL_BOOST.getId());
            if (skullCap > 0 && skullCap >= GtTiers.TIERS.indexOf(GtTiers.displayFor(tier))) {
                return tier;
            }
        }
        List<Tier> sorted = TierSortingRegistry.getSortedTiers();
        int index = sorted.indexOf(tier);

        int floor = sorted.indexOf(GtTiers.STONE.tier());
        if (index <= floor || index < 0) {
            return tier;
        }
        return sorted.get(index - 1);
    }
}
