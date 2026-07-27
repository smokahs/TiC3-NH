package com.tic3nh.buckets;

import javax.annotation.Nullable;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.ForgeEventFactory;

import com.tic3nh.setup.reg;

public class ClayBucket extends BucketItem {

    private final boolean hot;

    @SuppressWarnings("deprecation")
    public ClayBucket(Fluid content, boolean hot, Properties props) {
        super(content, props);
        this.hot = hot;
    }

    public boolean isHot() {
        return hot;
    }

    public ItemStack emptied() {
        return hot ? ItemStack.EMPTY : new ItemStack(reg.CLAY_BUCKET.get());
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return !hot && getFluid() != Fluids.EMPTY;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        return emptied();
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack held = player.getItemInHand(hand);
        boolean empty = getFluid() == Fluids.EMPTY;
        BlockHitResult hit = getPlayerPOVHitResult(
                level,
                player,
                empty ? ClipContext.Fluid.SOURCE_ONLY : ClipContext.Fluid.NONE);

        InteractionResultHolder<ItemStack> handled = ForgeEventFactory.onBucketUse(player, level, held, hit);
        if (handled != null) {
            return handled;
        }
        if (hit.getType() != HitResult.Type.BLOCK) {
            return InteractionResultHolder.pass(held);
        }

        BlockPos pos = hit.getBlockPos();
        Direction face = hit.getDirection();
        BlockPos beside = pos.relative(face);
        if (!level.mayInteract(player, pos) || !player.mayUseItemAt(beside, face, held)) {
            return InteractionResultHolder.fail(held);
        }
        return empty ? scoop(level, player, held, pos) : pour(level, player, held, hit, pos, beside);
    }

    private InteractionResultHolder<ItemStack> scoop(Level level, Player player, ItemStack held, BlockPos pos) {
        BlockState state = level.getBlockState(pos);
        Item filled = filledFor(state.getFluidState().getType());
        if (filled == null || !(state.getBlock() instanceof BucketPickup pickup)) {
            return InteractionResultHolder.fail(held);
        }

        ItemStack drained = pickup.pickupBlock(level, pos, state);
        if (drained.isEmpty()) {
            return InteractionResultHolder.fail(held);
        }

        player.awardStat(Stats.ITEM_USED.get(this));
        pickup.getPickupSound(state).ifPresent(sound -> player.playSound(sound, 1.0F, 1.0F));
        level.gameEvent(player, GameEvent.FLUID_PICKUP, pos);

        ItemStack result = ItemUtils.createFilledResult(held, player, new ItemStack(filled));
        if (player instanceof ServerPlayer server) {
            CriteriaTriggers.FILLED_BUCKET.trigger(server, drained);
        }
        return InteractionResultHolder.sidedSuccess(result, level.isClientSide());
    }

    private InteractionResultHolder<ItemStack> pour(Level level, Player player, ItemStack held, BlockHitResult hit,
            BlockPos pos, BlockPos beside) {
        BlockState state = level.getBlockState(pos);
        BlockPos target = canBlockContainFluid(level, pos, state) ? pos : beside;
        if (!emptyContents(player, level, target, hit, held)) {
            return InteractionResultHolder.fail(held);
        }

        checkExtraContent(player, level, held, target);
        if (player instanceof ServerPlayer server) {
            CriteriaTriggers.PLACED_BLOCK.trigger(server, target, held);
        }
        player.awardStat(Stats.ITEM_USED.get(this));

        ItemStack result = player.getAbilities().instabuild ? held : emptied();
        return InteractionResultHolder.sidedSuccess(result, level.isClientSide());
    }

    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable CompoundTag nbt) {
        return new BucketCap(stack);
    }

    @Nullable
    static Item filledFor(Fluid fluid) {
        if (fluid == Fluids.WATER || fluid == Fluids.FLOWING_WATER) {
            return reg.WATER_CLAY_BUCKET.get();
        }
        if (fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA) {
            return reg.LAVA_CLAY_BUCKET.get();
        }
        return null;
    }
}
