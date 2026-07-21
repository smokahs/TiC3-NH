package com.tic3nh.mininglevel;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.Tier;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.RawDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockHarvestModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.RestrictedCompoundTag;

import com.tic3nh.leveling.LvlLogic;

public class BoostMod extends NoLevelsModifier
        implements BlockHarvestModifierHook, RawDataModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hooks) {
        super.registerHooks(hooks);
        hooks.addHook(this, ModifierHooks.BLOCK_HARVEST, ModifierHooks.RAW_DATA);
    }

    @Override
    public void finishHarvest(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context,
                              int harvested) {
        if (harvested <= 0) {
            return;
        }
        ModDataNBT data = tool.getPersistentData();
        if (data.getBoolean(MineKeys.BOOSTED)) {
            return;
        }

        Tier tier = tool.getStats().get(ToolStats.HARVEST_TIER);
        if (GtTiers.TIERS.indexOf(GtTiers.displayFor(tier)) <= 0) {
            return;
        }
        int required = requiredBoostXp(tool);
        int xp = data.getInt(MineKeys.BOOST_XP) + harvested;
        if (xp >= required) {
            data.putBoolean(MineKeys.BOOSTED, true);
            data.remove(MineKeys.BOOST_XP);
            ServerPlayer player = context.getPlayer();
            if (player != null) {
                player.displayClientMessage(Component.translatable("tic3nh.message.boosted"), true);
            }
        } else {
            data.putInt(MineKeys.BOOST_XP, xp);
        }
    }

    public static int requiredBoostXp(IToolStackView tool) {
        return LvlLogic.requiredBoostXp(tool);
    }

    @Override
    public void addRawData(IToolStackView tool, ModifierEntry modifier, RestrictedCompoundTag tag) {
        if (tool.getMaterials().size() == 0) {
            return;
        }
        ModDataNBT data = tool.getPersistentData();

        String head = tool.getMaterial(0).getVariant().toString();
        String stored = data.getString(MineKeys.BOOST_HEAD_MATERIAL);
        if (!stored.isEmpty() && !stored.equals(head)) {
            data.putBoolean(MineKeys.BOOSTED, false);
            data.remove(MineKeys.BOOST_XP);
        }
        data.putString(MineKeys.BOOST_HEAD_MATERIAL, head);

        int cur = requiredBoostXp(tool);
        if (!data.getBoolean(MineKeys.BOOSTED)) {
            int xp = data.getInt(MineKeys.BOOST_XP);
            int old = data.getInt(MineKeys.BOOST_REQ_REF);
            if (xp > 0 && old > 0 && old != cur) {
                data.putInt(MineKeys.BOOST_XP, Math.max(0, Math.round(xp * (float) cur / old)));
            }
        }
        data.putInt(MineKeys.BOOST_REQ_REF, cur);
    }

    @Override
    public void removeRawData(IToolStackView tool, Modifier modifier, RestrictedCompoundTag tag) {

    }
}
