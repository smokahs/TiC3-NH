package com.tic3nh.buckets;

import javax.annotation.Nullable;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.MilkBucketItem;
import net.minecraft.world.level.Level;

import net.minecraftforge.common.capabilities.ICapabilityProvider;

import com.tic3nh.setup.reg;

public class MilkBucket extends MilkBucketItem {

    public MilkBucket(Properties props) {
        super(props);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);
        return result.is(Items.BUCKET) ? new ItemStack(reg.CLAY_BUCKET.get()) : result;
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return new ItemStack(reg.CLAY_BUCKET.get());
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BucketCap(stack);
    }
}
