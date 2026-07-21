package com.tic3nh.modifiers;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import slimeknights.tconstruct.library.modifiers.Modifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.interaction.InventoryTickModifierHook;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.helper.ToolDamageUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class MossMod extends Modifier implements InventoryTickModifierHook {

    @Override
    protected void registerHooks(ModuleHookMap.Builder hooks) {
        super.registerHooks(hooks);
        hooks.addHook(this, ModifierHooks.INVENTORY_TICK);
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world,
                                LivingEntity holder, int itemSlot, boolean isSelected,
                                boolean isCorrectSlot, ItemStack stack) {
        if (world.isClientSide() || tool.isBroken() || tool.getDamage() <= 0) {
            return;
        }
        boolean inSun = world.isDay() && world.canSeeSky(holder.blockPosition());
        long interval = inSun ? 10L : 20L;
        if (world.getGameTime() % interval == 0L) {
            ToolDamageUtil.repair(tool, modifier.getLevel());
        }
    }
}
