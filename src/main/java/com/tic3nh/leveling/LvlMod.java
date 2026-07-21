package com.tic3nh.leveling;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.RawDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockHarvestModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.SlotType;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolDataNBT;
import slimeknights.tconstruct.library.utils.RestrictedCompoundTag;

public class LvlMod extends NoLevelsModifier
        implements BlockHarvestModifierHook, MeleeHitModifierHook, VolatileDataModifierHook,
                   RawDataModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hooks) {
        super.registerHooks(hooks);
        hooks.addHook(this, ModifierHooks.BLOCK_HARVEST, ModifierHooks.MELEE_HIT,
                ModifierHooks.VOLATILE_DATA, ModifierHooks.RAW_DATA);
    }

    @Override
    public void finishHarvest(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context,
                              int harvested) {
        if (harvested > 0) {
            LvlLogic.addXp(tool, context.getPlayer(), harvested);
            Bonuses.noteMiningUsage(tool, context);
        }
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context,
                              float damageDealt) {
        int hearts = (int) Math.floor(damageDealt / 2f);
        if (hearts > 0) {
            LvlLogic.addXp(tool, context.getPlayerAttacker(), hearts);
        }
        Bonuses.noteCombatUsage(tool, context.getLivingTarget());
    }

    @Override
    public void addVolatileData(IToolContext context, ModifierEntry modifier, ToolDataNBT volatileData) {
        int slots = LvlKeys.slotsForLevel(context.getPersistentData().getInt(LvlKeys.TOOL_LEVEL));
        if (slots > 0) {
            volatileData.addSlots(SlotType.UPGRADE, slots);
        }
    }

    @Override
    public void addRawData(IToolStackView tool, ModifierEntry modifier, RestrictedCompoundTag tag) {
        ModDataNBT data = tool.getPersistentData();
        int cur = LvlLogic.baseRequiredXp(tool);
        int xp = data.getInt(LvlKeys.TOOL_XP);
        int old = data.getInt(LvlKeys.XP_REQ_REF);
        if (xp > 0 && old > 0 && old != cur) {
            data.putInt(LvlKeys.TOOL_XP, Math.max(0, Math.round(xp * (float) cur / old)));
        }
        data.putInt(LvlKeys.XP_REQ_REF, cur);
    }

    @Override
    public void removeRawData(IToolStackView tool, Modifier modifier, RestrictedCompoundTag tag) {

    }
}
