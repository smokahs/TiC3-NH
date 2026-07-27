package com.tic3nh.buckets;

import java.util.function.Predicate;

import net.minecraft.core.BlockPos;
import net.minecraft.core.cauldron.CauldronInteraction;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.animal.Cow;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.LayeredCauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;

import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import com.tic3nh.TiC3NH;
import com.tic3nh.setup.reg;

@Mod.EventBusSubscriber(modid = TiC3NH.MOD_ID)
public final class BucketEvents {

    private BucketEvents() {}

    @SubscribeEvent
    public static void milkCow(PlayerInteractEvent.EntityInteract event) {
        if (!(event.getTarget() instanceof Cow cow) || cow.isBaby()) {
            return;
        }
        ItemStack held = event.getItemStack();
        if (!held.is(reg.CLAY_BUCKET.get())) {
            return;
        }

        Player player = event.getEntity();
        Level level = event.getLevel();
        player.playSound(SoundEvents.COW_MILK, 1.0F, 1.0F);
        player.setItemInHand(
                event.getHand(),
                ItemUtils.createFilledResult(held, player, new ItemStack(reg.MILK_CLAY_BUCKET.get())));

        event.setCancellationResult(InteractionResult.sidedSuccess(level.isClientSide()));
        event.setCanceled(true);
    }

    public static void registerCauldrons() {
        CauldronInteraction.EMPTY.put(
                reg.WATER_CLAY_BUCKET.get(),
                (state, level, pos, player, hand, stack) -> emptyInto(
                        level,
                        pos,
                        player,
                        hand,
                        stack,
                        Blocks.WATER_CAULDRON.defaultBlockState().setValue(LayeredCauldronBlock.LEVEL, 3),
                        SoundEvents.BUCKET_EMPTY));

        CauldronInteraction.EMPTY.put(
                reg.LAVA_CLAY_BUCKET.get(),
                (state, level, pos, player, hand, stack) -> emptyInto(
                        level,
                        pos,
                        player,
                        hand,
                        stack,
                        Blocks.LAVA_CAULDRON.defaultBlockState(),
                        SoundEvents.BUCKET_EMPTY_LAVA));

        CauldronInteraction.WATER.put(
                reg.CLAY_BUCKET.get(),
                (state, level, pos, player, hand, stack) -> fillFrom(
                        state,
                        level,
                        pos,
                        player,
                        hand,
                        stack,
                        reg.WATER_CLAY_BUCKET.get(),
                        full -> full.getValue(LayeredCauldronBlock.LEVEL) == 3,
                        SoundEvents.BUCKET_FILL));

        CauldronInteraction.LAVA.put(
                reg.CLAY_BUCKET.get(),
                (state, level, pos, player, hand, stack) -> fillFrom(
                        state,
                        level,
                        pos,
                        player,
                        hand,
                        stack,
                        reg.LAVA_CLAY_BUCKET.get(),
                        any -> true,
                        SoundEvents.BUCKET_FILL_LAVA));
    }

    private static InteractionResult emptyInto(Level level, BlockPos pos, Player player, InteractionHand hand,
            ItemStack stack, BlockState filled, SoundEvent sound) {
        if (!level.isClientSide) {
            Item item = stack.getItem();
            if (!player.getAbilities().instabuild) {
                ItemStack left = item instanceof ClayBucket bucket ? bucket.emptied() : ItemStack.EMPTY;
                stack.shrink(1);
                if (stack.isEmpty()) {
                    player.setItemInHand(hand, left);
                } else if (!left.isEmpty() && !player.getInventory().add(left)) {
                    player.drop(left, false);
                }
            }
            player.awardStat(Stats.FILL_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(item));
            level.setBlockAndUpdate(pos, filled);
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PLACE, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }

    private static InteractionResult fillFrom(BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, ItemStack stack, Item filled, Predicate<BlockState> ready, SoundEvent sound) {
        if (!ready.test(state)) {
            return InteractionResult.PASS;
        }
        if (!level.isClientSide) {
            player.setItemInHand(hand, ItemUtils.createFilledResult(stack, player, new ItemStack(filled)));
            player.awardStat(Stats.USE_CAULDRON);
            player.awardStat(Stats.ITEM_USED.get(stack.getItem()));
            level.setBlockAndUpdate(pos, Blocks.CAULDRON.defaultBlockState());
            level.playSound(null, pos, sound, SoundSource.BLOCKS, 1.0F, 1.0F);
            level.gameEvent(null, GameEvent.FLUID_PICKUP, pos);
        }
        return InteractionResult.sidedSuccess(level.isClientSide);
    }
}
