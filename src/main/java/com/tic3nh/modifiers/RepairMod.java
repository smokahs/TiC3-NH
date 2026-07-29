package com.tic3nh.modifiers;

import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.behavior.RepairFactorModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
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
        int pct = Cfg.durabilityPercentage();
        if (pct != 100) {
            builder.multiplier(ToolStats.DURABILITY, pct / 100.0);
        }
        int speed = Cfg.miningSpeedPercentage();
        if (speed != 100) {
            builder.multiplier(ToolStats.MINING_SPEED, speed / 100.0);
        }
    }

    // counts what the player spent, not what is left: leveling keeps handing out fresh slots
    @Override
    public float getRepairFactor(IToolStackView tool, ModifierEntry entry, float factor) {
        if (!Cfg.repairModifierPenalty()) {
            return factor;
        }
        int used = 0;
        for (ModifierEntry upgrade : tool.getUpgrades().getModifiers()) {
            used += upgrade.getLevel();
        }
        if (used <= 0) {
            return factor;
        }
        float mult = Math.max(Cfg.repairPenaltyFloor(), 1f - used * Cfg.repairPenaltyPerModifier());
        return factor * mult;
    }
}
