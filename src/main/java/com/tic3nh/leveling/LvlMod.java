package com.tic3nh.leveling;

import javax.annotation.Nullable;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.RawDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.build.VolatileDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockHarvestModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.ranged.ProjectileLaunchModifierHook;
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

import com.tic3nh.config.Cfg;

public class LvlMod extends NoLevelsModifier
        implements BlockHarvestModifierHook, MeleeHitModifierHook, ProjectileLaunchModifierHook,
                   VolatileDataModifierHook, RawDataModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hooks) {
        super.registerHooks(hooks);
        hooks.addHook(this, ModifierHooks.BLOCK_HARVEST, ModifierHooks.MELEE_HIT,
                ModifierHooks.PROJECTILE_LAUNCH, ModifierHooks.VOLATILE_DATA, ModifierHooks.RAW_DATA);
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

    // bows level per shot like iguana tweaks, secondary multishot arrows do not count
    @Override
    public void onProjectileLaunch(IToolStackView tool, ModifierEntry modifier, LivingEntity shooter,
                                   Projectile projectile, @Nullable AbstractArrow arrow,
                                   ModDataNBT persistentData, boolean primary) {
        if (primary && shooter instanceof Player player && !player.level().isClientSide()) {
            LvlLogic.addXp(tool, player, 1);
        }
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

        // a part swap changes the requirement, rescale so the fraction toward the next level survives it
        if (xp > 0 && old > 0 && old != cur) {
            xp = Math.max(0, Math.round(xp * (float) cur / old));
            data.putInt(LvlKeys.TOOL_XP, xp);
        }
        data.putInt(LvlKeys.XP_REQ_REF, cur);

        // then charge for the swap itself, on top of the rescale
        String parts = materialFingerprint(tool);
        String stored = data.getString(LvlKeys.MATERIAL_REF);
        if (!stored.isEmpty() && !stored.equals(parts)) {
            int pct = Cfg.loaded() ? Cfg.XP_PENALTY.get() : 0;
            if (pct > 0 && xp > 0) {
                data.putInt(LvlKeys.TOOL_XP, Math.max(0, xp - (int) Math.ceil(xp * pct / 100f)));
            }
        }
        if (!parts.equals(stored)) {
            data.putString(LvlKeys.MATERIAL_REF, parts);
        }
    }

    // tic3 only counts a part swap when the material variant changes, so the variants are the whole identity
    private static String materialFingerprint(IToolStackView tool) {
        int size = tool.getMaterials().size();
        if (size == 0) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (i > 0) {
                sb.append(',');
            }
            sb.append(tool.getMaterial(i).getVariant());
        }
        return sb.toString();
    }

    @Override
    public void removeRawData(IToolStackView tool, Modifier modifier, RestrictedCompoundTag tag) {

    }
}
