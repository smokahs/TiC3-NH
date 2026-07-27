package com.tic3nh.buckets;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.wrappers.FluidBucketWrapper;

import com.tic3nh.setup.reg;

public class BucketCap extends FluidBucketWrapper {

    public BucketCap(ItemStack container) {
        super(container);
    }

    @Override
    public boolean canFillFluidType(FluidStack fluid) {
        return ClayBucket.filledFor(fluid.getFluid()) != null;
    }

    @Override
    protected void setFluid(FluidStack fluidStack) {
        if (fluidStack.isEmpty()) {

            container = container.getItem() instanceof ClayBucket bucket ? bucket.emptied()
                    : new ItemStack(reg.CLAY_BUCKET.get());
            return;
        }
        Item filled = ClayBucket.filledFor(fluidStack.getFluid());
        container = filled == null ? ItemStack.EMPTY : new ItemStack(filled);
    }
}
