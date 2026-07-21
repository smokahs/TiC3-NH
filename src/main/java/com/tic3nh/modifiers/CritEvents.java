package com.tic3nh.modifiers;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.event.entity.player.CriticalHitEvent;
import net.minecraftforge.eventbus.api.Event.Result;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import slimeknights.tconstruct.common.TinkerTags;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import com.tic3nh.TiC3NH;
import com.tic3nh.setup.reg;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID)
public final class CritEvents {

    private CritEvents() {}

    @SubscribeEvent
    public static void onCritical(CriticalHitEvent event) {
        if (event.getResult() == Result.DENY || event.isVanillaCritical()) {
            return;
        }
        Player player = event.getEntity();
        ItemStack stack = player.getMainHandItem();
        if (!stack.is(TinkerTags.Items.MODIFIABLE)) {
            return;
        }
        ToolStack tool = ToolStack.from(stack);
        if (tool.getModifierLevel(reg.CRITICAL.getId()) > 0 && player.getRandom().nextInt(10) == 0) {
            event.setResult(Result.ALLOW);
        }
    }
}
