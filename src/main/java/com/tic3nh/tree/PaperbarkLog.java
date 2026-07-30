package com.tic3nh.tree;

import java.util.function.Supplier;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.HitResult;

import net.minecraftforge.common.ToolAction;
import net.minecraftforge.common.ToolActions;

import com.tic3nh.config.Cfg;

/**
 * A paperbark log or wood block, whose bark comes off in four passes instead of vanilla's one.
 *
 * <p>{@code strip} runs 0 (bark intact) through 3 (75% peeled); a fourth strip replaces the block with
 * the fully stripped one. Every pass has a chance to peel off a sheet of paper. The three part-peeled
 * states are scenery only — they never drop themselves, and pick-block hands over the stripped log.
 *
 * <p>Peeled logs grow their bark back one stage per random tick roll, so a trunk left at 75% or better
 * heals into a standing paper farm. Take the last stage off and it becomes a plain stripped log, which
 * is a building block and stays bare.
 *
 * <p>The strip itself is Forge's {@code getToolModifiedState}, which is what both vanilla axes and
 * Tinkers' hatchets ask. Tinkers calls it once with {@code simulate = true} to test the block before
 * calling again to apply, so the paper only drops on the real pass, server side.
 */
public class PaperbarkLog extends RotatedPillarBlock implements Wood.Burns {

    public static final IntegerProperty STRIP = IntegerProperty.create("strip", 0, 3);

    /** Peeling past this value hands the position over to the fully stripped block. */
    public static final int LAST_STAGE = 3;

    private final Supplier<Block> stripped;

    public PaperbarkLog(Properties props, Supplier<Block> stripped) {
        super(props);
        this.stripped = stripped;
        registerDefaultState(defaultBlockState().setValue(STRIP, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(STRIP);
    }

    @Override
    public BlockState getToolModifiedState(BlockState state, UseOnContext context, ToolAction action,
            boolean simulate) {
        if (!ToolActions.AXE_STRIP.equals(action)) {
            return super.getToolModifiedState(state, context, action, simulate);
        }
        if (!context.getItemInHand().canPerformAction(ToolActions.AXE_STRIP)) {
            return null;
        }

        Level level = context.getLevel();
        if (!simulate && !level.isClientSide()) {
            int chance = Cfg.paperPerStripChance();
            if (chance > 0 && level.getRandom().nextInt(100) < chance) {
                popResourceFromFace(level, context.getClickedPos(), context.getClickedFace(),
                        new ItemStack(Items.PAPER));
            }
        }

        int stage = state.getValue(STRIP);
        return stage < LAST_STAGE
                ? state.setValue(STRIP, stage + 1)
                : stripped.get().defaultBlockState().setValue(AXIS, state.getValue(AXIS));
    }

    /** Only peeled logs need ticks; the cache reads this once per state, so keep config out of it. */
    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(STRIP) > 0;
    }

    /** Bark grows back a stage at a time. The fully stripped block is separate, so it stays bare. */
    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        int chance = Cfg.barkRegrowChance();
        if (chance > 0 && random.nextInt(100) < chance) {
            level.setBlock(pos, state.setValue(STRIP, state.getValue(STRIP) - 1), UPDATE_CLIENTS);
        }
    }

    @Override
    public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter level, BlockPos pos,
            Player player) {
        return state.getValue(STRIP) > 0
                ? new ItemStack(stripped.get())
                : super.getCloneItemStack(state, target, level, pos, player);
    }
}
