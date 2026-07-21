package com.tic3nh.modifiers;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.RepairFactorModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import com.tic3nh.config.Cfg;

public class RepairMod extends NoLevelsModifier
        implements ToolStatsModifierHook, RepairFactorModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hooks) {
        super.registerHooks(hooks);
        hooks.addHook(this, ModifierHooks.TOOL_STATS, ModifierHooks.REPAIR_FACTOR);
    }

    @Override
    public boolean shouldDisplay(boolean advanced) {
        return false;
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        int pct = Cfg.DURABILITY_PERCENTAGE.get();
        if (pct != 100) {
            builder.multiplier(ToolStats.DURABILITY, pct / 100.0);
        }
    }

    @Override
    public float getRepairFactor(IToolStackView tool, ModifierEntry entry, float factor) {
        if (!Cfg.REPAIR_MODIFIER_PENALTY.get()) {
            return factor;
        }
        int freeSlots = tool.getFreeSlots(SlotType.UPGRADE);
        float mult = switch (Math.min(freeSlots, 3)) {
            case 0 -> 0.7f;
            case 1 -> 0.8f;
            case 2 -> 0.9f;
            default -> 1.0f;
        };
        return factor * mult;
    }
}
