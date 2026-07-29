package com.tic3nh.mininglevel;

import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.ModifierId;
import slimeknights.tconstruct.library.modifiers.hook.build.RawDataModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.mining.BlockHarvestModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.context.ToolHarvestContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModDataNBT;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;
import slimeknights.tconstruct.library.tools.stat.ToolStats;
import slimeknights.tconstruct.library.utils.RestrictedCompoundTag;

import com.tic3nh.config.Cfg;
import com.tic3nh.leveling.LvlLogic;
import com.tic3nh.setup.reg;

public class BoostMod extends NoLevelsModifier
        implements BlockHarvestModifierHook, RawDataModifierHook, InventoryTickModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hooks) {
        super.registerHooks(hooks);
        hooks.addHook(this, ModifierHooks.BLOCK_HARVEST, ModifierHooks.RAW_DATA, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void finishHarvest(IToolStackView tool, ModifierEntry modifier, ToolHarvestContext context,
                              int harvested) {
        if (harvested <= 0 || (Cfg.loaded() && !Cfg.ALLOW_LEVELING_BOOST.get())) {
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
            headReplaced(tool, data);
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

    private static void headReplaced(IToolStackView tool, ModDataNBT data) {
        int pct = Cfg.loaded() ? Cfg.PICK_BOOST_XP_PENALTY.get() : 100;
        int xp = data.getInt(MineKeys.BOOST_XP);
        if (pct > 0 && xp > 0) {
            data.putInt(MineKeys.BOOST_XP, Math.max(0, xp - (int) Math.ceil(xp * pct / 100f)));
        }

        // an earned boost belongs to the old head, so by default it has to be earned again
        if (data.getBoolean(MineKeys.BOOSTED) && (!Cfg.loaded() || Cfg.BOOST_LOST_ON_HEAD_CHANGE.get())) {
            data.putBoolean(MineKeys.BOOSTED, false);
            data.remove(MineKeys.BOOST_XP);
        }

        // modifiers cannot be touched mid-rebuild, so flag it and drop the head on the next tick
        if (Cfg.loaded() && Cfg.REMOVE_MOB_HEAD.get()
                && tool.getModifierLevel(reg.MINING_LEVEL_BOOST.getId()) > 0) {
            data.putBoolean(MineKeys.DROP_MOB_HEAD, true);
        }
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity holder,
                                int itemSlot, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (world.isClientSide() || !(tool instanceof ToolStack toolStack)) {
            return;
        }
        ModDataNBT data = tool.getPersistentData();
        if (!data.getBoolean(MineKeys.DROP_MOB_HEAD)) {
            return;
        }

        // clear first: removeModifier rebuilds the tool, which runs addRawData again
        data.remove(MineKeys.DROP_MOB_HEAD);
        ModifierId id = reg.MINING_LEVEL_BOOST.getId();
        int level = toolStack.getUpgrades().getLevel(id);
        if (level > 0) {
            toolStack.removeModifier(id, level);
            if (holder instanceof ServerPlayer player) {
                player.displayClientMessage(Component.translatable("tic3nh.message.mob_head_returned"), true);
            }
        }
    }

    @Override
    public void removeRawData(IToolStackView tool, Modifier modifier, RestrictedCompoundTag tag) {

    }
}
