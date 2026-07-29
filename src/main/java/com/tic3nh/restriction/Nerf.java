package com.tic3nh.restriction;

import java.util.List;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BowItem;
import net.minecraft.world.item.CrossbowItem;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;

import net.minecraftforge.event.ItemAttributeModifierEvent;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

import slimeknights.tconstruct.common.TinkerTags;

import com.tic3nh.TiC3NH;
import com.tic3nh.config.Cfg;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID)
public final class Nerf {

    private Nerf() {}

    @SubscribeEvent
    public static void onHarvestCheck(PlayerEvent.HarvestCheck event) {
        if (shouldNerf(event.getEntity().getMainHandItem())) {

            event.setCanHarvest(!event.getTargetBlock().requiresCorrectToolForDrops());
        }
    }

    @SubscribeEvent
    public static void onTooltip(ItemTooltipEvent event) {
        ItemStack stack = event.getItemStack();
        if (shouldNerf(stack) || shouldNerfSword(stack) || shouldNerfBow(stack)) {
            event.getToolTip().add(Component.translatable("tic3nh.tooltip.tool_nerfed")
                    .withStyle(ChatFormatting.RED));
        }
    }

    @SubscribeEvent
    public static void onBreakSpeed(PlayerEvent.BreakSpeed event) {
        ItemStack tool = event.getEntity().getMainHandItem();
        if (shouldNerf(tool)) {
            float toolMultiplier = tool.getDestroySpeed(event.getState());
            if (toolMultiplier > 1.0F) {
                event.setNewSpeed(event.getNewSpeed() / toolMultiplier);
            }
        }
    }

    // stripping attack damage leaves the player's base 1.0, matching a bare hand
    @SubscribeEvent
    public static void onAttributes(ItemAttributeModifierEvent event) {
        if (event.getSlotType() == EquipmentSlot.MAINHAND && shouldNerfSword(event.getItemStack())) {
            event.removeAttribute(Attributes.ATTACK_DAMAGE);
        }
    }

    // cancelling the draw covers bows and crossbows in one place
    @SubscribeEvent
    public static void onUseStart(LivingEntityUseItemEvent.Start event) {
        if (shouldNerfBow(event.getItem())) {
            event.setCanceled(true);
        }
    }

    private static boolean shouldNerf(ItemStack stack) {
        if (!(stack.getItem() instanceof DiggerItem) || !nerfable(stack)) {
            return false;
        }
        if (stack.getItem() instanceof HoeItem) {
            return !isExcluded(stack, Cfg.EXCLUDED_HOES.get())
                    && (Cfg.nerfRegularHoes() || stoneNerf(stack));
        }
        if (isExcluded(stack, Cfg.EXCLUDED_TOOLS.get())) {
            return false;
        }
        return Cfg.nerfRegularTools() || stoneNerf(stack);
    }

    private static boolean shouldNerfSword(ItemStack stack) {
        if (!(stack.getItem() instanceof SwordItem) || !nerfable(stack)
                || isExcluded(stack, Cfg.EXCLUDED_SWORDS.get())) {
            return false;
        }
        return Cfg.nerfRegularSwords() || stoneNerf(stack);
    }

    private static boolean shouldNerfBow(ItemStack stack) {
        if (!(stack.getItem() instanceof BowItem || stack.getItem() instanceof CrossbowItem)
                || !nerfable(stack) || isExcluded(stack, Cfg.EXCLUDED_BOWS.get())) {
            return false;
        }
        return Cfg.nerfRegularBows();
    }

    private static boolean nerfable(ItemStack stack) {
        return Cfg.loaded() && !stack.isEmpty()
                && !stack.is(TinkerTags.Items.MODIFIABLE);
    }

    private static boolean stoneNerf(ItemStack stack) {
        return Cfg.disableStoneTools()
                && stack.getItem() instanceof TieredItem tiered && tiered.getTier() == Tiers.STONE;
    }

    private static boolean isExcluded(ItemStack stack, List<? extends String> items) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        boolean listed = items.contains(id.toString())
                || Cfg.excludedMods().contains(id.getNamespace());

        return Cfg.EXCLUSION_IS_WHITELIST.get() != listed;
    }
}
