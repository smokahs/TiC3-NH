package com.tic3nh.restriction;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.DiggerItem;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.SwordItem;
import net.minecraft.world.item.TieredItem;
import net.minecraft.world.item.Tiers;

import net.minecraftforge.event.ItemAttributeModifierEvent;
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
        if (shouldNerf(event.getItemStack()) || shouldNerfSword(event.getItemStack())) {
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

    private static boolean shouldNerf(ItemStack stack) {
        if (!(stack.getItem() instanceof DiggerItem) || !nerfable(stack)) {
            return false;
        }
        if (stoneTier(stack) && Cfg.DISABLE_STONE_TOOLS.get()) {
            return true;
        }
        if (!Cfg.NERF_VANILLA_TOOLS.get()) {
            return false;
        }
        if (stack.getItem() instanceof HoeItem && !Cfg.NERF_VANILLA_HOES.get()) {
            return false;
        }
        return true;
    }

    private static boolean shouldNerfSword(ItemStack stack) {
        if (!(stack.getItem() instanceof SwordItem) || !nerfable(stack)) {
            return false;
        }
        if (stoneTier(stack) && Cfg.DISABLE_STONE_TOOLS.get()) {
            return true;
        }
        return Cfg.NERF_VANILLA_SWORDS.get();
    }

    private static boolean nerfable(ItemStack stack) {
        return Cfg.loaded() && !stack.isEmpty()
                && !stack.is(TinkerTags.Items.MODIFIABLE)
                && !isExcluded(stack);
    }

    private static boolean stoneTier(ItemStack stack) {
        return stack.getItem() instanceof TieredItem tiered && tiered.getTier() == Tiers.STONE;
    }

    private static boolean isExcluded(ItemStack stack) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(stack.getItem());
        if (id == null) {
            return false;
        }
        boolean listed = Cfg.EXCLUDED_TOOLS.get().contains(id.toString())
                || Cfg.EXCLUDED_MOD_TOOLS.get().contains(id.getNamespace());

        return Cfg.EXCLUDED_IS_WHITELIST.get() != listed;
    }
}
